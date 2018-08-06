package com.exozet.freedomplayer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.KITKAT
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.exozet.threehundredsixty.player.ThreeHundredSixtyPlayer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.freedom_player_main_activity.*


class FreedomPlayerActivity : AppCompatActivity() {

    lateinit var parameter: Parameter

    var height: Int = 2048

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.freedom_player_main_activity)

        exit.setOnClickListener {
            finish()
        }

        parameter = intent?.extras?.getParcelable(Parameter::class.java.canonicalName)
                ?: return

        window.decorView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                if (window.decorView.viewTreeObserver.isAlive)
                    window.decorView.viewTreeObserver.removeOnPreDrawListener(this)

                height = Math.min(window.decorView.width, window.decorView.height)

                when (parameter.startPlayer) {
                    SEQUENTIAL_IMAGE_PLAYER -> switchToExterior()
                    THREE_HUNDRED_SIXTY_PLAYER -> switchToInterior()
                }

                return false
            }
        })

        startExteriorPlayer.setOnClickListener {
            switchToExterior()
        }

        startInteriorPlayer.setOnClickListener {
            switchToInterior()
        }

        autoPlay.setOnCheckedChangeListener { _, isChecked -> sequentialImagePlayer.autoPlay = isChecked }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    override fun onResume() {
        super.onResume()
        sequentialImagePlayer.onResume()
    }

    override fun onPause() {
        super.onPause()
        sequentialImagePlayer.onPause()
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
        when {
            parameter.threeHundredSixtyUri.toString().startsWith("http://") -> loadInteriorJson(parameter.threeHundredSixtyUri) { uri = it }
            parameter.threeHundredSixtyUri.toString().startsWith("https://") -> loadInteriorJson(parameter.threeHundredSixtyUri) { uri = it }
            else -> uri = parameter.threeHundredSixtyUri
        }
        projectionMode = ThreeHundredSixtyPlayer.PROJECTION_MODE_SPHERE
        interactionMode = ThreeHundredSixtyPlayer.INTERACTIVE_MODE_MOTION_WITH_TOUCH
        showControls = true
        onCameraRotation = { pitch, yaw, roll ->
            try {
                degreeIndicator.rotation = -yaw
            } catch (ignore: Exception) {
                this@FreedomPlayerActivity.log(ignore.message)
            }
        }
    }

    private fun startSequentialPlayer() = with(sequentialImagePlayer) {
        parameter.sequentialImageUris?.let {
            imageUris = it
        }
        parameter.sequentialImageUri?.let { loadExteriorJson(it) { imageUris = it } }
        autoPlay = parameter.autoPlay
        fps = parameter.fps
        playBackwards = parameter.playBackwards
        zoomable = parameter.zoomable
        translatable = parameter.translatable
        showControls = false // parameter.showControls
        swipeSpeed = parameter.swipeSpeed
        blurLetterbox = parameter.blurLetterbox
        onProgressChanged = { degreeIndicator.rotation = it * 360 }
    }

    var interiorJsonRequest: Disposable? = null

    private fun loadInteriorJson(jsonUri: Uri, block: (Uri) -> Unit) {

        interiorJsonRequest = RequestProvider.interiorJson(jsonUri).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    log("interior=$it")

                    val uri: Uri? = Uri.parse(interiorPublicUrlByScreenHeight(height, it?.imageMedia?.publicUrls)
                            ?: "")

                    if (uri.toString().isEmpty())
                        Log.e(TAG, "error loading interiorJson public urls")

                    block(uri!!)

                }, { t ->
                    Log.e(TAG, t.message)
                    t.printStackTrace()
                })
    }

    var exteriorJsonRequest: Disposable? = null

    private fun loadExteriorJson(jsonUri: Uri, block: (Array<Uri>) -> Unit) {

        exteriorJsonRequest = RequestProvider.exteriorJson(jsonUri).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    log("exterior=$it")

                    val uris: Array<Uri> = it?.imageCollection?.galleryHasMedias?.map {
                        Uri.parse(exteriorPublicUrlByScreenHeight(height, it?.media?.publicUrls)
                                ?: "")
                    }?.toTypedArray() ?: arrayOf()

                    if (uris.isEmpty())
                        Log.e(TAG, "error loading exteriorJson public urls")

                    block(uris)

                }, { t ->
                    Log.e(TAG, t.message)
                    t.printStackTrace()
                })
    }

    /**
     *  interior
     *      imageMedia.publicUrls"exterior_view_medium": "https://storage.googleapis.com/preview-mobile-de/exterior_view/0001/01/thumb_541_exterior_view_medium.jpeg",
     *
     * interior_view_medium
     * interior_view_2160
     * interior_view_1080
     * interior_view_720
     * interior_view_480
     * reference
     *
     * @param window
     * @param publicUrls
     * @returns uri
     */
    internal fun interiorPublicUrlByScreenHeight(height: Int, publicUrls: PublicUrls?): String? = when {
        height >= 2160 -> {
            log("height=$height => interior_view_2160 ${publicUrls?.interior_view_2160}")
            publicUrls?.interior_view_2160
        }
        height >= 1080 -> {
            log("height=$height => interior_view_1080 ${publicUrls?.interior_view_1080}")
            publicUrls?.interior_view_1080
        }
        height >= 720 -> {
            log("height=$height => interior_view_720 ${publicUrls?.interior_view_720}")
            publicUrls?.interior_view_720
        }
        height >= 480 -> {
            log("height=$height => interior_view_480 ${publicUrls?.interior_view_480}")
            publicUrls?.interior_view_480
        }
        else -> {
            log("height=$height => interior_view_medium ${publicUrls?.interior_view_medium}")
            publicUrls?.interior_view_medium
        }
    }

    /**
     *  exterior
     *      imageCollection.galleryHasMedias.media.publicUrls
     *
     * exterior_view_medium
     * exterior_view_2160
     * exterior_view_1080
     * exterior_view_720
     * exterior_view_480
     * reference
     *
     * @param window
     * @param publicUrls
     * @returns uri
     */
    internal fun exteriorPublicUrlByScreenHeight(height: Int, publicUrls: PublicUrls?): String? = when {
        height >= 2160 -> {
            log("height=$height => exterior_view_2160 ${publicUrls?.exterior_view_2160}")
            publicUrls?.exterior_view_2160
        }
        height >= 1080 -> {
            log("height=$height => exterior_view_1080 ${publicUrls?.exterior_view_1080}")
            publicUrls?.exterior_view_1080
        }
        height >= 720 -> {
            log("height=$height => exterior_view_720 ${publicUrls?.exterior_view_720}")
            publicUrls?.exterior_view_720
        }
        height >= 480 -> {
            log("height=$height => exterior_view_480 ${publicUrls?.exterior_view_480}")
            publicUrls?.exterior_view_480
        }
        else -> {
            log("height=$height => exterior_view_medium ${publicUrls?.exterior_view_medium}")
            publicUrls?.exterior_view_medium
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (interiorJsonRequest?.isDisposed == false)
            interiorJsonRequest?.dispose()
        if (exteriorJsonRequest?.isDisposed == false)
            exteriorJsonRequest?.dispose()
    }

    companion object {

        fun startActivity(context: Context, parameter: Parameter) = context.startActivity(Intent(context, FreedomPlayerActivity::class.java).apply { putExtra(Parameter::class.java.canonicalName, parameter) })

        const val THREE_HUNDRED_SIXTY_PLAYER = "THREE_HUNDRED_SIXTY_PLAYER"
        const val SEQUENTIAL_IMAGE_PLAYER = "SEQUENTIAL_IMAGE_PLAYER"
    }

    var debug = true

    private val TAG = this::class.java.simpleName

    private fun log(message: String?) {
        if (debug)
            Log.d(TAG, message ?: "")
    }

    /**
     * https://developer.android.com/training/system-ui/immersive
     */
    private fun hideSystemUI() {
        if (SDK_INT < KITKAT) return
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}