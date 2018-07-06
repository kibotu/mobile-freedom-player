package com.exozet.freedomplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exozet.sequentialimage.player.SequentialImagePlayer
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startInteriorPlayer.setOnClickListener {
            startThreeHundredSixtyPlayer("equirectangular.png")
        }

        startExteriorPlayer.setOnClickListener {
            startSequentialPlayer((1 until 192).map { String.format("stabilized/out%03d.png", it) }.toList())
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
                .fps(24) // default: 30
                .playBackwards(false) // default: false
                .autoPlay(false) // default: true
                .zoom(true) // default: true
                .controls(true) // default: false
                .swipeSpeed(0.8f) // default: 1
                .startActivity()
    }
}