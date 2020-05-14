package com.yuchew6.hw1.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.yuchew6.hw1.DotifyApp
import com.yuchew6.hw1.R
import com.yuchew6.hw1.SongListAdapter
import com.yuchew6.hw1.manager.MusicManager
import kotlinx.android.synthetic.main.fragment_list_songs.*

class SongListFragment : Fragment() {
    private lateinit var songListAdapter: SongListAdapter
    private var onSongClickListener: OnSongClickListener? = null
    // no need local songs; see application
    //lateinit var songs: List<Song>
    private lateinit var musicManager: MusicManager

    companion object {
        // const val ALL_SONG = "all_song"
        // const val LIST = "list"
        val TAG: String = SongListFragment::class.java.simpleName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        musicManager = (context.applicationContext as DotifyApp).musicManager

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // no need to pass songs, get it from application
//        arguments?.let { args ->
//            var songList = args.getParcelableArrayList<Song>(ALL_SONG)
//            if (songList != null) {
//                this.songs = songList
//            }
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // no need to restore songs, get it from application
//        if (savedInstanceState != null) {
//            with(savedInstanceState) {
//                var songLists = getParcelableArrayList<Song>(LIST)
//                songs = songLists as List<Song>
//            }
//        }
        // render the list
        songListAdapter = SongListAdapter(musicManager.allSongs) // from application
        songList.adapter = songListAdapter

        // click the list
        songListAdapter.onSongClickListener = {song, theIndex ->
            onSongClickListener?.onSongClicked(song, theIndex)
        }

        // long press the list
        songListAdapter.onSongLongClickListener = {song, currList ->
            var newList = currList.toMutableList().apply{remove(song)}
            songListAdapter.change(newList)
            musicManager.allSongs = newList
            onSongClickListener?.onSongLongClicked(song)
            true
        }
    }
    // shuffle
    fun shuffleList() {
        var newList = musicManager.allSongs.toMutableList().apply{shuffle()}
        musicManager.allSongs = newList
        // songs = newList
        // renew Index
        musicManager.renewIndex()
        songListAdapter.change(newList)
    }

    // no need to restore songs
//    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putParcelableArrayList(LIST, songs as ArrayList)
//        super.onSaveInstanceState(outState)
//    }

}

interface OnSongClickListener {
    fun onSongClicked(song: Song, theIndex: Int)

    fun onSongLongClicked(song: Song)
}