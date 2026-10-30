package com.example.shalatku

sealed interface SalatKuEvent {
    data class PrayerToggled(val name: String) : SalatKuEvent
    data object PrayersCleared : SalatKuEvent
    data object PrayersMarkedAll : SalatKuEvent // <-- Tambahkan event baru ini
    data class DaySelected(val index: Int) : SalatKuEvent
    data object TasbihIncremented : SalatKuEvent
    data object TasbihReset : SalatKuEvent
    data class RoundSizeSelected(val size: Int) : SalatKuEvent
}