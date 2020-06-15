package com.nahltech.warunkkita.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nahltech.warunkkita.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        clickIntent()
        toolbarUI()
    }

    private fun clickIntent() {
        login_here.setOnClickListener {
            val moveIntent = Intent(this, LoginActivity::class.java)
            startActivity(moveIntent)
            finish()
        }
    }

    private fun toolbarUI() {
        setSupportActionBar(toolbar_register)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_register.setNavigationIcon(R.drawable.ic_back_black)
        toolbar_register.setNavigationOnClickListener { finish() }
    }
}