package com.junodev.assignmentapp.domain.repository

import com.junodev.assignmentapp.models.presentation.Tiles
import kotlinx.coroutines.flow.Flow

interface TilesRepository {

    val tiles: Flow<Tiles>

    suspend fun removeTile(number: Int)

    operator fun invoke()
}
