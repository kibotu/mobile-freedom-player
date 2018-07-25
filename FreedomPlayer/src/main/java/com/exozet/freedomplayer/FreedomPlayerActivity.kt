package com.exozet.freedomplayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer
import kotlinx.android.synthetic.main.freedom_player_main_activity.*

class FreedomPlayerActivity : AppCompatActivity() {

    lateinit var parameter: Parameter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.freedom_player_main_activity)

        exit.setOnClickListener {
            finish()
        }

        parameter = intent?.extras?.getParcelable(Parameter::class.java.canonicalName)
                ?: return

        when (parameter.startPlayer) {
            SEQUENTIAL_IMAGE_PLAYER -> switchToExterior()
            THREE_HUNDRED_SIXTY_PLAYER -> switchToInterior()
        }

        startExteriorPlayer.setOnClickListener {
            switchToExterior()
        }

        startInteriorPlayer.setOnClickListener {
            switchToInterior()
        }

        autoPlay.setOnCheckedChangeListener { _, isChecked -> sequentialImagePlayer.autoPlay = isChecked }
    }

    fun switchToExterior() {
        autoPlay.visibility = if (parameter.showControls) View.VISIBLE else View.GONE
        startInteriorPlayer.isSelected = false
        startExteriorPlayer.isSelected = true
        threeHundredSixtyView.visibility = View.GONE
        sequentialImagePlayer.visibility = View.VISIBLE
        startSequentialPlayer()
    }

    fun switchToInterior() {
        autoPlay.visibility = View.GONE
        startInteriorPlayer.isSelected = true
        startExteriorPlayer.isSelected = false
        threeHundredSixtyView.visibility = View.VISIBLE
        sequentialImagePlayer.visibility = View.GONE
        startThreeHundredSixtyPlayer()
    }

    private fun startThreeHundredSixtyPlayer() = with(threeHundredSixtyView) {
        uri = parameter.threeHundredSixtyUri
        projectionMode = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE
        interactionMode = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH
        showControls = true
        onCameraRotation = { pitch, yaw, roll -> degreeIndicator.rotation = -yaw }
    }

    private fun startSequentialPlayer() = with(sequentialImagePlayer) {
        imageUris = parameter.sequentialImageUris
        autoPlay = parameter.autoPlay
        fps = parameter.fps
        playBackwards = parameter.playBackwards
        zoomable = parameter.zoomable
        translatable = parameter.translatable
        showControls = false // parameter.showControls
        swipeSpeed = parameter.swipeSpeed
        blurLetterbox = parameter.blurLetterbox
    }

    companion object {

        fun startActivity(context: Context, parameter: Parameter) = context.startActivity(Intent(context, FreedomPlayerActivity::class.java).apply { putExtra(Parameter::class.java.canonicalName, parameter) })

        const val THREE_HUNDRED_SIXTY_PLAYER = "THREE_HUNDRED_SIXTY_PLAYER"
        const val SEQUENTIAL_IMAGE_PLAYER = "SEQUENTIAL_IMAGE_PLAYER"
    }
}