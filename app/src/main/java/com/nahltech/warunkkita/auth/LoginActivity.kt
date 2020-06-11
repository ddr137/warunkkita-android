package com.nahltech.warunkkita.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.nahltech.warunkkita.MainActivity
import com.nahltech.warunkkita.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        clickIntent()
        toolbarUI()
    }

    private fun clickIntent() {
        register_now.setOnClickListener {
            val moveIntent = Intent(this, RegisterActivity::class.java)
            startActivity(moveIntent)
        }
        login.setOnClickListener {
            val moveIntent = Intent(this, MainActivity::class.java)
            startActivity(moveIntent)
        }
    }

    private fun toolbarUI() {
        setSupportActionBar(toolbar_login)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_login.setNavigationIcon(R.drawable.ic_back)
        toolbar_login.setNavigationOnClickListener { finish() }
    }
}