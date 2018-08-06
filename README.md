# FreedomPlayer [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Gradle Version](https://img.shields.io/badge/gradle-4.8.1-green.svg)](https://docs.gradle.org/current/release-notes)  [![Kotlin](https://img.shields.io/badge/kotlin-1.2.51-green.svg)](https://kotlinlang.org/)  

Interior- and exterior player.

[![Screenshot](https://git.exozet.com/mobile-de/android-player/blob/master/demo.gif)](https://git.exozet.com/mobile-de/android-player/blob/master/demo.gif)

# How to use

        FreedomPlayerActivity.startActivity(this, Parameter(
                startPlayer = FreedomPlayerActivity.SEQUENTIAL_IMAGE_PLAYER, // SEQUENTIAL_IMAGE_PLAYER, THREE_HUNDRED_SIXTY_PLAYER, default: FreedomPlayerActivity.SEQUENTIAL_IMAGE_PLAYER
                // threeHundredSixtyUri = parseAssetFile("equirectangular.jpg"), // load local asset file
                threeHundredSixtyUri = parseAssetFile("interior_example.jpg"),  // load local asset file
                // threeHundredSixtyUri = Uri.parse("https://storage.googleapis.com/preview-mobile-de/default/0001/01/b66f74bd094963f1b07b297508cfdeae1262cc7f.json"), // load using interior.json
                projectionMode = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE, // PROJECTION_MODE_SPHERE, PROJECTION_MODE_MULTI_FISH_EYE_HORIZONTAL, PROJECTION_MODE_MULTI_FISH_EYE_VERTICAL
                interactionMode = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH, // INTERACTIVE_MODE_TOUCH, INTERACTIVE_MODE_MOTION, INTERACTIVE_MODE_MOTION_WITH_TOUCH, default: INTERACTIVE_MODE_MOTION_WITH_TOUCH
                showControls = false, // shows autoPlay and motion buttons, default false
                sequentialImageUris = (1 until 192).map { parseAssetFile(String.format("stabilized/out%03d.png", it)) }.toTypedArray(), // load from list of local files
                // sequentialImageUri = Uri.parse("https://storage.googleapis.com/preview-mobile-de/default/0001/01/7eb02f09747a624a50d3d287d2354610251ec2ad.json"), // load using exterior.json
                autoPlay = true, // default: true
                fps = 30, // [1:60] default: 30
                playBackwards = false, // default: false
                zoomable = true, // default: true
                translatable = true, // default: true
                swipeSpeed = 0.8f, // default 1f
                blurLetterbox = true // default: true
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