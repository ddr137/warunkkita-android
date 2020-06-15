package com.nahltech.warunkkita.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nahltech.warunkkita.R
import kotlinx.android.synthetic.main.activity_discount_product.*

class DiscountProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discount_product)
    }
    private fun toolbarUI() {
        setSupportActionBar(toolbar_discount_product)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_discount_product.setNavigationIcon(R.drawable.ic_back_white)
        toolbar_discount_product.setNavigationOnClickListener { finish() }
    }

}