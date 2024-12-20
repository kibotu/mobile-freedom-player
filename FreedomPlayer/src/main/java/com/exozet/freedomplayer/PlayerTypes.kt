package com.exozet.freedomplayer

import androidx.annotation.StringDef
import com.exozet.freedomplayer.FreedomPlayerActivity.Companion.SEQUENTIAL_IMAGE_PLAYER
import com.exozet.freedomplayer.FreedomPlayerActivity.Companion.THREE_HUNDRED_SIXTY_PLAYER


@StringDef(THREE_HUNDRED_SIXTY_PLAYER, SEQUENTIAL_IMAGE_PLAYER)
@Retention(AnnotationRetention.SOURCE)
annotation class PlayerTypes