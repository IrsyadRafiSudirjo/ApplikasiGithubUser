package com.example.applikasigithubuser2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler(mainLooper)
        handler.postDelayed({
            finish()
            val move = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(move)
        }, 3000)

    }
}