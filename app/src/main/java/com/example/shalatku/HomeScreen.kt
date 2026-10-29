package com.example.shalatku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    prayers: List<Prayer>,
    selectedDay: Int = 0,
    onSelectDay: (Int) -> Unit = {},
    onSelectToday: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Home Screen", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        Text("Pilih Hari:", style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = selectedDay == -1,
                onClick = { onSelectDay(-1) },
                label = { Text("Kemarin") }
            )
            FilterChip(
                selected = selectedDay == 0,
                onClick = { onSelectDay(0) },
                label = { Text("Hari Ini") }
            )
            FilterChip(
                selected = selectedDay == 1,
                onClick = { onSelectDay(1) },
                label = { Text("Besok") }
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(onClick = onSelectToday) {
            Text("Today")
        }

        Spacer(Modifier.height(16.dp))
        Text("Hari terpilih: $selectedDay", style = MaterialTheme.typography.titleMedium)
    }
}