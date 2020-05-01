package com.yuchew6.hw1.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.yuchew6.hw1.R
import com.yuchew6.hw1.activity.SongListAdapter
import kotlinx.android.synthetic.main.fragment_list_songs.*

class SongListFragment : Fragment() {
    private lateinit var songListAdapter: SongListAdapter
    private var onSongClickListener: OnSongClickListener? = null
    lateinit var songs: List<Song>

    companion object {
        const val ALL_SONG = "all_song"
        const val LIST = "list"
        val TAG: String = SongListFragment::class.java.simpleName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            var songList = args.getParcelableArrayList<Song>(ALL_SONG)
            if (songList != null) {
                this.songs = songList
            }
        }

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

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                var songLists = getParcelableArrayList<Song>(LIST)
                songs = songLists as List<Song>
            }
        }
        // render the list
        songListAdapter = SongListAdapter(songs)
        songList.adapter = songListAdapter

        // click the list
        songListAdapter.onSongClickListener = {song ->
            onSongClickListener?.onSongClicked(song)
        }

        // long press the list
        songListAdapter.onSongLongClickListener = {song, currList ->
            var newList = currList.toMutableList().apply{remove(song)}
            songListAdapter.change(newList)
            onSongClickListener?.onSongLongClicked(song)
            true
        }
    }
    // shuffle
    fun shuffleList() {
        var newList = songs.toMutableList().apply{shuffle()}
        songs = newList
        songListAdapter.change(newList)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(LIST, songs as ArrayList)
        super.onSaveInstanceState(outState)
    }

}

interface OnSongClickListener {
    fun onSongClicked(song: Song)

    fun onSongLongClicked(song: Song)
}