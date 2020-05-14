package com.yuchew6.hw1.model

data class AllSongs (
    val title: String,
    val numOfSongs: Int,
    val songs: List<SongChoice>
)