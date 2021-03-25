package com.junodev.assignmentapp.data.repository

import com.junodev.assignmentapp.domain.repository.TilesRepository
import com.junodev.assignmentapp.models.presentation.Tiles
import com.junodev.assignmentapp.utils.extensions.insertAtRandomIndex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class TilesRepositoryImpl : TilesRepository {

    private val pool: PriorityQueue<Int> = PriorityQueue()
    private val _tiles = MutableStateFlow(initTiles())
    override val tiles: StateFlow<Tiles> = _tiles.asStateFlow()

    override fun invoke() {
        _tiles.tryEmit(_tiles.value.addTile())
    }

    override suspend fun removeTile(number: Int) {
        _tiles.emit(_tiles.value.deleteTile(number))
        pool.offer(number)
    }

    private fun Tiles.deleteTile(number: Int) = copy(numbers = numbers.filter { it != number })

    private fun Tiles.addTile(): Tiles {
        val number = getNextTileNumber()
        return copy(numbers = numbers.insertAtRandomIndex(number), lastAddedNumber = number)
    }

    private fun getNextTileNumber(): Int = extractFromPool() ?: createNumber()

    private fun extractFromPool(): Int? = pool.poll()

    private fun createNumber(): Int = _tiles.value.numbers.maxOrNull()?.inc() ?: 0

    private fun initTiles() = Tiles(List(INITIAL_TILE_COUNT) { it + 1 })

    companion object {
        private const val INITIAL_TILE_COUNT = 15
    }
}
