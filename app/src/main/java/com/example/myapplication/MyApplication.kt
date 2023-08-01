package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.coremodules
import com.example.myapplication.di.repositorymodules
import com.example.myapplication.di.viewmodelmodules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    viewmodelmodules,
                    coremodules,
                    repositorymodules,
                )
            )
        }
    }
}