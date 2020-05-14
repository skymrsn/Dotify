package com.yuchew6.hw1.manager

import com.ericchee.songdataprovider.Song

interface SongRecommendListner {
    fun enterPlaying()

    fun update(song: Song)
}