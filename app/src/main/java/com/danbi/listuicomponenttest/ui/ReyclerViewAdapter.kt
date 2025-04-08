package com.danbi.listuicomponenttest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danbi.listuicomponenttest.Card
import com.danbi.listuicomponenttest.databinding.ViewCardBinding

class RecyclerViewAdapter(private var cards: MutableList<Card>) : RecyclerView.Adapter<RecyclerViewAdapter.CardViewHolder>() {

    inner class CardViewHolder(private val binding: ViewCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            binding.apply {
                txtName.text = card.name
                txtDescription.text = card.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ViewCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.bind(card)
    }

    override fun getItemCount(): Int = cards.size

    fun updateData(newCards: List<Card>) {
        cards.clear()
        cards.addAll(newCards)
        this.cards = newCards.toMutableList()
        notifyDataSetChanged()
    }
}
