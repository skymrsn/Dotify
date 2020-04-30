package com.yuchew6.hw1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlin.random.Random
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.yuchew6.hw1.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val randomNumber = Random.nextInt(1000, 10000)
    var counter = randomNumber

    // input from songList
    companion object {
        const val SONG_KEY = "song_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Back Button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // random play numbers
        clickCount.text = "$randomNumber plays"

        // get data from input song
        val song = intent.getParcelableExtra<Song>(SONG_KEY)
        val title = song.title
        val artistName = song.artist
        val albumPicure = song.largeImageID

        // deploy data
        albumPic.setImageResource(albumPicure)
        songTile.text = title
        songAuthor.text = artistName

        btnChange.setOnClickListener {
            userName.visibility = View.INVISIBLE
            inputUserName.visibility = View.VISIBLE
            btnChange.visibility = View.INVISIBLE
            btnApply.visibility = View.VISIBLE
        }

        btnApply.setOnClickListener {
            if (inputUserName.text.toString().isEmpty()) {
                Toast.makeText(this, "Must input something", Toast.LENGTH_SHORT).show()
            } else {
                userName.text = inputUserName.text
                userName.visibility = View.VISIBLE
                inputUserName.visibility = View.INVISIBLE
                btnApply.visibility = View.INVISIBLE
                btnChange.visibility = View.VISIBLE
            }
        }

        btnPlay.setOnClickListener {
            counter += 1
            clickCount.text = "${counter.toString()} plays"
        }

        btnPrevious.setOnClickListener {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        btnNext.setOnClickListener {
            Toast.makeText(this, "Skipping to Next track", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
