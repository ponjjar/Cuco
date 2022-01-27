package com.caiqueponjjar.cuco

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.VideoView
import com.caiqueponjjar.cuco.R.*
import android.util.DisplayMetrics
import android.widget.LinearLayout
import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.view.View.getDefaultSize
import java.security.AccessController.getContext
import kotlin.math.roundToInt
import android.view.View.getDefaultSize
import android.widget.Button
import android.R

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.caiqueponjjar.cuco.ui.login.LoginActivity


class SelectLogin : AppCompatActivity() {

    private lateinit var videoview : VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_select_login)
        videoview = findViewById<View>(id.backgroundVideo) as VideoView
        val uri: Uri = Uri.parse("android.resource://" + packageName + "/" + raw.cuckoofootage)
        videoview.setVideoURI(uri)
        val metrics = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getRealMetrics(metrics)

        // (getContext() as Activity).windowManager.defaultDisplay.getRealMetrics(metrics)
        //windowManager.defaultDisplay.getMetrics(metrics)
        val params = videoview.getLayoutParams()
        //params.leftMargin = 0
        videoview.setOnPreparedListener { mp: MediaPlayer ->

            val mVideoHeight = 720

            val mVideoWidth = 1280
            var width = metrics.widthPixels
            var height = metrics.heightPixels
                if (mVideoWidth * height > width * mVideoHeight) {
                    // Log.i("@@@", "image too tall, correcting");
                    height = width * mVideoHeight / mVideoWidth
                } else {
                    // Log.i("@@@", "image too wide, correcting");
                    width = height * (mVideoWidth / mVideoHeight)
                }

            mp.setLooping(true)
            mp.setVolume(0.0F ,0.0F)
            if(metrics.heightPixels > metrics.widthPixels) {
                params.height =metrics.heightPixels
                params.width = metrics.heightPixels * ( metrics.heightPixels/ mVideoHeight)
            }else{
                params.height = metrics.widthPixels * metrics.widthPixels / mVideoHeight
                params.width =  metrics.widthPixels
            }
            videoview.setLayoutParams(params)

        }

        videoview.start()
        val myFadeInAnimation: Animation = AnimationUtils.loadAnimation(this, anim.fadein)
        findViewById<ImageView>(id.LogoImage).startAnimation(myFadeInAnimation) //Set animation to your ImageView

        val WithGoogleButton = findViewById<Button>(id.withGoogle)
        WithGoogleButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))

        }
        val LoginButton = findViewById<Button>(id.login)
            LoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //Reproduz animação
        val fadeInDuration = 500 // Configure time values here

    }

    override fun onResume() {
        super.onResume()
        videoview.start()
    }

}