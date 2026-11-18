package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.CuratedStay
import com.example.model.StayBadgeType
import com.example.ui.components.TerracottaFilterChip
import com.example.ui.components.TerracottaPrimaryButton
import com.example.ui.components.TerracottaSecondaryButton
import com.example.ui.components.TerracottaStatusBadge
import com.example.ui.theme.CoralAccent
import com.example.ui.theme.TerracottaTheme

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StayDetailScreen(
    stay: CuratedStay,
    isSaved: Boolean,
    onToggleSaved: () -> Unit,
    onBack: () -> Unit,
    onConfirmBooking: (dateWindow: String, guests: Int, nights: Int) -> Unit,
    onGoToItinerary: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBack)

    var selectedDateIndex by rememberSaveable { mutableIntStateOf(0) }
    var guestCount by rememberSaveable { mutableIntStateOf(2.coerceAtMost(stay.maxGuests)) }
    val nights = 3
    val totalCost = stay.pricePerNight * nights
    var showConfirmationSheet by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState(pageCount = { stay.galleryResIds.size })
    val selectedDateWindow = stay.availableDateWindows.getOrElse(selectedDateIndex) { "Nov 21–24" }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TerracottaTheme.colors.sandCanvas)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 128.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Edge-to-edge Hero Gallery Carousel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.25f)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    Image(
                        painter = painterResource(id = stay.galleryResIds[page]),
                        contentDescription = "${stay.title} photo ${page + 1}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.35f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.40f)
                                )
                            )
                        )
                )

                // Top Overlay Actions (Back & Bookmark)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.40f))
                            .testTag("detail_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Navigate back",
                            tint = Color.White
                        )
                    }

                    IconButton(
                        onClick = onToggleSaved,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.40f))
                            .testTag("detail_bookmark_button")
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Rounded.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = if (isSaved) "Remove bookmark" else "Save stay",
                            tint = if (isSaved) CoralAccent else Color.White
                        )
                    }
                }

                // Pill Carousel Paginator
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.35f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    repeat(stay.galleryResIds.size) { index ->
                        val active = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .height(6.dp)
                                .width(if (active) 20.dp else 6.dp)
                                .clip(CircleShape)
                                .background(if (active) Color.White else Color.White.copy(alpha = 0.45f))
                        )
                    }
                }
            }

            // Editorial Content Body
            Column(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Header & Rating
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TerracottaStatusBadge(
                            label = stay.badgeLabel,
                            isUrgentOrSunset = stay.badgeType == StayBadgeType.PEAK_SEASON
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${stay.rating} (${stay.reviewCount} reviews)",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    Text(
                        text = stay.title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "${stay.region} · ${stay.country}",
                        style = MaterialTheme.typography.titleMedium,
                        color = TerracottaTheme.colors.tidepoolTeal
                    )

                    Text(
                        text = stay.architecturalNotes,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TerracottaTheme.colors.slateBody
                    )
                }

                // Soft Travertine Host Quote Inset Well
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = TerracottaTheme.colors.travertineWell,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Icon(
                            imageVector = Icons.Rounded.FormatQuote,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stay.hostQuote,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stay.hostName,
                            style = MaterialTheme.typography.labelMedium,
                            color = TerracottaTheme.colors.slateMuted
                        )
                    }
                }

                // Interactive Date Selection Card
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = TerracottaTheme.colors.linenCard,
                    border = BorderStroke(1.dp, TerracottaTheme.colors.cardHairline),
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Select Stay Window (3 Nights)",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            stay.availableDateWindows.forEachIndexed { idx, window ->
                                TerracottaFilterChip(
                                    label = window,
                                    selected = selectedDateIndex == idx,
                                    onClick = { selectedDateIndex = idx },
                                    useTerracottaActive = true,
                                    testTag = "date_chip_$idx"
                                )
                            }
                        }

                        // Guest Stepper
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(TerracottaTheme.colors.travertineWell)
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Travelers",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Up to ${stay.maxGuests} guests",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TerracottaTheme.colors.slateMuted
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { if (guestCount > 1) guestCount-- },
                                    enabled = guestCount > 1,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .testTag("decrease_guests_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Remove,
                                        contentDescription = "Decrease guests",
                                        tint = if (guestCount > 1) MaterialTheme.colorScheme.onSurface else TerracottaTheme.colors.slateMuted
                                    )
                                }
                                Text(
                                    text = guestCount.toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                IconButton(
                                    onClick = { if (guestCount < stay.maxGuests) guestCount++ },
                                    enabled = guestCount < stay.maxGuests,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .testTag("increase_guests_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Add,
                                        contentDescription = "Increase guests",
                                        tint = if (guestCount < stay.maxGuests) MaterialTheme.colorScheme.onSurface else TerracottaTheme.colors.slateMuted
                                    )
                                }
                            }
                        }
                    }
                }

                // Curated Amenities
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Included Sanctuary Details",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    stay.amenities.forEach { amenity ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(TerracottaTheme.colors.tealTint)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = null,
                                    tint = TerracottaTheme.colors.tidepoolTeal,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = amenity,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }

        // Sticky Floating Pricing & Booking Bar
        Surface(
            color = TerracottaTheme.colors.floatingBarSurface,
            shadowElevation = 14.dp,
            border = BorderStroke(1.dp, TerracottaTheme.colors.inputHairline),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "$$totalCost",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "· $nights nights",
                            style = MaterialTheme.typography.bodySmall,
                            color = TerracottaTheme.colors.slateMuted,
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                    }
                    Text(
                        text = "$selectedDateWindow · $guestCount guests",
                        style = MaterialTheme.typography.labelMedium,
                        color = TerracottaTheme.colors.tidepoolTeal
                    )
                }

                TerracottaPrimaryButton(
                    text = stringResource(R.string.action_reserve_stay),
                    onClick = {
                        onConfirmBooking(selectedDateWindow, guestCount, nights)
                        showConfirmationSheet = true
                    },
                    testTag = "reserve_stay_button"
                )
            }
        }
    }

    if (showConfirmationSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { showConfirmationSheet = false },
            sheetState = sheetState,
            containerColor = TerracottaTheme.colors.linenCard,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(TerracottaTheme.colors.tealTint)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = TerracottaTheme.colors.tidepoolTeal,
                        modifier = Modifier.size(34.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                TerracottaStatusBadge(
                    label = "RESERVATION LOGGED",
                    isUrgentOrSunset = false
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stay.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "$selectedDateWindow · $guestCount travelers · $$totalCost total",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TerracottaTheme.colors.slateBody
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TerracottaSecondaryButton(
                        text = "Keep Exploring",
                        onClick = { showConfirmationSheet = false },
                        modifier = Modifier.weight(1f),
                        testTag = "keep_exploring_button"
                    )
                    TerracottaPrimaryButton(
                        text = "View Itinerary",
                        onClick = {
                            showConfirmationSheet = false
                            onGoToItinerary()
                        },
                        modifier = Modifier.weight(1f),
                        testTag = "view_itinerary_button"
                    )
                }
            }
        }
    }
}
