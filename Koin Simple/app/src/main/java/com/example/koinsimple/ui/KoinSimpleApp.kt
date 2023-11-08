package com.example.koinsimple.ui

import android.app.Application
import com.example.koinsimple.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinSimpleApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinSimpleApp)
            modules(applicationModule)
        }
    }
}