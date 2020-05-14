package com.yuchew6.hw1

import android.app.Application
import com.yuchew6.hw1.manager.ApiManager
import com.yuchew6.hw1.manager.MusicManager

class DotifyApp: Application() {

    lateinit var musicManager: MusicManager
    lateinit var apiManager: ApiManager

    override fun onCreate() {
        super.onCreate()

        musicManager = MusicManager()
        apiManager = ApiManager(this)
    }
}