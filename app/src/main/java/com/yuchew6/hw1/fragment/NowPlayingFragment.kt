package com.yuchew6.hw1.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.squareup.picasso.Picasso
import com.yuchew6.hw1.DotifyApp
import com.yuchew6.hw1.R
import com.yuchew6.hw1.manager.MusicManager
import kotlinx.android.synthetic.main.fragment_play_song.*


class NowPlayingFragment: Fragment() {

    // private lateinit var theSong: Song
    // private val randomNumber = Random.nextInt(1000, 10000)
    // private var counter = randomNumber
    // private val COUNT = "count"
    private lateinit var musicManager: MusicManager

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName

        //const val THE_SONG = "the_song"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        musicManager = (context.applicationContext as DotifyApp).musicManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments?.let { args ->
//            val song = args.getParcelable<Song>(THE_SONG)
//            if (song != null) {
//                this.theSong = song
//            }
//        }
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

//        if (savedInstanceState != null) {
//            with(savedInstanceState) {
//                counter = getInt(COUNT)
//                clickCount.text = "${counter.toString()} plays"
//            }
//        }

        btnPlay.setOnClickListener {
            musicManager.addClick()
            clickCount.text = "${musicManager.getClick()} plays"
        }

        btnPrevious.setOnClickListener {
            musicManager.prevSong()
            updateSongView()
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        btnNext.setOnClickListener {
            musicManager.nextSong()
            updateSongView()
            Toast.makeText(context, "Skipping to Next track", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateSong(song: Song) {
        // this.theSong = song
        updateSongView()
    }

    private fun updateSongView() {
        val theSong = musicManager.currentSong

        if (theSong != null) {
            if (theSong.largeImageID != 2131099801) {
                albumPic.setImageResource(theSong.largeImageID)
                progressBar.visibility = View.INVISIBLE
            } else {
                Picasso.get().load(theSong.id).into(albumPic);
                showPic()
            }
            songTile.text = theSong.title
            songAuthor.text = theSong.artist

            clickCount.text = "${musicManager.getClick()} plays"
        }
    }

    private fun showPic() {
        //progressBar.visibility = View.INVISIBLE
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putInt(COUNT, counter)
//        super.onSaveInstanceState(outState)
//    }
}