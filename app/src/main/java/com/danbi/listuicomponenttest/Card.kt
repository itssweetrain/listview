package com.danbi.listuicomponenttest

const val INIT_CARD_SIZE_SMALL = 300000
const val INIT_CARD_SIZE_MEDIUM = 500000
const val INIT_CARD_SIZE_LARGE = 800000

data class Card(
    val id: Int,
    val name: String,
    val description: String,
    val date: String,
    val category: String,
    val price: Double,
    val imageUrl: String,
    val address: String,
    val phone: String,
    val email: String,
)

fun getCards(): MutableList<Card> {
    return generateCards(INIT_CARD_SIZE_MEDIUM)
}

fun generateCards(size: Int): MutableList<Card> {
    return MutableList(size) { index ->
        Card(
            id = index,
            name = "Card $index",
            description = "This is card number $index",
            date = "2025-03-31",
            category = "Category ${index % 10}",
            price = (index * 0.1) + 100,
            imageUrl = "",
            address = "Address $index",
            phone = "123-456-$index",
            email = "card$index@example.com",
        )
    }
}
