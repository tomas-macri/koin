package com.example.koinannotations

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module


class KoinAnnotationsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinAnnotationsApp)
            modules(AppModule().module)
        }
    }
}

@Module
@ComponentScan //Se puede especificar la ruta del paquete que abarcará el módulo o, por defecto, se coge la ruta de la clase
class AppModule

