package com.yuchew6.hw1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.yuchew6.hw1.R
import kotlinx.android.synthetic.main.fragment_play_song.*
import kotlin.random.Random


class NowPlayingFragment: Fragment() {

    private lateinit var theSong: Song
    private val randomNumber = Random.nextInt(1000, 10000)
    private var counter = randomNumber
    private val COUNT = "count"

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName

        const val THE_SONG = "the_song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            val song = args.getParcelable<Song>(THE_SONG)
            if (song != null) {
                this.theSong = song
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateSongView()

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                counter = getInt(COUNT)
                clickCount.text = "${counter.toString()} plays"
            }
        }

        btnPlay.setOnClickListener {
            counter += 1
            clickCount.text = "${counter.toString()} plays"
        }

        btnPrevious.setOnClickListener {
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        btnNext.setOnClickListener {
            Toast.makeText(context, "Skipping to Next track", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateSong(song: Song) {
        this.theSong = song
        updateSongView()
    }

    private fun updateSongView() {
        albumPic.setImageResource(theSong.largeImageID)
        songTile.text = theSong.title
        songAuthor.text = theSong.artist

        clickCount.text = "$randomNumber plays"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(COUNT, counter)
        super.onSaveInstanceState(outState)
    }
}