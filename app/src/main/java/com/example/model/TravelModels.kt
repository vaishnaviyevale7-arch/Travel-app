package com.example.model

import androidx.annotation.DrawableRes

enum class StayCategory(val label: String) {
    ALL("All Stays"),
    COASTAL("Coastal"),
    COURTYARD("Courtyard"),
    ECO_VERIFIED("Eco-Verified"),
    PEAK_SEASON("Peak Season")
}

enum class StayBadgeType {
    ECO_VERIFIED,
    MARITIME,
    PEAK_SEASON
}

data class CuratedStay(
    val id: String,
    val title: String,
    val region: String,
    val country: String,
    val category: StayCategory,
    val badgeType: StayBadgeType,
    val badgeLabel: String,
    val pricePerNight: Int,
    val rating: Double,
    val reviewCount: Int,
    val editorialSummary: String,
    val architecturalNotes: String,
    val hostQuote: String,
    val hostName: String,
    @DrawableRes val imageResId: Int,
    val galleryResIds: List<Int>,
    val amenities: List<String>,
    val availableDateWindows: List<String>,
    val maxGuests: Int,
    val isHeroPick: Boolean = false,
    val isMaritimeAccess: Boolean = true,
    val isEcoVerified: Boolean = true
)

data class ItineraryMoment(
    val id: String,
    val dayIndex: Int,
    val dayLabel: String,
    val time: String,
    val title: String,
    val location: String,
    val statusLabel: String,
    val isUrgentOrSunset: Boolean = false,
    val isCompleted: Boolean = false,
    val isCustom: Boolean = false
)

data class TripBooking(
    val id: String,
    val stayId: String,
    val stayTitle: String,
    val region: String,
    @DrawableRes val imageResId: Int,
    val dateWindow: String,
    val guests: Int,
    val nights: Int,
    val totalPrice: Int
)

data class TravelUiState(
    val stays: List<CuratedStay> = emptyList(),
    val selectedCategory: StayCategory = StayCategory.ALL,
    val searchQuery: String = "",
    val savedStayIds: Set<String> = emptySet(),
    val ecoOnlyFilter: Boolean = false,
    val maritimeOnlyFilter: Boolean = false,
    val bookings: List<TripBooking> = emptyList(),
    val itineraryMoments: List<ItineraryMoment> = emptyList(),
    val completedMomentIds: Set<String> = emptySet(),
    val selectedDayIndex: Int = 0,
    val darkPreviewOverride: Boolean? = null
) {
    val filteredStays: List<CuratedStay>
        get() = stays.filter { stay ->
            val matchesCategory = when (selectedCategory) {
                StayCategory.ALL -> true
                StayCategory.COASTAL -> stay.category == StayCategory.COASTAL
                StayCategory.COURTYARD -> stay.category == StayCategory.COURTYARD
                StayCategory.ECO_VERIFIED -> stay.isEcoVerified
                StayCategory.PEAK_SEASON -> stay.badgeType == StayBadgeType.PEAK_SEASON
            }
            val matchesEco = !ecoOnlyFilter || stay.isEcoVerified
            val matchesMaritime = !maritimeOnlyFilter || stay.isMaritimeAccess
            val q = searchQuery.trim().lowercase()
            val matchesQuery = q.isEmpty() ||
                stay.title.lowercase().contains(q) ||
                stay.region.lowercase().contains(q) ||
                stay.country.lowercase().contains(q) ||
                stay.amenities.any { it.lowercase().contains(q) }
            matchesCategory && matchesEco && matchesMaritime && matchesQuery
        }

    val savedStays: List<CuratedStay>
        get() = stays.filter { it.id in savedStayIds }
}
