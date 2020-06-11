package com.nahltech.warunkkita

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceNavigationView
import com.luseen.spacenavigation.SpaceOnClickListener
import com.nahltech.warunkkita.ui.HistoryFragment
import com.nahltech.warunkkita.ui.HomeFragment
import com.nahltech.warunkkita.ui.NotificationFragment
import com.nahltech.warunkkita.ui.ProfileFragment


class MainActivity : AppCompatActivity() {

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spaceNavigationView = findViewById<SpaceNavigationView>(R.id.space)
        val fragment = HomeFragment.newInstance()
        addFragment(fragment)

        spaceNavigationView.initWithSaveInstanceState(savedInstanceState)
        spaceNavigationView.addSpaceItem(SpaceItem("", R.drawable.ic_home_grey))
        spaceNavigationView.addSpaceItem(SpaceItem("", R.drawable.ic_notif_grey))
        spaceNavigationView.addSpaceItem(SpaceItem("", R.drawable.ic_bill_grey))
        spaceNavigationView.addSpaceItem(SpaceItem("", R.drawable.ic_user_grey))

        spaceNavigationView.setSpaceOnClickListener(object : SpaceOnClickListener {
            override fun onCentreButtonClick() {
                Toast.makeText(this@MainActivity, "Keranjang", Toast.LENGTH_SHORT).show()
            }

            override fun onItemClick(itemIndex: Int, itemName: String) {
                when (itemIndex) {
                    0 -> {
                        val fragment = HomeFragment.newInstance()
                        addFragment(fragment)
                        return
                    }
                    1 -> {
                        val fragment = NotificationFragment()
                        addFragment(fragment)
                        return
                    }
                    2 -> {
                        val fragment = HistoryFragment()
                        addFragment(fragment)
                        return
                    }
                    3 -> {
                        val fragment = ProfileFragment()
                        addFragment(fragment)
                        return
                    }
                }
            }

            override fun onItemReselected(itemIndex: Int, itemName: String) {

            }
        })
    }
}