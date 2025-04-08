package com.danbi.listuicomponenttest.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.danbi.listuicomponenttest.databinding.ActivityListBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ListAdapterActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: ActivityListBinding
    private lateinit var listAdapter: CardListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListAdapter()
        observeUiState()

        viewModel.getCardList(init = true)

        lifecycleScope.launch {
            delay(5000)
            viewModel.getCardList(init = false)
        }
    }

    private fun setListAdapter() {
        binding.list.layoutManager = LinearLayoutManager(this)
        listAdapter = CardListAdapter()
        binding.list.adapter = listAdapter
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .map { it.list }
                    .distinctUntilChanged()
                    .collect { newList ->
                        listAdapter.submitList(newList)
                    }
            }
        }
    }
}
