package com.exozet.freedomplayer

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.exozet.parseAssetFile
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer
import kotlinx.android.synthetic.main.freedom_player_main_activity.*

class FreedomPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.freedom_player_main_activity)

        val imageUris = (1 until 192).map { parseAssetFile(String.format("stabilized/out%03d.png", it)) }
        val equirectangular = "equirectangular.jpg"

        startExteriorPlayer.setOnClickListener {
            switchToExterior(imageUris)
        }

        startInteriorPlayer.setOnClickListener {
            switchToInterior(equirectangular)
        }

        exit.setOnClickListener {
            finish()
        }

        switchToExterior(imageUris)
        // switchToInterior(equirectangular)
    }

    fun switchToExterior(list: List<Uri>) {
        startInteriorPlayer.isSelected = false
        startExteriorPlayer.isSelected = true
        threeHundredSixtyView.visibility = View.GONE
        sequentialImagePlayer.visibility = View.VISIBLE
        startSequentialPlayer(list)
    }

    fun switchToInterior(filename: String) {
        startInteriorPlayer.isSelected = true
        startExteriorPlayer.isSelected = false
        threeHundredSixtyView.visibility = View.VISIBLE
        sequentialImagePlayer.visibility = View.GONE
        startThreeHundredSixtyPlayer(filename)
    }

    private fun startThreeHundredSixtyPlayer(filename: String) = with(threeHundredSixtyView) {
        uri = parseAssetFile(filename)
        projectionMode = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE
        interactionMode = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH
    }

    private fun startSequentialPlayer(list: List<Uri>) = with(sequentialImagePlayer) {
        imageUris = list.toTypedArray()
        autoPlayEnabled = false
        fps = 30
        playBackwards = false
        zoomable = false
        translatable = false
        showControls = true
        swipeSpeed = 1f
    }
}