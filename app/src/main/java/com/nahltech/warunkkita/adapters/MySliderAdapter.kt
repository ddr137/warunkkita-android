package com.nahltech.warunkkita.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nahltech.warunkkita.R
import com.nahltech.warunkkita.models.Banner
import com.squareup.picasso.Picasso

class MySliderAdapter(context: Context, mySliderLists: List<Banner>, viewPager: ViewPager2) : RecyclerView.Adapter<MySliderAdapter.ViewHolder>() {
    private val mySliderLists: List<Banner>
    private val mInflater: LayoutInflater
    private val viewPager: ViewPager2
    var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.slider_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ob = mySliderLists[position]
        if (ob.type == "news"){
            holder.sliderNews.visibility = View.VISIBLE
            Picasso.get().load(ob.image).into(holder.myClickImage)

        } else if (ob.type == "campaign"){
            holder.sliderNews.visibility = View.GONE
            holder.myImage.visibility = View.VISIBLE
            Picasso.get().load(ob.image).into(holder.myImage)
        }
    }

    override fun getItemCount(): Int {
        return mySliderLists.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myImage: ImageView
        var myClickImage: ImageView
        var imageSlider: CardView
        var sliderNews: RelativeLayout = itemView.findViewById(R.id.slider_news)
        var button: Button? = null

        init {
            myImage = itemView.findViewById(R.id.myimage)
            myClickImage = itemView.findViewById(R.id.image_list_slider)
            imageSlider = itemView.findViewById(R.id.click_image_slider)
        }
    }

    init {
        mInflater = LayoutInflater.from(context)
        this.mySliderLists = mySliderLists
        this.viewPager = viewPager
        this.context = context
    }
}