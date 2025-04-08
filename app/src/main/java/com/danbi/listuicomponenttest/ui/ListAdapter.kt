package com.danbi.listuicomponenttest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danbi.listuicomponenttest.Card
import com.danbi.listuicomponenttest.databinding.ViewCardBinding

class CardListAdapter : ListAdapter<Card, CardListAdapter.CardViewHolder>(CardDiffCallback()) {

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
        val card = getItem(position)
        holder.bind(card)
    }
}

class CardDiffCallback : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}
