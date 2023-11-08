package com.example.koinsimple.utils

import android.content.Context
import androidx.annotation.StringRes

class StringProvider (private val context: Context) {


    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

}