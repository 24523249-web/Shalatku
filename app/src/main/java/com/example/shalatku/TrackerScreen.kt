package com.example.shalatku

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TrackerScreen(
    prayers: List<Prayer>,
    donePrayers: List<String>,
    doneCount: Int,
    onToggle: (String) -> Unit,
    onClear: () -> Unit = {},
    onMarkAll: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Prayer Tracker", style = MaterialTheme.typography.headlineMedium)
        Text("$doneCount of ${prayers.size} prayers done today", style = MaterialTheme.typography.bodyLarge)

        Spacer(Modifier.height(8.dp))

        Row {
            OutlinedButton(onClick = onClear) {
                Text("Clear all")
            }
            Spacer(Modifier.width(8.dp))
            OutlinedButton(onClick = onMarkAll) {
                Text("Mark all")
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(prayers) { prayer ->
                val isChecked = prayer.name in donePrayers
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggle(prayer.name) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { onToggle(prayer.name) }
                    )
                    Text(text = prayer.name, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}