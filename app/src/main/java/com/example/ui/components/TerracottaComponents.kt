package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.model.CuratedStay
import com.example.model.StayBadgeType
import com.example.ui.theme.AmbientSlateShadow
import com.example.ui.theme.CoralAccent
import com.example.ui.theme.TerracottaPrimary
import com.example.ui.theme.TerracottaTheme

enum class AppDestination(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    EXPLORE(
        route = "explore",
        label = "Explore",
        selectedIcon = Icons.Rounded.Explore,
        unselectedIcon = Icons.Outlined.Explore,
        testTag = "nav_explore_tab"
    ),
    ITINERARY(
        route = "itinerary",
        label = "Itinerary",
        selectedIcon = Icons.Rounded.CalendarMonth,
        unselectedIcon = Icons.Outlined.CalendarMonth,
        testTag = "nav_itinerary_tab"
    ),
    SAVED(
        route = "saved",
        label = "Saved",
        selectedIcon = Icons.Rounded.Bookmark,
        unselectedIcon = Icons.Outlined.BookmarkBorder,
        testTag = "nav_saved_tab"
    )
}

@Composable
fun TerracottaPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    testTag: String = "primary_button"
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.98f else 1f,
        animationSpec = spring(stiffness = 600f),
        label = "primary_button_scale"
    )
    val containerColor by animateColorAsState(
        targetValue = when {
            !enabled -> TerracottaTheme.colors.travertineWell
            isPressed -> CoralAccent
            else -> MaterialTheme.colorScheme.primary
        },
        label = "primary_button_color"
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White,
            disabledContainerColor = TerracottaTheme.colors.travertineWell,
            disabledContentColor = TerracottaTheme.colors.slateMuted
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 0.dp
        ),
        modifier = modifier
            .heightIn(min = 52.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .testTag(testTag)
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TerracottaSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    testTag: String = "secondary_button"
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(stiffness = 600f),
        label = "secondary_button_scale"
    )

    Button(
        onClick = onClick,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = TerracottaTheme.colors.travertineWell,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        modifier = modifier
            .heightIn(min = 52.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .testTag(testTag)
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TerracottaFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    useTerracottaActive: Boolean = false,
    testTag: String = "filter_chip"
) {
    val backgroundColor by animateColorAsState(
        targetValue = when {
            selected && useTerracottaActive -> MaterialTheme.colorScheme.primary
            selected -> MaterialTheme.colorScheme.onBackground
            else -> TerracottaTheme.colors.travertineWell
        },
        label = "chip_bg"
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) {
            if (TerracottaTheme.colors.isDark && !useTerracottaActive) {
                TerracottaTheme.colors.sandCanvas
            } else {
                Color.White
            }
        } else {
            TerracottaTheme.colors.slateBody
        },
        label = "chip_fg"
    )

    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = backgroundColor,
        shadowElevation = if (selected) 4.dp else 0.dp,
        modifier = modifier
            .heightIn(min = 48.dp)
            .padding(vertical = 6.dp)
            .testTag(testTag)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(36.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold,
                color = contentColor,
                maxLines = 1
            )
        }
    }
}

@Composable
fun TerracottaStatusBadge(
    label: String,
    isUrgentOrSunset: Boolean = false,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isUrgentOrSunset) {
        TerracottaTheme.colors.terracottaTint
    } else {
        TerracottaTheme.colors.tealTint
    }
    val fgColor = if (isUrgentOrSunset) {
        MaterialTheme.colorScheme.primary
    } else {
        TerracottaTheme.colors.tidepoolTeal
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = fgColor,
            maxLines = 1
        )
    }
}

@Composable
fun TerracottaSearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColor by animateColorAsState(
        targetValue = if (isFocused) MaterialTheme.colorScheme.primary else TerracottaTheme.colors.inputHairline,
        label = "search_border"
    )
    val borderWidth = if (isFocused) 2.dp else 1.dp

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = TerracottaTheme.colors.linenCard,
        border = BorderStroke(borderWidth, borderColor),
        shadowElevation = if (isFocused) 6.dp else 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("explore_search_input")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search stays",
                tint = if (isFocused) MaterialTheme.colorScheme.primary else TerracottaTheme.colors.slateMuted,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.weight(1f)
            ) {
                if (query.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TerracottaTheme.colors.slateMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isFocused = it.isFocused }
                )
            }
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { onQueryChange("") },
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("clear_search_button")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Clear search",
                        tint = TerracottaTheme.colors.slateMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun EditorialStayCard(
    stay: CuratedStay,
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isHeroFeatured: Boolean = false
) {
    val cardShape = RoundedCornerShape(20.dp)
    val aspectRatio = if (isHeroFeatured) 1.6f else 4f / 3f

    Surface(
        shape = cardShape,
        color = TerracottaTheme.colors.linenCard,
        border = BorderStroke(1.dp, TerracottaTheme.colors.cardHairline),
        shadowElevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = cardShape,
                ambientColor = AmbientSlateShadow,
                spotColor = AmbientSlateShadow
            )
            .clip(cardShape)
            .clickable(onClick = onClick)
            .testTag("stay_card_${stay.id}")
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
                    .clip(RoundedCornerShape(14.dp))
            ) {
                Image(
                    painter = painterResource(id = stay.imageResId),
                    contentDescription = "${stay.title} in ${stay.region}, ${stay.country}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Top and bottom subtle scrims for crisp contrast
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.25f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.55f)
                                )
                            )
                        )
                )

                // Top-left status badge on frosted pill
                Surface(
                    shape = CircleShape,
                    color = TerracottaTheme.colors.linenCard.copy(alpha = 0.92f),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                ) {
                    val isPeak = stay.badgeType == StayBadgeType.PEAK_SEASON
                    Text(
                        text = stay.badgeLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isPeak) MaterialTheme.colorScheme.primary else TerracottaTheme.colors.tidepoolTeal,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }

                // Top-right 48dp touch target bookmark icon
                IconButton(
                    onClick = onToggleSave,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(48.dp)
                        .testTag("bookmark_button_${stay.id}")
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.36f))
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Rounded.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = if (isSaved) "Remove ${stay.title} from saved" else "Save ${stay.title}",
                            tint = if (isSaved) CoralAccent else Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Bottom-left pricing & rating overlay
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.50f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "$${stay.pricePerNight}",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = " / night",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.50f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Star,
                                contentDescription = null,
                                tint = CoralAccent,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${stay.rating}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${stay.region.uppercase()} · ${stay.country.uppercase()}",
                style = MaterialTheme.typography.labelSmall,
                color = TerracottaTheme.colors.tidepoolTeal
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stay.title,
                style = if (isHeroFeatured) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stay.editorialSummary,
                style = MaterialTheme.typography.bodyMedium,
                color = TerracottaTheme.colors.slateBody,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Amenity micro-tags
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                stay.amenities.take(2).forEach { amenity ->
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(TerracottaTheme.colors.travertineWell)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = amenity,
                            style = MaterialTheme.typography.labelMedium,
                            color = TerracottaTheme.colors.slateBody,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FloatingIslandNavBar(
    currentRoute: String?,
    savedCount: Int,
    onNavigate: (AppDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = TerracottaTheme.colors.floatingBarSurface,
            border = BorderStroke(1.dp, TerracottaTheme.colors.inputHairline),
            shadowElevation = 12.dp,
            modifier = Modifier
                .widthIn(max = 420.dp)
                .fillMaxWidth()
                .height(64.dp)
                .testTag("floating_island_nav_bar")
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                AppDestination.entries.forEach { destination ->
                    val selected = currentRoute == destination.route
                    val tint by animateColorAsState(
                        targetValue = if (selected) MaterialTheme.colorScheme.primary else TerracottaTheme.colors.slateMuted,
                        label = "nav_item_tint"
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 48.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { onNavigate(destination) }
                            .testTag(destination.testTag)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                                    contentDescription = destination.label,
                                    tint = tint,
                                    modifier = Modifier.size(22.dp)
                                )
                                if (destination == AppDestination.SAVED && savedCount > 0) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = savedCount.toString(),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (selected) MaterialTheme.colorScheme.primary else TerracottaTheme.colors.slateMuted
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            if (selected) {
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .clip(CircleShape)
                                        .background(TerracottaPrimary)
                                )
                            } else {
                                Text(
                                    text = destination.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = tint
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TerracottaNavRail(
    currentRoute: String?,
    savedCount: Int,
    onNavigate: (AppDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        containerColor = TerracottaTheme.colors.linenCard,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .border(1.dp, TerracottaTheme.colors.cardHairline)
            .testTag("terracotta_nav_rail")
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        AppDestination.entries.forEach { destination ->
            val selected = currentRoute == destination.route
            NavigationRailItem(
                selected = selected,
                onClick = { onNavigate(destination) },
                icon = {
                    Icon(
                        imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                        contentDescription = destination.label
                    )
                },
                label = {
                    val labelText = if (destination == AppDestination.SAVED && savedCount > 0) {
                        "${destination.label} ($savedCount)"
                    } else {
                        destination.label
                    }
                    Text(
                        text = labelText,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = TerracottaTheme.colors.terracottaTint,
                    unselectedIconColor = TerracottaTheme.colors.slateMuted,
                    unselectedTextColor = TerracottaTheme.colors.slateMuted
                ),
                modifier = Modifier.testTag(destination.testTag)
            )
        }
    }
}
