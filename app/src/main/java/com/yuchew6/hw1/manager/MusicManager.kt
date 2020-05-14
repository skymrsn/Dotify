package com.yuchew6.hw1.manager

import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.yuchew6.hw1.model.SongChoice

class MusicManager {
     var currentSong: Song? = null
     var songRecommendListener: SongRecommendListner? = null
     var index:Int = 0
     var allSongs: List<Song> = SongDataProvider.getAllSongs()
     var map: MutableMap<String, Int> = mutableMapOf()

    init {
        for (x in allSongs.indices) {
            map[allSongs[x].id] = 0
        }
    }

    fun getClick(): Int? {
        return map[currentSong?.id]
    }

    fun addClick() {
        map[currentSong!!.id]?.plus(1)?.let { map.put(currentSong?.id!!, it) }
    }

    fun recommendSong(theSong: SongChoice, theIndex: Int) {
        val title = theSong.title
        val artist = theSong.artist
        val uRL = theSong.largeImageURL

        val recommendation: Song = SongDataProvider.createSong(title, artist, uRL)
        // update song and UI
        songRecommendListener?.update(recommendation)
        currentSong = recommendation
        // update Index
        index = theIndex
        songRecommendListener?.enterPlaying()
    }

    fun renewIndex() {
        if (currentSong != null) {
            index = allSongs.indexOf(currentSong!!)
        }
    }

    fun prevSong() {
        if (index - 1 >= 0) {
            index -= 1;
            currentSong = allSongs[index]
        }
    }

    fun nextSong() {
        if (index + 1 < allSongs.size) {
            index += 1;
            currentSong = allSongs[index]
        }
    }
}