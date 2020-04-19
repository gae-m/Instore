package com.example.instore

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import models.Database

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_screen_activity_layout)

        Database.getElencoProdotti {
            Handler().postDelayed({
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            },4000)

        }
    }

}