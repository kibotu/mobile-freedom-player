package com.exozet.freedomplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exozet.sequentialimage.player.SequentialImagePlayer
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer
import kotlinx.android.synthetic.main.freedom_player_main_activity.*

class FreedomPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.freedom_player_main_activity)

        startInteriorPlayer.setOnClickListener {
            startThreeHundredSixtyPlayer("equirectangular.jpg")
        }

        startExteriorPlayer.setOnClickListener {
            startSequentialPlayer((1 until 192).map { String.format("stabilized/out%03d.png", it) }.toList())
        }

        exit.setOnClickListener {
            finish()
        }
    }

    private fun startThreeHundredSixtyPlayer(filename: String) {
        ThreeHundredSixtyPlayer
                .with(this)
                .assetFile(filename)
                .startActivity()
    }

    fun startSequentialPlayer(list: List<String>) {
        SequentialImagePlayer
                .with(this)
                // .internalStorageFiles(list)
                .assetFiles(list)
                // .externalStorageFiles(list)
                // .files(list)
                .fps(30) // default: 30
                .playBackwards(false) // default: false
                .autoPlay(true) // default: true
                .zoom(true) // default: true
                .controls(true) // default: false
                .swipeSpeed(0.8f) // default: 1
                .startActivity()
    }
}