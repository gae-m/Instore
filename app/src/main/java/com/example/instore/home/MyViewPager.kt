package com.example.instore.home

import android.content.Context
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager

class MyViewPager(context: Context) : ViewPager(context){
    init{
        overScrollMode = View.OVER_SCROLL_NEVER
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return false
    }
}