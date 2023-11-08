package com.example.koinsimple.data.utils

import com.example.koinsimple.data.modelo.ActorEntity
import com.example.koinsimple.data.modelo.MovieEntity
import com.example.koinsimple.domain.modelo.Actor
import com.example.koinsimple.domain.modelo.Movie


fun MovieEntity.toMovie() : Movie = Movie(this.id, this.title, this.overview ?:"", this.popularity, this.rating?: 0.0, this.posterPath?:"", this.budget)

fun ActorEntity.toActor(): Actor = Actor(this.id, this.name, this.character, this.profile, this.department)