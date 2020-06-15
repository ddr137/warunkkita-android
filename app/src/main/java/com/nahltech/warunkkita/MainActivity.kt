package com.nahltech.warunkkita

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceNavigationView
import com.luseen.spacenavigation.SpaceOnClickListener
import com.nahltech.warunkkita.ui.HistoryFragment
import com.nahltech.warunkkita.ui.NotificationFragment
import com.nahltech.warunkkita.ui.home.HomeFragment
import com.nahltech.warunkkita.ui.profile.ProfileFragment


class MainActivity : AppCompatActivity() {

    @SuppressLint("PrivateResource")
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
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
                        val fragmentHome = HomeFragment.newInstance()
                        addFragment(fragmentHome)
                        return
                    }
                    1 -> {
                        val fragmentNotification = NotificationFragment()
                        addFragment(fragmentNotification)
                        return
                    }
                    2 -> {
                        val fragmentHistory = HistoryFragment()
                        addFragment(fragmentHistory)
                        return
                    }
                    3 -> {
                        val fragmentProfile = ProfileFragment()
                        addFragment(fragmentProfile)
                        return
                    }
                }
            }

            override fun onItemReselected(itemIndex: Int, itemName: String) {

            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val spaceNavigationView = findViewById<SpaceNavigationView>(R.id.space)
        spaceNavigationView.onSaveInstanceState(outState)
    }
}