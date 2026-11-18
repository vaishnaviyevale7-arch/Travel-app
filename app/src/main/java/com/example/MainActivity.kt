package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.TravelViewModel
import com.example.ui.components.AppDestination
import com.example.ui.components.FloatingIslandNavBar
import com.example.ui.components.TerracottaNavRail
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.ItineraryScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.screens.StayDetailScreen
import com.example.ui.theme.TerracottaTideTheme
import com.example.ui.theme.TerracottaTheme

private const val ROUTE_STAY_DETAIL = "stay_detail/{stayId}"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: TravelViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val useDarkTheme = uiState.darkPreviewOverride ?: false

            SideEffect {
                enableEdgeToEdge(
                    statusBarStyle = if (useDarkTheme) {
                        SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
                    } else {
                        SystemBarStyle.light(
                            android.graphics.Color.TRANSPARENT,
                            android.graphics.Color.TRANSPARENT
                        )
                    },
                    navigationBarStyle = if (useDarkTheme) {
                        SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
                    } else {
                        SystemBarStyle.light(
                            android.graphics.Color.TRANSPARENT,
                            android.graphics.Color.TRANSPARENT
                        )
                    }
                )
            }

            TerracottaTideTheme(darkTheme = useDarkTheme) {
                TerracottaTideApp(
                    viewModel = viewModel,
                    isCurrentlyDark = useDarkTheme
                )
            }
        }
    }
}

@Composable
private fun TerracottaTideApp(
    viewModel: TravelViewModel,
    isCurrentlyDark: Boolean
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showTopLevelNav = currentRoute == AppDestination.EXPLORE.route ||
        currentRoute == AppDestination.ITINERARY.route ||
        currentRoute == AppDestination.SAVED.route

    val navigateToTopDestination: (AppDestination) -> Unit = { dest ->
        navController.navigate(dest.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 600.dp

        Scaffold(
            containerColor = TerracottaTheme.colors.sandCanvas
        ) { _ ->
            Row(modifier = Modifier.fillMaxSize()) {
                if (useNavigationRail && showTopLevelNav) {
                    TerracottaNavRail(
                        currentRoute = currentRoute,
                        savedCount = uiState.savedStays.size,
                        onNavigate = navigateToTopDestination
                    )
                }

                Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = AppDestination.EXPLORE.route,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(AppDestination.EXPLORE.route) {
                            ExploreScreen(
                                uiState = uiState,
                                onSearchQueryChange = viewModel::onSearchQueryChange,
                                onSelectCategory = viewModel::onSelectCategory,
                                onToggleSaved = viewModel::onToggleSaved,
                                onOpenStay = { stay ->
                                    navController.navigate("stay_detail/${stay.id}")
                                },
                                onOpenSavedTab = {
                                    navigateToTopDestination(AppDestination.SAVED)
                                }
                            )
                        }

                        composable(AppDestination.ITINERARY.route) {
                            ItineraryScreen(
                                uiState = uiState,
                                onSelectDay = viewModel::onSelectItineraryDay,
                                onToggleMomentCompleted = viewModel::onToggleMomentCompleted,
                                onAddCustomMoment = viewModel::onAddCustomMoment
                            )
                        }

                        composable(AppDestination.SAVED.route) {
                            SavedScreen(
                                uiState = uiState,
                                isCurrentlyDark = isCurrentlyDark,
                                onToggleSaved = viewModel::onToggleSaved,
                                onOpenStay = { stay ->
                                    navController.navigate("stay_detail/${stay.id}")
                                },
                                onNavigateExplore = {
                                    navigateToTopDestination(AppDestination.EXPLORE)
                                },
                                onToggleEcoOnly = viewModel::onToggleEcoFilter,
                                onToggleMaritimeOnly = viewModel::onToggleMaritimeFilter,
                                onToggleDarkMode = { dark ->
                                    viewModel.onSetDarkPreview(dark)
                                }
                            )
                        }

                        composable(
                            route = ROUTE_STAY_DETAIL,
                            arguments = listOf(navArgument("stayId") { type = NavType.StringType })
                        ) { entry ->
                            val stayId = entry.arguments?.getString("stayId") ?: ""
                            val stay = viewModel.findStay(stayId) ?: uiState.stays.first()
                            StayDetailScreen(
                                stay = stay,
                                isSaved = stay.id in uiState.savedStayIds,
                                onToggleSaved = { viewModel.onToggleSaved(stay.id) },
                                onBack = { navController.popBackStack() },
                                onConfirmBooking = { window, guests, nights ->
                                    viewModel.onBookStay(stay, window, guests, nights)
                                },
                                onGoToItinerary = {
                                    navController.popBackStack(AppDestination.EXPLORE.route, false)
                                    navigateToTopDestination(AppDestination.ITINERARY)
                                }
                            )
                        }
                    }

                    if (!useNavigationRail && showTopLevelNav) {
                        FloatingIslandNavBar(
                            currentRoute = currentRoute,
                            savedCount = uiState.savedStays.size,
                            onNavigate = navigateToTopDestination,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}
