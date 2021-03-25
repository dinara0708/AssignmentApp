package com.junodev.assignmentapp.di.modules

import com.junodev.assignmentapp.data.repository.TilesRepositoryImpl
import com.junodev.assignmentapp.domain.repository.TilesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TilesRepository> { TilesRepositoryImpl() }
}