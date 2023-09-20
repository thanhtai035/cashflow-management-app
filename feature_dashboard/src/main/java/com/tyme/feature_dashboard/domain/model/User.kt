package com.tyme.feature_dashboard.domain.model

import com.tyme.feature_dashboard.presentation.util.Card

@kotlinx.serialization.Serializable
data class User(
    val balance: Double,
    val debitCard: String?,
    val firstName: String,
    val lastName: String,
    val loaningCard: String?,
    val savingCard: String?,
    val username: String
) {
    fun getCards() : ArrayList<Card> {
        val cards: ArrayList<Card> = ArrayList<Card>()
        cards.add(Card.Credit) // Every Account has Credit Card
        if (savingCard != null)
            cards.add(Card.Saving)
        if (loaningCard!= null)
            cards.add(Card.Loaning)

//        if (debitCard != null)
//            cards.add(Card.Debit)
        return cards
    }
}