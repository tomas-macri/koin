package com.example.koinannotations.data.utils

import com.example.koinannotations.data.modelo.ActorEntity
import com.example.koinannotations.data.modelo.MovieEntity
import com.example.koinannotations.domain.modelo.Actor
import com.example.koinannotations.domain.modelo.Movie


fun MovieEntity.toMovie() : Movie = Movie(this.id, this.title, this.overview ?:"", this.popularity, this.rating?: 0.0, this.posterPath?:"", this.budget)

fun Movie.toMovieEntity(): MovieEntity = MovieEntity(this.id, this.titulo, this.resumen, this.popularity, this.rating, this.posterPath, this.budget)

fun ActorEntity.toActor(): Actor = Actor(this.id, this.name, this.character, this.profile, this.department)