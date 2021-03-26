package com.junodev.assignmentapp.ui.tiles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.junodev.assignmentapp.R
import com.junodev.assignmentapp.ui.tiles.adapter.TilesAdapter
import com.junodev.assignmentapp.utils.extensions.setupGridLayout
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class TilesFragment : Fragment(R.layout.fragment_tiles) {

    private val viewModel: TilesViewModel by viewModel()
    private lateinit var tilesAdapter: TilesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.initViews()
        initCollector()
    }

    private fun View.initViews() {
        tilesAdapter = TilesAdapter(::deleteTile)
        val recycler = findViewById<RecyclerView>(R.id.tiles_recycler_view)
        with(recycler) {
            setupGridLayout(requireContext(), resources.getInteger(R.integer.span_count))
            adapter = tilesAdapter
        }
    }

    private fun initCollector() {
        viewModel.tiles
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { tiles -> tilesAdapter.bind(tiles) }
            .launchIn(lifecycleScope)
    }

    private fun deleteTile(number: Int) {
        viewModel.deleteTile(number)
    }
}
