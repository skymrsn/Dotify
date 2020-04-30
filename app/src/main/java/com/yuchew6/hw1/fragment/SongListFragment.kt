package com.yuchew6.hw1

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericchee.songdataprovider.Song
import com.yuchew6.hw1.activity.SongListAdapter
import kotlinx.android.synthetic.main.fragment_list_songs.*

class SongListFragment : Fragment() {
    private lateinit var songListAdapter: SongListAdapter
    private var onSongClickListener: OnSongClickListener? = null
    lateinit var songs: List<Song>

    companion object {
        const val ALL_SONG = "all_song"
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
            val songList = args.getParcelableArrayList<Song>(ALL_SONG)
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

        songListAdapter = SongListAdapter(songs)
        songList.adapter = songListAdapter

//        emailAdapter.onEmailClicked = { email ->
//            onEmailSelectedListener?.onEmailSelected(email)
//        }
//
//        btnCompose.setOnClickListener {
//            startActivityForResult(Intent(context, ComposeActivity::class.java),
//                ListEmailsActivity.COMPOSE_REQUEST_CODE
//            )
//        }
    }

}

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}