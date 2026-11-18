package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.ItineraryMoment
import com.example.model.TravelUiState
import com.example.model.TripBooking
import com.example.ui.components.TerracottaFilterChip
import com.example.ui.components.TerracottaPrimaryButton
import com.example.ui.components.TerracottaSecondaryButton
import com.example.ui.components.TerracottaStatusBadge
import com.example.ui.theme.TerracottaTheme

@Composable
fun ItineraryScreen(
    uiState: TravelUiState,
    onSelectDay: (Int) -> Unit,
    onToggleMomentCompleted: (String) -> Unit,
    onAddCustomMoment: (dayIndex: Int, time: String, title: String, location: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }

    val dayLabels = listOf("Day 1 · Arrival", "Day 2 · Cove", "Day 3 · Atelier")
    val dayMoments = uiState.itineraryMoments.filter { it.dayIndex == uiState.selectedDayIndex }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(TerracottaTheme.colors.sandCanvas)
    ) {
        val horizontalGutter = if (maxWidth < 375.dp) 16.dp else 20.dp

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
            item(key = "itinerary_header") {
                Column(modifier = Modifier.widthIn(max = 600.dp)) {
                    TerracottaStatusBadge(
                        label = "ACTIVE JOURNEY · ${uiState.bookings.size} RESERVATION${if (uiState.bookings.size > 1) "S" else ""}",
                        isUrgentOrSunset = false
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Your Coastal Itinerary",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Unhurried daily pacing curated around morning tides and golden-hour dinners.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TerracottaTheme.colors.slateBody
                    )
                }
            }

            // Confirmed Bookings Carousel
            item(key = "confirmed_bookings") {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(uiState.bookings, key = { it.id }) { booking ->
                        BookingSummaryBannerCard(booking = booking)
                    }
                }
            }

            // Day Selector Pills
            item(key = "day_selector") {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(dayLabels.size, key = { it }) { index ->
                        TerracottaFilterChip(
                            label = dayLabels[index],
                            selected = uiState.selectedDayIndex == index,
                            onClick = { onSelectDay(index) },
                            useTerracottaActive = true,
                            testTag = "day_chip_$index"
                        )
                    }
                }
            }

            // Timeline Moments
            items(dayMoments, key = { it.id }) { moment ->
                ItineraryMomentCard(
                    moment = moment,
                    onToggleComplete = { onToggleMomentCompleted(moment.id) }
                )
            }

            // Add Custom Moment Secondary Action
            item(key = "add_moment_cta") {
                TerracottaSecondaryButton(
                    text = stringResource(R.string.action_add_moment),
                    leadingIcon = Icons.Outlined.Add,
                    onClick = { showAddDialog = true },
                    modifier = Modifier
                        .widthIn(max = 600.dp)
                        .fillMaxWidth(),
                    testTag = "add_custom_moment_button"
                )
            }
        }
    }

    if (showAddDialog) {
        AddCustomMomentDialog(
            dayLabel = dayLabels.getOrElse(uiState.selectedDayIndex) { "Day 1" },
            onDismiss = { showAddDialog = false },
            onConfirm = { time, title, location ->
                onAddCustomMoment(uiState.selectedDayIndex, time, title, location)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun BookingSummaryBannerCard(booking: TripBooking) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = TerracottaTheme.colors.linenCard,
        border = BorderStroke(1.dp, TerracottaTheme.colors.cardHairline),
        shadowElevation = 3.dp,
        modifier = Modifier
            .width(304.dp)
            .testTag("booking_banner_${booking.id}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp)
        ) {
            Image(
                painter = painterResource(id = booking.imageResId),
                contentDescription = booking.stayTitle,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(14.dp))
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                TerracottaStatusBadge(
                    label = booking.dateWindow,
                    isUrgentOrSunset = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = booking.stayTitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${booking.region} · ${booking.guests} guests",
                    style = MaterialTheme.typography.bodySmall,
                    color = TerracottaTheme.colors.slateMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ItineraryMomentCard(
    moment: ItineraryMoment,
    onToggleComplete: () -> Unit
) {
    val checkBg by animateColorAsState(
        targetValue = if (moment.isCompleted) {
            TerracottaTheme.colors.tidepoolTeal
        } else {
            TerracottaTheme.colors.travertineWell
        },
        label = "moment_check_bg"
    )

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = TerracottaTheme.colors.linenCard,
        border = BorderStroke(1.dp, TerracottaTheme.colors.cardHairline),
        shadowElevation = 3.dp,
        modifier = Modifier
            .widthIn(max = 600.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onToggleComplete)
            .testTag("itinerary_moment_${moment.id}")
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(20.dp)
        ) {
            // Interactive Completion Toggle (48dp touch target)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onToggleComplete)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(checkBg)
                ) {
                    if (moment.isCompleted) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "Completed",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (moment.isUrgentOrSunset) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        TerracottaTheme.colors.tidepoolTeal
                                    }
                                )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Schedule,
                            contentDescription = null,
                            tint = TerracottaTheme.colors.slateMuted,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = moment.time,
                            style = MaterialTheme.typography.labelMedium,
                            color = TerracottaTheme.colors.slateMuted
                        )
                    }

                    TerracottaStatusBadge(
                        label = moment.statusLabel,
                        isUrgentOrSunset = moment.isUrgentOrSunset
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = moment.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (moment.isCompleted) TerracottaTheme.colors.slateMuted else MaterialTheme.colorScheme.onSurface,
                    textDecoration = if (moment.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = TerracottaTheme.colors.tidepoolTeal,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = moment.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = TerracottaTheme.colors.slateBody
                    )
                }
            }
        }
    }
}

@Composable
private fun AddCustomMomentDialog(
    dayLabel: String,
    onDismiss: () -> Unit,
    onConfirm: (time: String, title: String, location: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("04:30 PM") }
    var location by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = TerracottaTheme.colors.linenCard,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = "Add to $dayLabel",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Experience or stop") },
                    placeholder = { Text("Sunset Gelato Walk") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    placeholder = { Text("Marina Pier") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TerracottaPrimaryButton(
                text = "Save Moment",
                enabled = title.isNotBlank(),
                onClick = { onConfirm(time, title, location) },
                testTag = "confirm_custom_moment_button"
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = TerracottaTheme.colors.slateMuted
                )
            }
        }
    )
}
