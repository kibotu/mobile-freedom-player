package com.exozet.freedomplayer.app

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exozet.freedomplayer.FreedomPlayerActivity
import com.exozet.freedomplayer.Parameter
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FreedomPlayerActivity.startActivity(this, Parameter(
                startPlayer = FreedomPlayerActivity.THREE_HUNDRED_SIXTY_PLAYER,
//                 threeHundredSixtyUri = parseAssetFile("equirectangular.jpg"),
//                 threeHundredSixtyUri = parseAssetFile("interior_example.jpg"),
                threeHundredSixtyUri = Uri.parse("http://mobile-de-360-nopasswd.test7.exozet.com/test.json"),
                projectionMode = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE,
                interactionMode = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH,
                showControls = true,
                sequentialImageUris = (1 until 192).map { parseAssetFile(String.format("stabilized/out%03d.png", it)) }.toTypedArray(),
                autoPlay = true,
                fps = 30,
                playBackwards = false,
                zoomable = true,
                translatable = true,
                swipeSpeed = 0.8f,
                blurLetterbox = true
        ))

        finish()
    }

    fun parseAssetFile(file: String): Uri = Uri.parse("file:///android_asset/$file")
}