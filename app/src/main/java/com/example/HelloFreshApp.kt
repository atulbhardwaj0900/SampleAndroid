package com.example

import android.app.Application
import com.hellofresh.task2.di.remoteDataModule
import com.hellofresh.task2.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HelloFreshApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@HelloFreshApp)
            modules(listOf(viewModels, remoteDataModule))
        }
    }
}