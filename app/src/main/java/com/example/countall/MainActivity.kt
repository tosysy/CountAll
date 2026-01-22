package com.example.countall

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.countall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var counterAdapter: CounterAdapter
    private val counters = mutableListOf<Counter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupButtons()

        // Add initial counter
        counterAdapter.addCounter()
    }

    private fun setupRecyclerView() {
        counterAdapter = CounterAdapter(counters) { position ->
            counterAdapter.removeCounter(position)
        }

        binding.recyclerViewCounters.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = counterAdapter
        }
    }

    private fun setupButtons() {
        binding.buttonAddCounter.setOnClickListener {
            counterAdapter.addCounter()
            binding.recyclerViewCounters.smoothScrollToPosition(counters.size - 1)
        }
    }
}
