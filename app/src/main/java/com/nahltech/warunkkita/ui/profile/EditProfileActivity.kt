package com.nahltech.warunkkita.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nahltech.warunkkita.R
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        toolbarUI()

    }

    private fun toolbarUI() {
        setSupportActionBar(toolbar_edit_account)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_edit_account.setNavigationIcon(R.drawable.ic_back_white)
        toolbar_edit_account.setNavigationOnClickListener { finish() }
    }
}