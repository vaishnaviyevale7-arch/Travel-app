package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.ItineraryMoment
import com.example.model.TripBooking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.travelDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "terracotta_tide_prefs"
)

data class PersistedTravelData(
    val savedStayIds: Set<String>,
    val completedMomentIds: Set<String>,
    val customMoments: List<ItineraryMoment>,
    val extraBookings: List<TripBooking>,
    val ecoOnlyFilter: Boolean,
    val maritimeOnlyFilter: Boolean,
    val darkPreviewOverride: Boolean?
)

class TravelPreferencesRepository(private val context: Context) {

    private object Keys {
        val SAVED_STAY_IDS = stringSetPreferencesKey("saved_stay_ids")
        val HAS_INIT_SAVED = booleanPreferencesKey("has_init_saved")
        val COMPLETED_MOMENT_IDS = stringSetPreferencesKey("completed_moment_ids")
        val CUSTOM_MOMENTS = stringSetPreferencesKey("custom_moments")
        val EXTRA_BOOKINGS = stringSetPreferencesKey("extra_bookings")
        val ECO_ONLY = booleanPreferencesKey("eco_only_filter")
        val MARITIME_ONLY = booleanPreferencesKey("maritime_only_filter")
        val DARK_PREVIEW = stringPreferencesKey("dark_preview_override")
    }

    val persistedDataFlow: Flow<PersistedTravelData> = context.travelDataStore.data.map { prefs ->
        val hasInitSaved = prefs[Keys.HAS_INIT_SAVED] ?: false
        val savedIds = if (hasInitSaved) {
            prefs[Keys.SAVED_STAY_IDS] ?: emptySet()
        } else {
            EditorialCatalog.defaultSavedIds
        }

        val completedIds = prefs[Keys.COMPLETED_MOMENT_IDS] ?: emptySet()
        val customMoments = (prefs[Keys.CUSTOM_MOMENTS] ?: emptySet())
            .mapNotNull { decodeMoment(it) }
        val extraBookings = (prefs[Keys.EXTRA_BOOKINGS] ?: emptySet())
            .mapNotNull { decodeBooking(it) }

        val darkOverride = when (prefs[Keys.DARK_PREVIEW]) {
            "true" -> true
            "false" -> false
            else -> null
        }

        PersistedTravelData(
            savedStayIds = savedIds,
            completedMomentIds = completedIds,
            customMoments = customMoments,
            extraBookings = extraBookings,
            ecoOnlyFilter = prefs[Keys.ECO_ONLY] ?: false,
            maritimeOnlyFilter = prefs[Keys.MARITIME_ONLY] ?: false,
            darkPreviewOverride = darkOverride
        )
    }

    suspend fun toggleSavedStay(stayId: String) {
        context.travelDataStore.edit { prefs ->
            val hasInit = prefs[Keys.HAS_INIT_SAVED] ?: false
            val current = if (hasInit) {
                prefs[Keys.SAVED_STAY_IDS] ?: emptySet()
            } else {
                EditorialCatalog.defaultSavedIds
            }
            val updated = if (stayId in current) current - stayId else current + stayId
            prefs[Keys.HAS_INIT_SAVED] = true
            prefs[Keys.SAVED_STAY_IDS] = updated
        }
    }

    suspend fun toggleMomentCompleted(momentId: String) {
        context.travelDataStore.edit { prefs ->
            val current = prefs[Keys.COMPLETED_MOMENT_IDS] ?: emptySet()
            prefs[Keys.COMPLETED_MOMENT_IDS] =
                if (momentId in current) current - momentId else current + momentId
        }
    }

    suspend fun addCustomMoment(moment: ItineraryMoment) {
        context.travelDataStore.edit { prefs ->
            val current = prefs[Keys.CUSTOM_MOMENTS] ?: emptySet()
            prefs[Keys.CUSTOM_MOMENTS] = current + encodeMoment(moment)
        }
    }

    suspend fun addBooking(booking: TripBooking) {
        context.travelDataStore.edit { prefs ->
            val current = prefs[Keys.EXTRA_BOOKINGS] ?: emptySet()
            prefs[Keys.EXTRA_BOOKINGS] = current + encodeBooking(booking)
        }
    }

    suspend fun setEcoOnlyFilter(enabled: Boolean) {
        context.travelDataStore.edit { prefs ->
            prefs[Keys.ECO_ONLY] = enabled
        }
    }

    suspend fun setMaritimeOnlyFilter(enabled: Boolean) {
        context.travelDataStore.edit { prefs ->
            prefs[Keys.MARITIME_ONLY] = enabled
        }
    }

    suspend fun setDarkPreviewOverride(dark: Boolean?) {
        context.travelDataStore.edit { prefs ->
            if (dark == null) {
                prefs.remove(Keys.DARK_PREVIEW)
            } else {
                prefs[Keys.DARK_PREVIEW] = dark.toString()
            }
        }
    }

    private fun sanitize(input: String): String = input.replace("|", "/")

    private fun encodeMoment(m: ItineraryMoment): String {
        return listOf(
            sanitize(m.id),
            m.dayIndex.toString(),
            sanitize(m.dayLabel),
            sanitize(m.time),
            sanitize(m.title),
            sanitize(m.location),
            sanitize(m.statusLabel)
        ).joinToString("|")
    }

    private fun decodeMoment(raw: String): ItineraryMoment? {
        val parts = raw.split("|")
        if (parts.size < 7) return null
        val dayIdx = parts[1].toIntOrNull() ?: return null
        return ItineraryMoment(
            id = parts[0],
            dayIndex = dayIdx,
            dayLabel = parts[2],
            time = parts[3],
            title = parts[4],
            location = parts[5],
            statusLabel = parts[6],
            isUrgentOrSunset = false,
            isCompleted = false,
            isCustom = true
        )
    }

    private fun encodeBooking(b: TripBooking): String {
        return listOf(
            sanitize(b.id),
            sanitize(b.stayId),
            sanitize(b.stayTitle),
            sanitize(b.region),
            b.imageResId.toString(),
            sanitize(b.dateWindow),
            b.guests.toString(),
            b.nights.toString(),
            b.totalPrice.toString()
        ).joinToString("|")
    }

    private fun decodeBooking(raw: String): TripBooking? {
        val parts = raw.split("|")
        if (parts.size < 9) return null
        val imgRes = parts[4].toIntOrNull() ?: return null
        val guests = parts[6].toIntOrNull() ?: 2
        val nights = parts[7].toIntOrNull() ?: 3
        val total = parts[8].toIntOrNull() ?: 900
        return TripBooking(
            id = parts[0],
            stayId = parts[1],
            stayTitle = parts[2],
            region = parts[3],
            imageResId = imgRes,
            dateWindow = parts[5],
            guests = guests,
            nights = nights,
            totalPrice = total
        )
    }
}
