package com.example.shalatku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SalatKuViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SalatKuUiState())
    val uiState: StateFlow<SalatKuUiState> = _uiState.asStateFlow()

    private var callCounter = 0 // Variabel private untuk menghitung pemanggilan

    fun onEvent(event: SalatKuEvent) {
        when (event) {
            is SalatKuEvent.PrayerToggled -> togglePrayer(event.name)
            SalatKuEvent.PrayersCleared -> clearPrayers()
            SalatKuEvent.PrayersMarkedAll -> markAllDone()
            is SalatKuEvent.DaySelected -> selectDay(event.index)
            SalatKuEvent.TasbihIncremented -> incrementTasbih()
            SalatKuEvent.TasbihReset -> resetTasbih()
            is SalatKuEvent.RoundSizeSelected -> setRoundSize(event.size)
            SalatKuEvent.PrayerTimesRequested -> loadPrayerTimes() // <-- Tambahkan ini
        }
    }

    private fun loadPrayerTimes() {
        // Jika sedang loading,abaikan pemanggilan baru
        if (_uiState.value.prayerTimes is PrayerTimesUiState.Loading) return

        viewModelScope.launch {
            // Set ke Loading
            _uiState.update { it.copy(prayerTimes = PrayerTimesUiState.Loading) }

            delay(1500) // Simulasi jeda network 1.5 detik

            callCounter++

            // Panggilan ganjil -> Success, panggilan genap -> Error
            if (callCounter % 2 != 0) {
                _uiState.update {
                    it.copy(prayerTimes = PrayerTimesUiState.Success(samplePrayers))
                }
            } else {
                _uiState.update {
                    it.copy(prayerTimes = PrayerTimesUiState.Error("Failed to load prayer times."))
                }
            }
        }
    }

    private fun togglePrayer(name: String) {
        _uiState.update { state ->
            val done = if (name in state.donePrayers) {
                state.donePrayers - name
            } else {
                state.donePrayers + name
            }
            state.copy(donePrayers = done)
        }
    }

    private fun clearPrayers() {
        _uiState.update { it.copy(donePrayers = emptyList()) }
    }

    private fun markAllDone() {
        _uiState.update { state ->
            state.copy(donePrayers = state.prayers.map { it.name })
        }
    }

    private fun selectDay(index: Int) {
        _uiState.update { it.copy(selectedDay = index) }
    }

    private fun incrementTasbih() {
        _uiState.update { state ->
            val next = state.tasbihCount + 1
            if (next == state.roundSize) {
                state.copy(tasbihCount = 0, completedRounds = state.completedRounds + 1)
            } else {
                state.copy(tasbihCount = next)
            }
        }
    }

    private fun resetTasbih() {
        _uiState.update { it.copy(tasbihCount = 0, completedRounds = 0) }
    }

    private fun setRoundSize(size: Int) {
        _uiState.update { it.copy(roundSize = size, tasbihCount = 0) }
    }
}