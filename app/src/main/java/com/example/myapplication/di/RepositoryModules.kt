package com.example.myapplication.di

import com.example.myapplication.core.repository.CoreRepository
import org.koin.dsl.module

val repositorymodules = module { single { CoreRepository(get ()) } }