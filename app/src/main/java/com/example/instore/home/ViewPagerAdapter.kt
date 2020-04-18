package com.example.instore.home

import android.content.Context
import android.database.DataSetObserver
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide

class ViewPagerAdapter(val context: Context, val imageResourceList: List<Int>): PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object` as ImageView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
//        Glide.with(context).load(imageResourceList[position]).into(imageView)
        imageView.setImageResource(imageResourceList[position])
        imageView.scaleType= ImageView.ScaleType.FIT_XY
        container.addView(imageView)
        return imageView
    }

    override fun getCount(): Int {
        return imageResourceList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }

}