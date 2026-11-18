package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.CuratedStay
import com.example.model.StayCategory
import com.example.model.TravelUiState
import com.example.ui.components.EditorialStayCard
import com.example.ui.components.TerracottaFilterChip
import com.example.ui.components.TerracottaSearchField
import com.example.ui.components.TerracottaStatusBadge
import com.example.ui.components.TerracottaSecondaryButton
import com.example.ui.theme.TerracottaTheme

@Composable
fun ExploreScreen(
    uiState: TravelUiState,
    onSearchQueryChange: (String) -> Unit,
    onSelectCategory: (StayCategory) -> Unit,
    onToggleSaved: (String) -> Unit,
    onOpenStay: (CuratedStay) -> Unit,
    onOpenSavedTab: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(TerracottaTheme.colors.sandCanvas)
    ) {
        val isNarrow = maxWidth < 375.dp
        val isTwoColumn = maxWidth >= 600.dp
        val horizontalGutter = if (isNarrow) 16.dp else 20.dp

        val filtered = uiState.filteredStays
        val heroStay = filtered.firstOrNull { it.isHeroPick } ?: filtered.firstOrNull()
        val secondaryStays = if (heroStay != null) filtered.filter { it.id != heroStay.id } else emptyList()

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
            // Editorial Header
            item(key = "explore_header") {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        TerracottaStatusBadge(
                            label = stringResource(R.string.hero_eyebrow),
                            isUrgentOrSunset = false
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(R.string.hero_title),
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Surface(
                        shape = CircleShape,
                        color = TerracottaTheme.colors.linenCard,
                        border = BorderStroke(1.dp, TerracottaTheme.colors.inputHairline),
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .clickable(onClick = onOpenSavedTab)
                            .testTag("header_saved_shortcut")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Outlined.BookmarkBorder,
                                contentDescription = "Open saved stays",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Search Field
            item(key = "search_bar") {
                TerracottaSearchField(
                    query = uiState.searchQuery,
                    onQueryChange = onSearchQueryChange,
                    placeholder = stringResource(R.string.search_placeholder)
                )
            }

            // Filter Chips Row
            item(key = "filter_chips") {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(StayCategory.entries, key = { it.name }) { category ->
                        TerracottaFilterChip(
                            label = category.label,
                            selected = uiState.selectedCategory == category,
                            onClick = { onSelectCategory(category) },
                            useTerracottaActive = category == StayCategory.PEAK_SEASON,
                            testTag = "category_chip_${category.name.lowercase()}"
                        )
                    }
                }
            }

            // Editorial Empty State if search/filters return 0 items
            if (filtered.isEmpty()) {
                item(key = "empty_search_state") {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = TerracottaTheme.colors.linenCard,
                        border = BorderStroke(1.dp, TerracottaTheme.colors.cardHairline),
                        modifier = Modifier.fillMaxWidth()
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
                                    imageVector = Icons.Outlined.WbSunny,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No sanctuaries match your filter",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Try clearing your search or switching back to all curated coastal stays.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TerracottaTheme.colors.slateBody
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            TerracottaSecondaryButton(
                                text = "Reset Filters",
                                onClick = {
                                    onSearchQueryChange("")
                                    onSelectCategory(StayCategory.ALL)
                                },
                                testTag = "reset_filters_button"
                            )
                        }
                    }
                }
            } else {
                // Featured Hero Pick
                if (heroStay != null) {
                    item(key = "hero_pick_label") {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Editor's Coastal Pick",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "${filtered.size} curated",
                                style = MaterialTheme.typography.labelMedium,
                                color = TerracottaTheme.colors.slateMuted
                            )
                        }
                    }

                    item(key = "hero_card_${heroStay.id}") {
                        EditorialStayCard(
                            stay = heroStay,
                            isSaved = heroStay.id in uiState.savedStayIds,
                            onToggleSave = { onToggleSaved(heroStay.id) },
                            onClick = { onOpenStay(heroStay) },
                            isHeroFeatured = true
                        )
                    }
                }

                // Curated Stays Collection
                if (secondaryStays.isNotEmpty()) {
                    item(key = "collection_label") {
                        Column {
                            Text(
                                text = "Sun-Drenched Retreats",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Architectural stays chosen for tactile warmth & coastal calm",
                                style = MaterialTheme.typography.bodySmall,
                                color = TerracottaTheme.colors.slateMuted
                            )
                        }
                    }

                    if (isTwoColumn) {
                        val chunked = secondaryStays.chunked(2)
                        items(chunked, key = { row -> row.joinToString("_") { it.id } }) { pair ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                pair.forEach { stay ->
                                    EditorialStayCard(
                                        stay = stay,
                                        isSaved = stay.id in uiState.savedStayIds,
                                        onToggleSave = { onToggleSaved(stay.id) },
                                        onClick = { onOpenStay(stay) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (pair.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    } else {
                        items(secondaryStays, key = { it.id }) { stay ->
                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                EditorialStayCard(
                                    stay = stay,
                                    isSaved = stay.id in uiState.savedStayIds,
                                    onToggleSave = { onToggleSaved(stay.id) },
                                    onClick = { onOpenStay(stay) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
