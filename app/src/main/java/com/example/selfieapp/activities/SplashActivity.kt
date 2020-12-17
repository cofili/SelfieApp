package com.example.selfieapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.selfieapp.R
import com.example.selfieapp.helpers.SessionManager

class SplashActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    private val delayedTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var handler = Handler()
        handler.postDelayed({

            checkLogin()

            Intent(this, LoginActivity::class.java)

        }, delayedTime)
    }

    private fun checkLogin() {
        sessionManager = SessionManager(this)
        var intent = if (sessionManager.isLoggedIn()) {
            //user is logged in
            Intent(this, LoginActivity::class.java)
        } else {
            //user not logged in send to login activity
            Intent(this, LoginActivity::class.java)
        }

        startActivity(intent)
        finish()
    }


}