package com.example.countall

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countall.databinding.ItemCounterBinding

class CounterAdapter(
    private val counters: MutableList<Counter>,
    private val onCounterDeleted: (Int) -> Unit
) : RecyclerView.Adapter<CounterAdapter.CounterViewHolder>() {

    inner class CounterViewHolder(private val binding: ItemCounterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var textWatcher: TextWatcher? = null

        fun bind(counter: Counter, position: Int) {
            // Remove previous TextWatcher to avoid conflicts
            textWatcher?.let { binding.editTextCounterName.removeTextChangedListener(it) }

            // Set current values
            binding.editTextCounterName.setText(counter.name)
            binding.textViewCounterValue.text = counter.value.toString()

            // Create new TextWatcher for name changes
            textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    counter.name = s.toString()
                }
            }
            binding.editTextCounterName.addTextChangedListener(textWatcher)

            // Increment button
            binding.buttonIncrement.setOnClickListener {
                counter.value++
                binding.textViewCounterValue.text = counter.value.toString()
            }

            // Decrement button
            binding.buttonDecrement.setOnClickListener {
                counter.value--
                binding.textViewCounterValue.text = counter.value.toString()
            }

            // Delete button
            binding.buttonDelete.setOnClickListener {
                onCounterDeleted(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterViewHolder {
        val binding = ItemCounterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CounterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CounterViewHolder, position: Int) {
        holder.bind(counters[position], position)
    }

    override fun getItemCount(): Int = counters.size

    fun addCounter() {
        counters.add(Counter())
        notifyItemInserted(counters.size - 1)
    }

    fun removeCounter(position: Int) {
        counters.removeAt(position)
        notifyItemRemoved(position)
    }
}
