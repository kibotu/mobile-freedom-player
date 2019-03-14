package com.exozet.freedomplayer.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.exozet.freedomplayer.FreedomPlayerActivity
import com.exozet.freedomplayer.Parameter
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
            threeHundredSixtyUri = parseAssetFile("interior_example.jpg"),
//                threeHundredSixtyUri = Uri.parse("https://storage.googleapis.com/preview-mobile-de/default/0001/01/b66f74bd094963f1b07b297508cfdeae1262cc7f.json"),
            projectionMode = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE,
            interactionMode = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH,
            showControls = true,
//                sequentialImageUris = (1 until 192).map { parseAssetFile(String.format("default/out%d.png", it)) }.toTypedArray(),
                sequentialImageUris = (1 until 192).map { parseAssetFile(String.format("stabilized/out%03d.png", it)) }.toTypedArray(),
//            sequentialImageUri = Uri.parse("https://storage.googleapis.com/preview-mobile-de/default/0001/14/61b8ec8b30a9ed586f89ef7c1d71e479aadfe46d.json"),
            autoPlay = true,
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

        Log.v(MainActivity::class.java.simpleName, "[onActivityResult] requestCode=$requestCode resultCode=$resultCode data=${data?.extras} id=${data?.extras?.getString("id")}")

        if (requestCode == FREEDOM_PLAYER_ACTIVITY_REQUEST_CODE) {

            when (resultCode) {
                Activity.RESULT_CANCELED -> {
                }
                Activity.RESULT_OK -> {
                    var result = data?.extras?.getString("id")
                    // todo trigger remove event
                }
            }
        }
    }
}