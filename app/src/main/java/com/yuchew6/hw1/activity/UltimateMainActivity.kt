package com.yuchew6.hw1.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.yuchew6.hw1.R
import com.yuchew6.hw1.fragment.NowPlayingFragment
import com.yuchew6.hw1.fragment.OnSongClickListener
import com.yuchew6.hw1.fragment.SongListFragment
import kotlinx.android.synthetic.main.activity_ultimate_main.*

class UltimateMainActivity : AppCompatActivity(), OnSongClickListener {

    private var theSong: Song?= null
    private val THE_SONG = "the_song"
    private val MINI_TEXT = "mini_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_main)

        if (getSongListFragment() != null) {
            // restore info
            if (savedInstanceState != null) {
                with(savedInstanceState) {
                    miniPlayer.text = getCharSequence(MINI_TEXT)
                    theSong = this!!.getParcelable(THE_SONG)!!
                }
            }
            if (getNowPlayingFragment() != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                miniPlayerBox.visibility = View.GONE
            }
        } else {
            // render song list; put all_song into list fragment
            var songListFragment = SongListFragment()
            var argumentBundle = Bundle().apply {
                val allSongs: List<Song> = SongDataProvider.getAllSongs()

                putParcelableArrayList(SongListFragment.ALL_SONG, allSongs as ArrayList)
            }
            songListFragment.arguments = argumentBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .commit()
        }

        // shuffle
        btnShuffle.setOnClickListener {
            getSongListFragment()?.shuffleList()
        }

        // play song
        miniPlayer.setOnClickListener {
            if (theSong != null) {
                miniPlayerBox.visibility = View.GONE

                var nowPlayingFragment = getNowPlayingFragment()
                if (nowPlayingFragment == null) {
                    nowPlayingFragment = NowPlayingFragment()
                    val nowPlayingBuddle = Bundle().apply {
                        putParcelable(NowPlayingFragment.THE_SONG, theSong)
                    }
                    nowPlayingFragment.arguments = nowPlayingBuddle

                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                        .addToBackStack(NowPlayingFragment.TAG)
                        .commit()
                } else {
                    nowPlayingFragment.updateSong(theSong!!)
                }
            } else {

            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0

            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                miniPlayerBox.visibility = View.GONE
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                miniPlayerBox.visibility = View.VISIBLE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putCharSequence(MINI_TEXT, miniPlayer.text)
        outState.putParcelable(THE_SONG, theSong)
        super.onSaveInstanceState(outState)
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment
    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

    override fun onSongClicked(song: Song) {
        miniPlayer.text = "${song.title} - ${song.artist}"
        theSong = song
    }

    override fun onSongLongClicked(song: Song) {
        if (song == theSong) {
            miniPlayer.text = ""
        }

        var deleteName = song.title
        Toast.makeText(this, "`$deleteName` was deleted", Toast.LENGTH_SHORT).show()
    }
}