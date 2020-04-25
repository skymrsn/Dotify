package com.yuchew6.hw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        title = "All Songs"

        // Given song object
        lateinit var theSong: Song
        // Config of Song list
        val allSongs: List<Song> = SongDataProvider.getAllSongs()
        val songListAdapter = SongListAdapter(allSongs)

        songList.adapter = songListAdapter
        songListAdapter.onSongClickListener = {song ->
            miniPlayer.text = "${song.title} - ${song.artist}"
            theSong = song
        }
        songListAdapter.onSongLongClickListener = {song, currList ->
            var newList = currList.toMutableList().apply{remove(song)}
            songListAdapter.change(newList)
            if (song == theSong) {
                miniPlayer.text = ""
            }

            var deleteName = song.title
            Toast.makeText(this, "`$deleteName` was deleted", Toast.LENGTH_SHORT).show()
            true;
        }

        // Shuffle Button
        btnShuffle.setOnClickListener {
            var newList = allSongs.toMutableList().apply{shuffle()}
            songListAdapter.change(newList)
        }

        // Start Player
        miniPlayer.setOnClickListener {
            if (theSong != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("song_key", theSong)
                startActivity(intent)
            }
        }
    }
}
