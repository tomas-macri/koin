package com.example.koinannotations.utils

import androidx.annotation.StringRes
import com.example.koinannotations.KoinAnnotationsApp
import org.koin.core.annotation.Single

@Single
class StringProvider() {

    fun getString(@StringRes stringResId: Int): String {
        //TODO: BUG en la release 1.3.0 de Koin Annotations - Al usar la CONFIG CHECK (arg("KOIN_CONFIG_CHECK","true"))
        // no encuentra la variable de tipo Context a pesar de inicializarla en el starKoin { }
        // See https://github.com/InsertKoinIO/koin-annotations/issues/94
        //     https://github.com/InsertKoinIO/koin-annotations/issues/96
        return KoinAnnotationsApp().applicationContext.getString(stringResId)
    }
}