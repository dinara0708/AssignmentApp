package com.junodev.assignmentapp.ui.tiles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junodev.assignmentapp.domain.repository.TilesRepository
import com.junodev.assignmentapp.models.presentation.Tiles
import com.junodev.assignmentapp.utils.startCoroutineTimer
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TilesViewModel(
    private val repository: TilesRepository
) : ViewModel() {

    private val _tiles = MutableStateFlow(Tiles(emptyList()))
    val tiles: StateFlow<Tiles> = _tiles.asStateFlow()

    init {
        viewModelScope.launch {
            startCoroutineTimer(REPEAT_MILLS) { repository() }
        }
        getTiles()
    }

    fun deleteTile(number: Int) {
        viewModelScope.launch {
            repository.removeTile(number)
        }
    }

    private fun getTiles() {
        repository.tiles
            .filterNotNull()
            .onEach {
                _tiles.emit(it)
            }
            .launchIn(viewModelScope)
    }

    companion object {
        private const val REPEAT_MILLS = 5000L
    }
}
