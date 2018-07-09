package com.exozet.freedomplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exozet.parseAssetFile
import com.exozet.sequentialimage.player.SequentialImagePlayer
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer
import kotlinx.android.synthetic.main.freedom_player_main_activity.*

class FreedomPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.freedom_player_main_activity)

        startExteriorPlayer.setOnClickListener {
            switchToExterior()
        }

        startInteriorPlayer.setOnClickListener {
            switchToInterior()
        }

        exit.setOnClickListener {
            finish()
        }

        switchToInterior()
    }

    fun switchToExterior() {
        startInteriorPlayer.isSelected = false
        startExteriorPlayer.isSelected = true
        startSequentialPlayer((1 until 192).map { String.format("stabilized/out%03d.png", it) }.toList())
    }

    fun switchToInterior() {
        startInteriorPlayer.isSelected = true
        startExteriorPlayer.isSelected = false
        startThreeHundredSixtyPlayer("equirectangular.jpg")
    }

    private fun startThreeHundredSixtyPlayer(filename: String) {
        threeHundredSixtyView.uri = parseAssetFile(filename)
        threeHundredSixtyView.projectionMode = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE
        threeHundredSixtyView.interactionMode = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH
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