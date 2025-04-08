package com.danbi.listuicomponenttest.ui

import androidx.compose.foundation.background
import androidx.compose.material3.Text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danbi.listuicomponenttest.Card

@Composable
fun CardList(cards: List<Card>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        itemsIndexed(
            items = cards,
            key = { _, card -> card.id },
        ) { _, card ->
            Card(card)
        }
    }
}

@Composable
fun Card(card: Card) {
    Row {
        Column {
            Text(text = card.name, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(text = card.description, fontSize = 30.sp)
        }
    }
}
