package com.junodev.assignmentapp.ui.tiles.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.junodev.assignmentapp.R
import com.junodev.assignmentapp.models.presentation.Tiles
import com.junodev.assignmentapp.utils.extensions.setDebounceOnClickListener

class TilesAdapter(
    private val listener: (Int) -> Unit
) : RecyclerView.Adapter<TileViewHolder>() {

    private var numbers = listOf<Int>()
    private var lastAddedNumber: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        return TileViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tile, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        val number = numbers[position]
        val isDefaultBackground = number != lastAddedNumber

        holder.onBind(number, isDefaultBackground)
        holder.itemView.findViewById<FloatingActionButton>(R.id.delete_tile_button)
            .setDebounceOnClickListener(DEBOUNCE_TIME) {
                listener.invoke(number)
            }
    }

    override fun getItemCount(): Int = numbers.size

    fun bind(tiles: Tiles) {
        val diffCallback = TilesDiffUtilCallback(numbers, tiles.numbers, lastAddedNumber)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)

        numbers = tiles.numbers
        lastAddedNumber = tiles.lastAddedNumber
    }

    companion object {
        private const val DEBOUNCE_TIME = 500L
    }
}
