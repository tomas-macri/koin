package com.example.koinsimple.data.modelo

import com.example.koinsimple.data.common.DataConstants
import com.squareup.moshi.Json


data class MovieEntity(
    val id: Int = 0,
    val title: String,
    val overview: String?,
    val popularity: Double?,
    @field:Json(name = DataConstants.VOTE_AVERAGE) val rating: Double?,
    @field:Json(name = DataConstants.POSTER_PATH) val posterPath: String?,
    val budget: Int?,
)

