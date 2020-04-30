package com.yuchew6.hw1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.yuchew6.hw1.OnSongClickListener
import com.yuchew6.hw1.R
import com.yuchew6.hw1.SongListFragment

class UltimateMainActivity : AppCompatActivity(), OnSongClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_main)

        // render song list; put all_song into list fragment
        val songListFragment = SongListFragment()
        val argumentBundle = Bundle().apply {
            val allSongs: List<Song> = SongDataProvider.getAllSongs()

            putParcelableArrayList(SongListFragment.ALL_SONG, allSongs as ArrayList)
        }
        songListFragment.arguments = argumentBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0

            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    //private fun getEmailDetailFragment() = supportFragmentManager.findFragmentByTag(EmailDetailFragment.TAG) as? EmailDetailFragment

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

//    override fun onSongClicked(song: Song) {
//        var emailDetailFragment = getEmailDetailFragment()
//
//        if (emailDetailFragment == null) {
//            emailDetailFragment = EmailDetailFragment()
//            val argumentBundle = Bundle().apply {
//                putParcelable(EmailDetailFragment.ARG_EMAIL, email)
//            }
//            emailDetailFragment.arguments = argumentBundle
//
//            supportFragmentManager
//                .beginTransaction()
//                .add(R.id.fragContainer, emailDetailFragment, EmailDetailFragment.TAG)
//                .addToBackStack(EmailDetailFragment.TAG)
//                .commit()
//        } else {
//            emailDetailFragment.updateEmail(email)
//        }
//
//    }
    override fun onSongClicked(song: Song) {}
}