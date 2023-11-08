package com.example.koinsimple.di

import org.koin.dsl.module


//This module gathers the definition of the other three modules in order to pass a test before compiling the app
val applicationModule = module {
    includes(useCasesModule, viewModelModule, dataModule)
}
