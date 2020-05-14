package com.yuchew6.hw1.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.yuchew6.hw1.DotifyApp
import com.yuchew6.hw1.R
import com.yuchew6.hw1.fragment.NowPlayingFragment
import com.yuchew6.hw1.fragment.OnSongClickListener
import com.yuchew6.hw1.fragment.SongListFragment
import com.yuchew6.hw1.manager.ApiManager
import com.yuchew6.hw1.manager.MusicManager
import com.yuchew6.hw1.manager.SongRecommendListner
import com.yuchew6.hw1.model.AllSongs
import kotlinx.android.synthetic.main.activity_ultimate_main.*
import kotlin.random.Random

class UltimateMainActivity : AppCompatActivity(), OnSongClickListener, SongRecommendListner {

    // no need to store theSong, get it from application
    // private var theSong: Song?= null
    // private val THE_SONG = "the_song"
    private val MINI_TEXT = "mini_text"
    lateinit var apiManager: ApiManager
    lateinit var musicManager: MusicManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_main)

        // initialize SongRecommendListner
        val dotifyApp = application as DotifyApp
        dotifyApp.musicManager.songRecommendListener = this

        // initialize manager
        apiManager = (application as DotifyApp).apiManager
        musicManager = (application as DotifyApp).musicManager

        // create listFragment
        if (getSongListFragment() != null) {
            // restore info
            if (savedInstanceState != null) {
                with(savedInstanceState) {
                    miniPlayer.text = getCharSequence(MINI_TEXT)
                    // no need to store theSong, get it from application
                    // theSong = this!!.getParcelable(THE_SONG)!!
                }
            }
            if (getNowPlayingFragment() != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                miniPlayerBox.visibility = View.GONE
                btnRecommend.visibility = View.GONE
            }
        } else {
            // render song list; put all_song into list fragment
            var songListFragment = SongListFragment()

            // no need to pass allSongs, get it from application
//            var argumentBundle = Bundle().apply {
//                val allSongs: List<Song> = musicManager.allSongs
//
//                putParcelableArrayList(SongListFragment.ALL_SONG, allSongs as ArrayList)
//            }
//            songListFragment.arguments = argumentBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .commit()
        }

        // recommendation
        btnRecommend.setOnClickListener {
            val songs = apiManager.getAllSongs()
            if (songs == null) {
                apiManager.getListOfSong({ allSongs ->
                    recommendHelper(allSongs)
                }, {
                    Toast.makeText(this, "Fail to fetch data", Toast.LENGTH_SHORT).show()
                })
            } else {
                recommendHelper(songs)
            }
        }

        // shuffle
        btnShuffle.setOnClickListener {
            getSongListFragment()?.shuffleList()
        }

        // play song
        miniPlayer.setOnClickListener {
            if (musicManager.currentSong != null) {
                enterPlaying()
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0

            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                miniPlayerBox.visibility = View.GONE
                btnRecommend.visibility = View.GONE
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                miniPlayerBox.visibility = View.VISIBLE
                btnRecommend.visibility = View.VISIBLE
            }
        }
    }

    private fun recommendHelper(allSongs: AllSongs?) {
        val listOfSongs = allSongs?.songs
        val randomNumber = listOfSongs?.size?.let { Random.nextInt(0, it) }
        val recommend = randomNumber?.let { listOfSongs?.get(it) }
        if (recommend != null) {
            musicManager.recommendSong(recommend, randomNumber)
        }
    }

    // help creating nowplayingfragment
    override fun enterPlaying() {
        miniPlayerBox.visibility = View.GONE
        btnRecommend.visibility = View.GONE

        var nowPlayingFragment = getNowPlayingFragment()
        if (nowPlayingFragment == null) {
            nowPlayingFragment = NowPlayingFragment()

            // no need to pass theSong, get it from application
//            val nowPlayingBuddle = Bundle().apply {
//                putParcelable(NowPlayingFragment.THE_SONG, theSong)
//            }
//            nowPlayingFragment.arguments = nowPlayingBuddle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                .addToBackStack(NowPlayingFragment.TAG)
                .commit()
        } else {
            nowPlayingFragment.updateSong(musicManager.currentSong!!)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putCharSequence(MINI_TEXT, miniPlayer.text)
        // outState.putParcelable(THE_SONG, theSong)
        super.onSaveInstanceState(outState)
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment
    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

    override fun onSongClicked(song: Song, theIndex: Int) {
        miniPlayer.text = "${song.title} - ${song.artist}"
        // theSong = song
        musicManager.currentSong = song
        musicManager.index = theIndex
        Log.i("echee", theIndex.toString())
    }

    override fun onSongLongClicked(song: Song) {
        if (song == musicManager.currentSong) {
            miniPlayer.text = ""
            musicManager.index = 0
            // theSong = null
            musicManager.currentSong = null
        }

        var deleteName = song.title
        Toast.makeText(this, "`$deleteName` was deleted", Toast.LENGTH_SHORT).show()
    }

    override fun update(song: Song) {
        // theSong = song
        miniPlayer.text = "${song.title} - ${song.artist}"
    }
}