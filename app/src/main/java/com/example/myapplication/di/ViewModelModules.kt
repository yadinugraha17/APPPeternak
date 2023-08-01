package com.example.myapplication.di

import com.example.myapplication.ui.login.LoginViewModel
import org.koin.dsl.module

val viewmodelmodules = module { single { LoginViewModel(get()) } }