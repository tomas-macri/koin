package com.example.koinsimple

import android.content.Context
import com.example.koinsimple.di.applicationModule
import org.junit.Test
import org.koin.test.verify.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class KoinUnitTest {

    @Test
    fun applicationModule_isCorrect() {
        //TODO: org.koin.test.verify.verify method is experimental

        //Verifica que todas las clases que definimos se puedan inyectar, quitando el Context que se inyecta en el método startKoin{ }

        //¡¡ESTO NO VERIFICA QUE HAYA INSTANCIAS CREADAS DE
        // CLASES QUE NO SE ENCUENTREN EN NINGÚN CONSTRUCTOR (por ejemplo Retrofit, los Interceptor u OkHttpClient)!!

        applicationModule.verify(extraTypes = listOf(Context::class))
    }
}