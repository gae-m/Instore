package com.example.instore.home

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager

class MyOnPageChangeListner (val imageSwipeLeft: ImageView,val imageSwipeRight: ImageView,val lastIndex: Int): ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when(position){
            0->{
                imageSwipeLeft.visibility = View.INVISIBLE
                imageSwipeRight.visibility = View.VISIBLE
            }
            lastIndex->{
                imageSwipeRight.visibility = View.INVISIBLE
                imageSwipeLeft.visibility = View.VISIBLE
            }
            else ->{
                imageSwipeRight.visibility = View.VISIBLE
                imageSwipeLeft.visibility = View.VISIBLE
            }
        }
    }


}
