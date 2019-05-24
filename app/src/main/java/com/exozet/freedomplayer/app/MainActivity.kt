package com.exozet.freedomplayer.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.exozet.freedomplayer.FreedomPlayerActivity
import com.exozet.freedomplayer.Parameter
import com.exozet.freedomplayer.PlayerTypes
import com.exozet.threehundredsixtyplayer.ThreeHundredSixtyPlayer

class MainActivity : AppCompatActivity() {

    companion object {
        const val FREEDOM_PLAYER_ACTIVITY_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val parameter = Parameter(
            startPlayer = FreedomPlayerActivity.THREE_HUNDRED_SIXTY_PLAYER,
//                 threeHundredSixtyUri = parseAssetFile("equirectangular.jpg"),
//            threeHundredSixtyUri = parseAssetFile("interior_example.jpg"),
            threeHundredSixtyUri = Uri.parse("https://storage.googleapis.com/mobile-de-live/default/0020/57/30681a8ec64824d1767638cb5a36b0724501c4f8.json"),
            projectionMode = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE,
            interactionMode = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH,
            showControls = false,
            sequentialImageUris = (1 until 192).map {
                parseAssetFile(String.format("default/out%d.png", it))
            }.toTypedArray(),
//            sequentialImageUris = (1 until 192).map { parseAssetFile(String.format("stabilized/out%03d.png", it)) }.toTypedArray(),
            // sequentialImageUri = Uri.parse("https://storage.googleapis.com/mobile-de-live/default/0024/99/ffb2c28b0cd4d2f1a1480e57591a79ead05b9903.json"), //todo: broken link
            autoPlay = false,
            fps = 17,
            playBackwards = false,
            zoomable = true,
            translatable = true,
            swipeSpeed = 0.8f,
            blurLetterbox = true
        )

        FreedomPlayerActivity.startActivityForResult(this, parameter, FREEDOM_PLAYER_ACTIVITY_REQUEST_CODE)
    }

    fun parseAssetFile(file: String): Uri = Uri.parse("file:///android_asset/$file")

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.v(
            MainActivity::class.java.simpleName,
            "[onActivityResult] requestCode=$requestCode resultCode=$resultCode data=${data?.extras} id=${data?.extras?.getString(
                "id"
            )}"
        )

        if (requestCode == FREEDOM_PLAYER_ACTIVITY_REQUEST_CODE) {

            when (resultCode) {
                Activity.RESULT_CANCELED -> {
                }
                Activity.RESULT_OK -> {

                    val adsId = data?.extras?.getString("id")

                    when (data?.extras?.getString(PlayerTypes::class.java.simpleName)) {
                        FreedomPlayerActivity.THREE_HUNDRED_SIXTY_PLAYER -> {
                        }
                        FreedomPlayerActivity.SEQUENTIAL_IMAGE_PLAYER -> {
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}