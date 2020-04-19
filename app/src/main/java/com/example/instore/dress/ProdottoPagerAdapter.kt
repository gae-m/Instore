package com.example.instore.dress

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide

class ProdottoPagerAdapter(val context: Context, val imageUrlList: Array<String>): PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object` as ImageView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        Glide.with(context).load(imageUrlList[position]).into(imageView)
        imageView.scaleType= ImageView.ScaleType.CENTER_INSIDE
        container.addView(imageView)
        return imageView
    }

    override fun getCount(): Int {
        return imageUrlList.size
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }

}