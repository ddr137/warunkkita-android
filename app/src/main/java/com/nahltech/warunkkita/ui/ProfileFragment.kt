package com.nahltech.warunkkita.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nahltech.warunkkita.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cb_free_delivery.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tv_free_delivery.text = "Batalkan Free Ongkir"
            } else {
                tv_free_delivery.text = "Free Ongkir"
            }
        }
    }
}