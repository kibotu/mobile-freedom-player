# FreedomPlayer [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Gradle Version](https://img.shields.io/badge/gradle-4.8.1-green.svg)](https://docs.gradle.org/current/release-notes)  [![Kotlin](https://img.shields.io/badge/kotlin-1.2.51-green.svg)](https://kotlinlang.org/)  

Interior- and exterior player.

[![Screenshot](https://git.exozet.com/mobile-de/android-player/blob/master/demo.gif)](https://git.exozet.com/mobile-de/android-player/blob/master/demo.gif)

# How to use

        FreedomPlayerActivity.startActivity(this, Parameter(
                startPlayer = FreedomPlayerActivity.SEQUENTIAL_IMAGE_PLAYER, // default: FreedomPlayerActivity.SEQUENTIAL_IMAGE_PLAYER
                threeHundredSixtyUri = parseAssetFile("equirectangular.jpg"),
                projectionMode = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE,
                interactionMode = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH,
                showControls = false,
                sequentialImageUris = (1 until 192).map { parseAssetFile(String.format("stabilized/out%03d.png", it)) }.toTypedArray(),
                autoPlay = true,
                fps = 30,
                playBackwards = false,
                zoomable = true,
                translatable = true,
                swipeSpeed = 0.8f,
                blurLetterbox = true
        ))
     
# How to install (tbd)

Atm only as module
    
    dependencies {
        api project(':FreedomPlayer')
    }
    
# Changelog

# TODO

* remove stroke between buttons: https://gist.github.com/CiTuX/5031751

# Related Project

* [ThreeHundredSixtyPlayer](https://git.exozet.com/mobile-de/POC/android-360-player)

* [SequentialImagePlayer](https://git.exozet.com/mobile-de/POC/android-walkthroug-player)

## Contributors

[Jan Rabe](jan.rabe@exozet.com)