package com.example.data

import com.example.R
import com.example.model.CuratedStay
import com.example.model.ItineraryMoment
import com.example.model.StayBadgeType
import com.example.model.StayCategory
import com.example.model.TripBooking

object EditorialCatalog {

    val curatedStays: List<CuratedStay> = listOf(
        CuratedStay(
            id = "villa-terracotta-amalfi",
            title = "Villa Terracotta & Tidepool",
            region = "Amalfi Coast",
            country = "Italy",
            category = StayCategory.COASTAL,
            badgeType = StayBadgeType.ECO_VERIFIED,
            badgeLabel = "VERIFIED · ECO",
            pricePerNight = 420,
            rating = 4.96,
            reviewCount = 84,
            editorialSummary = "Sun-bleached lime plaster arches suspended above private limestone tidepools and wild olive groves.",
            architecturalNotes = "Restored 19th-century cliffside residence crafted with local hand-troweled terracotta clay, travertine sun terraces, and rainwater-fed plunge pools.",
            hostQuote = "Morning light arrives over the Tyrrhenian cove around 6:40 AM—take espresso down to the lower stone steps before the boats wake.",
            hostName = "Elena Marchetti · Architect & Host",
            imageResId = R.drawable.img_amalfi_cliffside_1794989588540,
            galleryResIds = listOf(
                R.drawable.img_amalfi_cliffside_1794989588540,
                R.drawable.img_aegean_cove_1794989589603,
                R.drawable.img_kyoto_sanctuary_1794989590091
            ),
            amenities = listOf("Private Tidepool Steps", "Solar Heated Pool", "Organic Linen Atelier", "Morning Boat Charter"),
            availableDateWindows = listOf("Nov 21–24", "Dec 04–08", "Dec 18–22", "Jan 09–13"),
            maxGuests = 4,
            isHeroPick = true,
            isMaritimeAccess = true,
            isEcoVerified = true
        ),
        CuratedStay(
            id = "riad-dar-maris",
            title = "Riad Dar Maris",
            region = "Medina Quarter",
            country = "Morocco",
            category = StayCategory.COURTYARD,
            badgeType = StayBadgeType.MARITIME,
            badgeLabel = "VERIFIED · COURTYARD",
            pricePerNight = 295,
            rating = 4.92,
            reviewCount = 112,
            editorialSummary = "Sculpted terracotta colonnades framing a tranquil emerald plunge pool and citrus courtyard.",
            architecturalNotes = "Traditional tadelakt lime-plaster arches paired with hand-glazed Fez zellij tilework and shaded cedar reading alcoves.",
            hostQuote = "When the afternoon heat peaks, the courtyard pool keeps the interior naturally cool by eight degrees without mechanical noise.",
            hostName = "Tariq Benali · Curator",
            imageResId = R.drawable.img_marrakech_riad_1794989589335,
            galleryResIds = listOf(
                R.drawable.img_marrakech_riad_1794989589335,
                R.drawable.img_amalfi_cliffside_1794989588540
            ),
            amenities = listOf("Courtyard Plunge Pool", "Rooftop Tea Salon", "Hammam Clay Ritual", "Artisan Breakfast"),
            availableDateWindows = listOf("Nov 28–Dec 01", "Dec 12–16", "Jan 03–07", "Jan 22–26"),
            maxGuests = 6,
            isHeroPick = false,
            isMaritimeAccess = false,
            isEcoVerified = true
        ),
        CuratedStay(
            id = "aegean-pergola-milos",
            title = "Pergola House on Milos",
            region = "Cyclades",
            country = "Greece",
            category = StayCategory.COASTAL,
            badgeType = StayBadgeType.PEAK_SEASON,
            badgeLabel = "PEAK SEASON",
            pricePerNight = 365,
            rating = 4.94,
            reviewCount = 67,
            editorialSummary = "Weathered chestnut pergola and travertine dining terrace floating directly above an Aegean harbor.",
            architecturalNotes = "Dry-stone Cycladic walls blended with raw ceramic tableware thrown in nearby Pollonia and linen sailcloth shading.",
            hostQuote = "Dusk paints the bay coral and sea-glass teal—the fishermen tie up below right as dinner hits the travertine table.",
            hostName = "Nikos & Rhea · Hosts",
            imageResId = R.drawable.img_aegean_cove_1794989589603,
            galleryResIds = listOf(
                R.drawable.img_aegean_cove_1794989589603,
                R.drawable.img_amalfi_cliffside_1794989588540
            ),
            amenities = listOf("Harbor Mooring", "Outdoor Clay Oven", "Kayak & Snorkel Kit", "Private Sea Terrace"),
            availableDateWindows = listOf("Nov 25–29", "Dec 06–10", "Dec 26–30", "Jan 14–18"),
            maxGuests = 4,
            isHeroPick = false,
            isMaritimeAccess = true,
            isEcoVerified = true
        ),
        CuratedStay(
            id = "kuroshio-cedar-pavilion",
            title = "Kuroshio Cedar Pavilion",
            region = "Izu Peninsula",
            country = "Japan",
            category = StayCategory.COASTAL,
            badgeType = StayBadgeType.ECO_VERIFIED,
            badgeLabel = "MARITIME · ECO",
            pricePerNight = 480,
            rating = 4.98,
            reviewCount = 53,
            editorialSummary = "Charred cedar and raw travertine sanctuary cantilevered over misty Pacific tidepools at dawn.",
            architecturalNotes = "Designed around passive geothermal ocean breezes, mineral onsen soaking basins, and hand-fired Bizen clay lanterns.",
            hostQuote = "Leave the shoji screens open to the salt mist at sunrise; the horizon blurs sea and sky into a single pastel wash.",
            hostName = "Kenji Takahashi · Studio Principal",
            imageResId = R.drawable.img_kyoto_sanctuary_1794989590091,
            galleryResIds = listOf(
                R.drawable.img_kyoto_sanctuary_1794989590091,
                R.drawable.img_aegean_cove_1794989589603
            ),
            amenities = listOf("Mineral Sea Onsen", "Cedar Sound Room", "Seasonal Kaiseki", "Coastal Trail Access"),
            availableDateWindows = listOf("Nov 20–23", "Dec 02–06", "Dec 19–23", "Jan 11–15"),
            maxGuests = 2,
            isHeroPick = false,
            isMaritimeAccess = true,
            isEcoVerified = true
        )
    )

    val defaultSavedIds: Set<String> = setOf("villa-terracotta-amalfi", "kuroshio-cedar-pavilion")

    val defaultBooking: TripBooking = TripBooking(
        id = "booking-seed-amalfi",
        stayId = "villa-terracotta-amalfi",
        stayTitle = "Villa Terracotta & Tidepool",
        region = "Amalfi Coast, Italy",
        imageResId = R.drawable.img_amalfi_cliffside_1794989588540,
        dateWindow = "Nov 21–24",
        guests = 2,
        nights = 3,
        totalPrice = 1260
    )

    val defaultItineraryMoments: List<ItineraryMoment> = listOf(
        ItineraryMoment(
            id = "m-1",
            dayIndex = 0,
            dayLabel = "Day 1 · Arrival",
            time = "02:30 PM",
            title = "Terracotta Cliff Check-In & Lemon Aperitivo",
            location = "Villa Terracotta Lower Terrace",
            statusLabel = "CONFIRMED",
            isUrgentOrSunset = false
        ),
        ItineraryMoment(
            id = "m-2",
            dayIndex = 0,
            dayLabel = "Day 1 · Arrival",
            time = "05:15 PM",
            title = "Golden Hour Tidepool Swim",
            location = "Private Limestone Steps",
            statusLabel = "SUNSET SLOT",
            isUrgentOrSunset = true
        ),
        ItineraryMoment(
            id = "m-3",
            dayIndex = 0,
            dayLabel = "Day 1 · Arrival",
            time = "08:00 PM",
            title = "Candlelit Seafood & Handmade Pasta",
            location = "Trattoria del Porto, Praiano",
            statusLabel = "RESERVED",
            isUrgentOrSunset = false
        ),
        ItineraryMoment(
            id = "m-4",
            dayIndex = 1,
            dayLabel = "Day 2 · Cove",
            time = "08:30 AM",
            title = "Wooden Gozzo Boat Charter Along the Cliffs",
            location = "Marina di Praia Pier",
            statusLabel = "MARITIME",
            isUrgentOrSunset = false
        ),
        ItineraryMoment(
            id = "m-5",
            dayIndex = 1,
            dayLabel = "Day 2 · Cove",
            time = "01:00 PM",
            title = "Seaside Olive Grove Picnic",
            location = "Conca dei Marini Cove",
            statusLabel = "CONFIRMED",
            isUrgentOrSunset = false
        ),
        ItineraryMoment(
            id = "m-6",
            dayIndex = 1,
            dayLabel = "Day 2 · Cove",
            time = "06:00 PM",
            title = "Ceramic Workshop & Aperitivo",
            location = "Vietri Clay Atelier",
            statusLabel = "SUNSET SLOT",
            isUrgentOrSunset = true
        ),
        ItineraryMoment(
            id = "m-7",
            dayIndex = 2,
            dayLabel = "Day 3 · Atelier",
            time = "09:00 AM",
            title = "Morning Walk Along Sentiero degli Dei",
            location = "Bomerano Ridge Trail",
            statusLabel = "ECO TRAIL",
            isUrgentOrSunset = false
        ),
        ItineraryMoment(
            id = "m-8",
            dayIndex = 2,
            dayLabel = "Day 3 · Atelier",
            time = "03:30 PM",
            title = "Linen & Botanical Pressing Journal Session",
            location = "Villa Sun Courtyard",
            statusLabel = "CONFIRMED",
            isUrgentOrSunset = false
        )
    )
}
