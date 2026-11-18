package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.EditorialCatalog
import com.example.data.TravelPreferencesRepository
import com.example.model.CuratedStay
import com.example.model.ItineraryMoment
import com.example.model.StayCategory
import com.example.model.TravelUiState
import com.example.model.TripBooking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TravelViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TravelPreferencesRepository(application.applicationContext)

    private val selectedCategory = MutableStateFlow(StayCategory.ALL)
    private val searchQuery = MutableStateFlow("")
    private val selectedDayIndex = MutableStateFlow(0)

    val uiState: StateFlow<TravelUiState> = combine(
        repository.persistedDataFlow,
        selectedCategory,
        searchQuery,
        selectedDayIndex
    ) { persisted, category, query, dayIdx ->
        val allMoments = (EditorialCatalog.defaultItineraryMoments + persisted.customMoments).map { moment ->
            moment.copy(isCompleted = moment.id in persisted.completedMomentIds)
        }
        val allBookings = listOf(EditorialCatalog.defaultBooking) + persisted.extraBookings

        TravelUiState(
            stays = EditorialCatalog.curatedStays,
            selectedCategory = category,
            searchQuery = query,
            savedStayIds = persisted.savedStayIds,
            ecoOnlyFilter = persisted.ecoOnlyFilter,
            maritimeOnlyFilter = persisted.maritimeOnlyFilter,
            bookings = allBookings,
            itineraryMoments = allMoments,
            completedMomentIds = persisted.completedMomentIds,
            selectedDayIndex = dayIdx,
            darkPreviewOverride = persisted.darkPreviewOverride
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = TravelUiState(
            stays = EditorialCatalog.curatedStays,
            savedStayIds = EditorialCatalog.defaultSavedIds,
            bookings = listOf(EditorialCatalog.defaultBooking),
            itineraryMoments = EditorialCatalog.defaultItineraryMoments
        )
    )

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }

    fun onSelectCategory(category: StayCategory) {
        selectedCategory.value = category
    }

    fun onToggleSaved(stayId: String) {
        viewModelScope.launch {
            repository.toggleSavedStay(stayId)
        }
    }

    fun onSelectItineraryDay(dayIndex: Int) {
        selectedDayIndex.value = dayIndex
    }

    fun onToggleMomentCompleted(momentId: String) {
        viewModelScope.launch {
            repository.toggleMomentCompleted(momentId)
        }
    }

    fun onAddCustomMoment(dayIndex: Int, time: String, title: String, location: String) {
        val dayLabels = listOf("Day 1 · Arrival", "Day 2 · Cove", "Day 3 · Atelier")
        val label = dayLabels.getOrElse(dayIndex) { "Day ${dayIndex + 1}" }
        val moment = ItineraryMoment(
            id = "custom-${System.currentTimeMillis()}",
            dayIndex = dayIndex,
            dayLabel = label,
            time = time.ifBlank { "04:00 PM" },
            title = title.trim(),
            location = location.trim().ifBlank { "Coastal Walk" },
            statusLabel = "PERSONAL NOTE",
            isCustom = true
        )
        viewModelScope.launch {
            repository.addCustomMoment(moment)
        }
    }

    fun onBookStay(stay: CuratedStay, dateWindow: String, guests: Int, nights: Int) {
        val booking = TripBooking(
            id = "booking-${stay.id}-${System.currentTimeMillis()}",
            stayId = stay.id,
            stayTitle = stay.title,
            region = "${stay.region}, ${stay.country}",
            imageResId = stay.imageResId,
            dateWindow = dateWindow,
            guests = guests,
            nights = nights,
            totalPrice = stay.pricePerNight * nights
        )
        viewModelScope.launch {
            repository.addBooking(booking)
        }
    }

    fun onToggleEcoFilter(enabled: Boolean) {
        viewModelScope.launch {
            repository.setEcoOnlyFilter(enabled)
        }
    }

    fun onToggleMaritimeFilter(enabled: Boolean) {
        viewModelScope.launch {
            repository.setMaritimeOnlyFilter(enabled)
        }
    }

    fun onSetDarkPreview(dark: Boolean?) {
        viewModelScope.launch {
            repository.setDarkPreviewOverride(dark)
        }
    }

    fun findStay(stayId: String): CuratedStay? {
        return EditorialCatalog.curatedStays.find { it.id == stayId }
    }
}
