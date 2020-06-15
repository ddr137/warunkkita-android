package com.nahltech.warunkkita.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.nahltech.warunkkita.R
import com.nahltech.warunkkita.data.models.Banner


class MySliderAdapter(
    var context: Context,
    private val mySliderLists: List<Banner>
) :
    RecyclerView.Adapter<MySliderAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.slider_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ob = mySliderLists[position]
        if (ob.type == "campaign") {
            holder.myImage.load(ob.image){
                crossfade(true)
            }
        }
    }

    override fun getItemCount(): Int {
        return mySliderLists.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myImage: ImageView = itemView.findViewById(R.id.myimage)
    }

}