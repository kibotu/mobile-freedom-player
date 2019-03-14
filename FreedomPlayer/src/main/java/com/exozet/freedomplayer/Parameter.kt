package com.exozet.freedomplayer

import android.net.Uri
import com.exozet.freedomplayer.FreedomPlayerActivity.Companion.SEQUENTIAL_IMAGE_PLAYER
import com.exozet.threehundredsixtyplayer.InteractionMode
import com.exozet.threehundredsixtyplayer.ProjectionMode
import com.exozet.threehundredsixtyplayer.ThreeHundredSixtyPlayer
import org.parceler.Parcel


@Parcel(Parcel.Serialization.BEAN)
data class Parameter(
    @PlayerTypes var startPlayer: String = SEQUENTIAL_IMAGE_PLAYER,
    var threeHundredSixtyUri: Uri? = null,
    @ProjectionMode var projectionMode: Int = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE,
    @InteractionMode var interactionMode: Int = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH,
    var showControls: Boolean = false,
    var sequentialImageUris: Array<Uri>? = null,
    var sequentialImageUri: Uri? = null,
    var autoPlay: Boolean = true,
    var fps: Int = 30,
    var playBackwards: Boolean = false,
    var zoomable: Boolean = true,
    var translatable: Boolean = true,
    var swipeSpeed: Float = 0.8f,
    var blurLetterbox: Boolean = true
)