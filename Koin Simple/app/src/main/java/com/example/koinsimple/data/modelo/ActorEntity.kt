package com.example.koinsimple.data.modelo

import com.example.koinsimple.data.common.DataConstants
import com.squareup.moshi.Json

data class  ActorEntity(
    val id: Int,
    val name: String,
    val character: String,
    @field:Json(name = DataConstants.PROFILE_PATH) val profile: String?,
    @field:Json(name = DataConstants.KNOWN_FOR_DEPARTMENT) val department: String,
)