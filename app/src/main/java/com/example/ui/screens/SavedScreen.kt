package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Sailing
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.CuratedStay
import com.example.model.TravelUiState
import com.example.ui.components.EditorialStayCard
import com.example.ui.components.TerracottaPrimaryButton
import com.example.ui.components.TerracottaStatusBadge
import com.example.ui.theme.TerracottaTheme

@Composable
fun SavedScreen(
    uiState: TravelUiState,
    isCurrentlyDark: Boolean,
    onToggleSaved: (String) -> Unit,
    onOpenStay: (CuratedStay) -> Unit,
    onNavigateExplore: () -> Unit,
    onToggleEcoOnly: (Boolean) -> Unit,
    onToggleMaritimeOnly: (Boolean) -> Unit,
    onToggleDarkMode: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(TerracottaTheme.colors.sandCanvas)
    ) {
        val horizontalGutter = if (maxWidth < 375.dp) 16.dp else 20.dp
        val savedStays = uiState.savedStays

        LazyColumn(
            contentPadding = PaddingValues(
                start = horizontalGutter,
                end = horizontalGutter,
                top = 12.dp,
                bottom = 112.dp
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            item(key = "saved_header") {
                Column(modifier = Modifier.widthIn(max = 600.dp)) {
                    TerracottaStatusBadge(
                        label = "PERSONAL ARCHIVE · ${savedStays.size} SAVED",
                        isUrgentOrSunset = false
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Saved Sanctuaries",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Your short-listed coastal residences and editorial travel preferences.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TerracottaTheme.colors.slateBody
                    )
                }
            }

            if (savedStays.isEmpty()) {
                item(key = "saved_empty_state") {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = TerracottaTheme.colors.linenCard,
                        border = BorderStroke(1.dp, TerracottaTheme.colors.cardHairline),
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .widthIn(max = 600.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(28.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(TerracottaTheme.colors.terracottaTint)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.BookmarkBorder,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.saved_empty_title),
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = stringResource(R.string.saved_empty_subtitle),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TerracottaTheme.colors.slateBody
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            TerracottaPrimaryButton(
                                text = stringResource(R.string.action_discover_stays),
                                onClick = onNavigateExplore,
                                testTag = "saved_empty_explore_button"
                            )
                        }
                    }
                }
            } else {
                items(savedStays, key = { it.id }) { stay ->
                    EditorialStayCard(
                        stay = stay,
                        isSaved = true,
                        onToggleSave = { onToggleSaved(stay.id) },
                        onClick = { onOpenStay(stay) },
                        modifier = Modifier.widthIn(max = 600.dp)
                    )
                }
            }

            // Editorial Preferences Card
            item(key = "travel_preferences_card") {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = TerracottaTheme.colors.linenCard,
                    border = BorderStroke(1.dp, TerracottaTheme.colors.cardHairline),
                    shadowElevation = 3.dp,
                    modifier = Modifier
                        .widthIn(max = 600.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Curated Discovery Preferences",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        PreferenceToggleRow(
                            icon = Icons.Outlined.Eco,
                            title = "Eco-Verified Stays Only",
                            subtitle = "Filter Explore to solar, passive-cooled residences",
                            checked = uiState.ecoOnlyFilter,
                            onCheckedChange = onToggleEcoOnly,
                            testTag = "pref_eco_switch"
                        )
                        PreferenceToggleRow(
                            icon = Icons.Outlined.Sailing,
                            title = "Direct Maritime Access",
                            subtitle = "Prioritize coves, private piers, and boat moorings",
                            checked = uiState.maritimeOnlyFilter,
                            onCheckedChange = onToggleMaritimeOnly,
                            testTag = "pref_maritime_switch"
                        )
                        PreferenceToggleRow(
                            icon = Icons.Outlined.DarkMode,
                            title = "Night Tide Palette",
                            subtitle = "Preview warm terracotta over deep maritime slate",
                            checked = isCurrentlyDark,
                            onCheckedChange = onToggleDarkMode,
                            testTag = "pref_dark_mode_switch"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PreferenceToggleRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    testTag: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(TerracottaTheme.colors.travertineWell)
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(TerracottaTheme.colors.linenCard)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = TerracottaTheme.colors.tidepoolTeal,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TerracottaTheme.colors.slateMuted
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = TerracottaTheme.colors.slateMuted,
                uncheckedTrackColor = TerracottaTheme.colors.linenCard
            ),
            modifier = Modifier.testTag(testTag)
        )
    }
}
