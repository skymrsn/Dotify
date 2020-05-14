package com.yuchew6.hw1.manager

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.yuchew6.hw1.model.AllSongs


class ApiManager(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)
    private var allSongs: AllSongs ?= null


    fun getListOfSong(onSongReady: (AllSongs) -> Unit, onError: (() -> Unit)? = null) {
        val songURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/musiclibrary.json"

        val request = StringRequest (
            Request.Method.GET, songURL,
            { response ->
                // Success
                val gson = Gson()
                allSongs = gson.fromJson(response, AllSongs::class.java )

                onSongReady?.invoke(allSongs!!)

            },
            {
                onError?.invoke()
            }
        )

        queue.add(request)
    }

    fun getAllSongs(): AllSongs? {
        return allSongs
    }
}