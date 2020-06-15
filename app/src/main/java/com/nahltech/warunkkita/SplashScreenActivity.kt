package com.nahltech.warunkkita

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.nahltech.warunkkita.auth.LoginActivity
import com.nahltech.warunkkita.utils.Constants

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            if (Constants.getToken(this@SplashScreenActivity) == "undefined"
                && Constants.getIdUser(this@SplashScreenActivity) == "undefined"
            ) {
                startActivity(Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }).also { finish() }
            } else {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            finish()
        }, 2000L)

    }
}