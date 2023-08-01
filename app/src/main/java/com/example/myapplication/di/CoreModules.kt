package com.example.myapplication.di

import com.example.myapplication.core.data.api.RemoteDataSource
import com.example.myapplication.core.data.api.network.NetworkClient
import org.koin.dsl.module

val coremodules = module {
    single { NetworkClient.getApiService() }
    single { RemoteDataSource(get ()) }
}