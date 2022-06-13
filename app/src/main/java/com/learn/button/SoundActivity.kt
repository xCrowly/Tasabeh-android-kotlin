package com.learn.button

import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_sound.*

class SoundActivity : AppCompatActivity() {

    //to make the variable accessible every where
    companion object {
        var mp: MediaPlayer? = null
        var checkPlaying: Boolean = false
        var iconHolder: Int? = null
        var sounddata = ArrayList<SoundData>()
        var sourceId: Int? = null
        var holderId: Int? = null
        var viewHolderId: Int? = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (checkPlaying) {
            iconHolder = R.mipmap.ic_pause
        } else {
            iconHolder = R.mipmap.ic_play
        }

        setContentView(R.layout.activity_sound)

        //sound data
        if (sounddata.size == 0) {
            sounddata.add(
                SoundData(
                    "أذكار الشيخ مشاري",
                    R.drawable.ic_round_music_note_24,
                    R.raw.mshary1,
                    R.mipmap.ic_play
                )
            )
            sounddata.add(
                SoundData(
                    "سبحان الله",
                    R.drawable.ic_round_music_note_24,
                    R.raw.sobhanallah,
                    R.mipmap.ic_play
                )
            )
            sounddata.add(
                SoundData(
                    "استغفر الله",
                    R.drawable.ic_round_music_note_24,
                    R.raw.astghferallah,
                    R.mipmap.ic_play
                )
            )
            sounddata.add(
                SoundData(
                    "لا حول ولا قوة الا بالله",
                    R.drawable.ic_round_music_note_24,
                    R.raw.la_hawl_wla,
                    R.mipmap.ic_play
                )
            )
            sounddata.add(
                SoundData(
                    "لا اله الا هو الحي القيوم",
                    R.drawable.ic_round_music_note_24,
                    R.raw.la_elah_ela_howa,
                    R.mipmap.ic_play
                )
            )
            sounddata.add(
                SoundData(
                    "اللهم صلي علي سيدنا محمد",
                    R.drawable.ic_round_music_note_24,
                    R.raw.allahom_sally,
                    R.mipmap.ic_play
                )
            )
            sounddata.add(
                SoundData(
                    "الحمد لله",
                    R.drawable.ic_round_music_note_24,
                    R.raw.alham_lellah,
                    R.mipmap.ic_play
                )
            )
            sounddata.add(
                SoundData(
                    "سبحان الله وبحمده سبحان الله العظيم",
                    R.drawable.ic_round_music_note_24,
                    R.raw.sobhan_allah_wabehamdeh,
                    R.mipmap.ic_play
                )
            )
            sounddata.add(
                SoundData(
                    "سبحان الله والحمد لله",
                    R.drawable.ic_round_music_note_24,
                    R.raw.spb7an_allah_walhamd_lellah,
                    R.mipmap.ic_play
                )
            )
        }

        var myadapter = MyAdapter()
        var mylayout = GridLayoutManager(this, 1)
        rv.layoutManager = mylayout
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = myadapter
    }

    private fun stopPlaying() {
        if (mp != null) {
            mp!!.stop()
            mp!!.release()
            mp = null
        }
    }

    //media player fun
    private fun mpstart(boolean: Boolean, source: Int): Boolean {
        //PlayButton.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
        if (boolean == true) {
            mp!!.stop()
            mp!!.release()
            mp = null
            checkPlaying = false

        } else {
            //PlayButton.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
            stopPlaying()
            mp = MediaPlayer.create(this, source)
            mp!!.start()
            mp!!.setOnCompletionListener {

                mp!!.release()
                mp = null
                checkPlaying = false
            }
            checkPlaying = true
            sourceId = source
        }
        return checkPlaying
    }

    //recycler view item id and data
    inner class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var soundname = view.findViewById<TextView>(R.id.SoundText)!!
            var soundImage = view.findViewById<ImageView>(R.id.soundImage)!!
            var soundButton = view.findViewById<ImageView>(R.id.imageView2)!!
            var soundsource: Int? = null
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            var itemview =
                LayoutInflater.from(parent.context).inflate(
                    R.layout.card_view,
                    parent,
                    false
                )
            return MyViewHolder(itemview)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            var data = sounddata[position]
            holder.soundname.text = data.soundName
            holder.soundImage.setImageResource(data.soundImage)
            holder.soundsource = data.soundSource
            holder.soundButton.setImageResource(data.soundB)

            if (sourceId == holder.soundsource) {
                holder.soundButton.findViewById<ImageView>(holder.soundButton.id)
                    .setImageResource(iconHolder!!)
            }

            holder.soundButton.setOnClickListener {

                mpstart(checkPlaying, data.soundSource)

                if (checkPlaying) {
                    iconHolder = R.mipmap.ic_pause
                    holder.soundButton.findViewById<ImageView>(holder.soundButton.id)
                        .setImageResource(iconHolder!!)
                    holderId = holder.soundButton.id
                    viewHolderId = holder.soundButton.id
                } else {
                    if (sourceId != holder.soundsource) {
                        stopPlaying()
                        checkPlaying = false
                        mpstart(checkPlaying, data.soundSource)

                        if (checkPlaying) {
                            iconHolder = R.mipmap.ic_pause
                            holder.soundButton.findViewById<ImageView>(holder.soundButton.id)
                                .setImageResource(iconHolder!!)
                            holderId = holder.soundButton.id
                            notifyDataSetChanged()
                        }
                    } else {
                        iconHolder = R.mipmap.ic_play
                        //holder.soundButton.findViewById<ImageView>(holder.soundButton.id)
                        //.setImageResource(iconHolder!!)
                        holder.soundButton.findViewById<ImageView>(holderId!!)
                            .setImageResource(iconHolder!!)
                        holderId == null
                        notifyDataSetChanged()
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return sounddata.size
        }
    }

}
