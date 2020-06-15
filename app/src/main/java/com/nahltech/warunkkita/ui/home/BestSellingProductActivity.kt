package com.nahltech.warunkkita.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nahltech.warunkkita.R
import kotlinx.android.synthetic.main.activity_best_selling_product.*

class BestSellingProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_best_selling_product)
        toolbarUI()
    }

    private fun toolbarUI() {
        setSupportActionBar(toolbar_best_selling_product)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_best_selling_product.setNavigationIcon(R.drawable.ic_back_white)
        toolbar_best_selling_product.setNavigationOnClickListener { finish() }
    }
}