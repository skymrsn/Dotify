package com.yuchew6.hw1


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(private var listOfSong: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    var onSongClickListener: ((song: Song) -> Unit)? = null
    lateinit var onSongLongClickListener: ((song: Song, currList: List<Song>) -> Boolean)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)

        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSong.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val artistName = listOfSong[position].artist
        val songTitle = listOfSong[position].title
        val songPic = listOfSong[position].smallImageID
        holder.bind(artistName, songTitle, songPic, listOfSong[position])
    }

    fun change(newSong: List<Song>) {
        val callback = SongDiffCallback(listOfSong, newSong)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)

        listOfSong = newSong

    }

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val artistName = itemView.findViewById<TextView>(R.id.artistName)
        private val songTitle = itemView.findViewById<TextView>(R.id.songTitle)
        private val photoSrc = itemView.findViewById<ImageView>(R.id.songPic)

        fun bind(name: String, title: String, pic: Int, song: Song) {
            artistName.text = name
            songTitle.text = title
            photoSrc.setImageResource(pic)

            // choose song
            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }

            // delete song
            itemView.setOnLongClickListener {
                onSongLongClickListener.invoke(song, listOfSong)
            }
        }
    }
}

