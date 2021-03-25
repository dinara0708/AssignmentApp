package com.junodev.assignmentapp.di

import android.app.Application
import com.junodev.assignmentapp.di.modules.repositoryModule
import com.junodev.assignmentapp.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object DI {

    fun initDI(application: Application) {
        startKoin {
            androidContext(application)
            modules(
                viewModelModule,
                repositoryModule
            )
        }
    }
}
