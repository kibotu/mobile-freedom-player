package com.exozet.freedomplayer

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.exozet.threehundredsixty.player.InteractionMode
import com.exozet.threehundredsixty.player.ProjectionMode
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer
import java.util.*


data class Parameter(
        @PlayerTypes val startPlayer: String = FreedomPlayerActivity.SEQUENTIAL_IMAGE_PLAYER,
        val threeHundredSixtyUri: Uri,
        @ProjectionMode val projectionMode: Int = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE,
        @InteractionMode val interactionMode: Int = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH,
        val showControls: Boolean = false,
        val sequentialImageUris: Array<Uri>? = null,
        val sequentialImageUri: Uri? = null,
        val autoPlay: Boolean = true,
        val fps: Int = 30,
        val playBackwards: Boolean = false,
        val zoomable: Boolean = true,
        val translatable: Boolean = true,
        val swipeSpeed: Float = 0.8f,
        val blurLetterbox: Boolean = true
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Uri::class.java.classLoader),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.createTypedArray(Uri.CREATOR),
            parcel.readParcelable(Uri::class.java.classLoader),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readFloat(),
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(startPlayer)
        parcel.writeParcelable(threeHundredSixtyUri, flags)
        parcel.writeInt(projectionMode)
        parcel.writeInt(interactionMode)
        parcel.writeByte(if (showControls) 1 else 0)
        parcel.writeTypedArray(sequentialImageUris, flags)
        parcel.writeParcelable(sequentialImageUri, flags)
        parcel.writeByte(if (autoPlay) 1 else 0)
        parcel.writeInt(fps)
        parcel.writeByte(if (playBackwards) 1 else 0)
        parcel.writeByte(if (zoomable) 1 else 0)
        parcel.writeByte(if (translatable) 1 else 0)
        parcel.writeFloat(swipeSpeed)
        parcel.writeByte(if (blurLetterbox) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }


    override fun toString(): String {
        return "Parameter(startPlayer='$startPlayer', threeHundredSixtyUri=$threeHundredSixtyUri, projectionMode=$projectionMode, interactionMode=$interactionMode, showControls=$showControls, sequentialImageUris=${Arrays.toString(sequentialImageUris)}, sequentialImageUri=$sequentialImageUri, autoPlay=$autoPlay, fps=$fps, playBackwards=$playBackwards, zoomable=$zoomable, translatable=$translatable, swipeSpeed=$swipeSpeed, blurLetterbox=$blurLetterbox)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Parameter

        if (startPlayer != other.startPlayer) return false
        if (threeHundredSixtyUri != other.threeHundredSixtyUri) return false
        if (projectionMode != other.projectionMode) return false
        if (interactionMode != other.interactionMode) return false
        if (showControls != other.showControls) return false
        if (!Arrays.equals(sequentialImageUris, other.sequentialImageUris)) return false
        if (sequentialImageUri != other.sequentialImageUri) return false
        if (autoPlay != other.autoPlay) return false
        if (fps != other.fps) return false
        if (playBackwards != other.playBackwards) return false
        if (zoomable != other.zoomable) return false
        if (translatable != other.translatable) return false
        if (swipeSpeed != other.swipeSpeed) return false
        if (blurLetterbox != other.blurLetterbox) return false

        return true
    }

    override fun hashCode(): Int {
        var result = startPlayer.hashCode()
        result = 31 * result + threeHundredSixtyUri.hashCode()
        result = 31 * result + projectionMode
        result = 31 * result + interactionMode
        result = 31 * result + showControls.hashCode()
        result = 31 * result + (sequentialImageUris?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + (sequentialImageUri?.hashCode() ?: 0)
        result = 31 * result + autoPlay.hashCode()
        result = 31 * result + fps
        result = 31 * result + playBackwards.hashCode()
        result = 31 * result + zoomable.hashCode()
        result = 31 * result + translatable.hashCode()
        result = 31 * result + swipeSpeed.hashCode()
        result = 31 * result + blurLetterbox.hashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<Parameter> {
        override fun createFromParcel(parcel: Parcel): Parameter {
            return Parameter(parcel)
        }

        override fun newArray(size: Int): Array<Parameter?> {
            return arrayOfNulls(size)
        }
    }


}