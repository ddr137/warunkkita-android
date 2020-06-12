package com.nahltech.warunkkita.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.nahltech.warunkkita.R
import com.nahltech.warunkkita.adapters.MySliderAdapter
import com.nahltech.warunkkita.models.Banner
import com.nahltech.warunkkita.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeFragment : Fragment() {

    var viewPager: ViewPager2? = null
    var mySliderLists: List<Banner>? = null
    var adapter: MySliderAdapter? = null
    private var indicatorLay: LinearLayout? = null
    private var api = ApiClient.instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.viewPager_slider)
        indicatorLay = view.findViewById<View>(R.id.lay_indicator) as LinearLayout
        getData()
        viewPager!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setupCurrentIndicator(position)
            }
        })

        val handler = Handler()
        val update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            viewPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 5000, 5000)
    }

    private fun getData() {
        api.getBannerSlider().enqueue(object : Callback<List<Banner>> {
            override fun onResponse(call: Call<List<Banner>>, response: Response<List<Banner>>) {
                mySliderLists = response.body()
                adapter = MySliderAdapter(requireContext(), mySliderLists as List<Banner>)
                viewPager!!.adapter = adapter
                setupIndicator()
                setupCurrentIndicator(0)
            }

            override fun onFailure(call: Call<List<Banner>>, t: Throwable) {}
        })
    }

    private fun setupIndicator() {
        val indicator = arrayOfNulls<ImageView>(adapter!!.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(4, 0, 4, 0)
        for (i in indicator.indices) {
            indicator[i] = ImageView(requireContext())
            indicator[i]!!.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.indicator_inactive))
            indicator[i]!!.layoutParams = layoutParams
            indicatorLay!!.addView(indicator[i])
        }
    }

    private fun setupCurrentIndicator(index: Int) {
        val itemCount = indicatorLay!!.childCount
        for (i in 0 until itemCount) {
            val imageView = indicatorLay!!.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(activity?.let { ContextCompat.getDrawable(it, R.drawable.indicator_active) })
            } else {
                imageView.setImageDrawable(activity?.let { ContextCompat.getDrawable(it, R.drawable.indicator_inactive) })
            }
        }
    }

    companion object {
        private var currentPage = 0
        private const val NUM_PAGES = 3

        fun newInstance(): HomeFragment{
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}