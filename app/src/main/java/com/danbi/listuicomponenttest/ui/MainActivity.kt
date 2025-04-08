package com.danbi.listuicomponenttest.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.danbi.listuicomponenttest.ListSideEffect
import com.danbi.listuicomponenttest.Route

import com.danbi.listuicomponenttest.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.setOnClickListener {
            viewModel.clickNavigateToList(Route.RECYCLERVIEW)
        }
        binding.listAdapter.setOnClickListener {
            viewModel.clickNavigateToList(Route.LIST_ADAPTER)
        }
        binding.lazyColumn.setOnClickListener {
            viewModel.clickNavigateToList(Route.LAZY_COLUMN)
        }

        observeSideEffect()
    }


    private fun observeSideEffect() = lifecycleScope.launch {
        viewModel.effect.collect {
            when (it) {
                is ListSideEffect.NavigateToList -> {
                    when(it.route) {
                        Route.RECYCLERVIEW -> startActivity(Intent(this@MainActivity, RecyclerViewActivity::class.java))
                        Route.LIST_ADAPTER -> startActivity(Intent(this@MainActivity, ListAdapterActivity::class.java))
                        Route.LAZY_COLUMN -> startActivity(Intent(this@MainActivity, LazyColumnActivity::class.java))
                    }
                }
            }
        }
    }
}
