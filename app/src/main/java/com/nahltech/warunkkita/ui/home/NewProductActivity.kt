package com.nahltech.warunkkita.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nahltech.warunkkita.R
import kotlinx.android.synthetic.main.activity_new_product.*

class NewProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)
        toolbarUI()
    }

    private fun toolbarUI() {
        setSupportActionBar(toolbar_new_product)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_new_product.setNavigationIcon(R.drawable.ic_back)
        toolbar_new_product.setNavigationOnClickListener { finish() }
    }
}