package com.jackzhao.easyrecyclerview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jackzhao.easyrecyclerview.divider.RecyclerViewDivider

class EasyRecyclerView : RecyclerView {
    constructor(context: Context) : super(context) {
        this.setLinearLayout(context, LinearLayoutManager.VERTICAL)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.setLinearLayout(context, LinearLayoutManager.VERTICAL)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.setLinearLayout(context, LinearLayoutManager.VERTICAL)
    }


    fun setLinearLayout(context: Context, orientation: Int) {
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = orientation
        this.layoutManager = mLayoutManager
    }


    fun setGridLayout(
        context: Context,
        spanCount: Int,
        canScrollVertically: Boolean,
        canScrollHorizontally: Boolean
    ) {
        val gridLayoutManager: GridLayoutManager =
            object : GridLayoutManager(context, spanCount) {
                override fun canScrollVertically(): Boolean {
                    return canScrollVertically
                }

                override fun canScrollHorizontally(): Boolean {
                    return canScrollHorizontally
                }
            }
        this.layoutManager = gridLayoutManager;
    }

    fun addItemDecoration(
        context: Context,
        orientation: Int = LinearLayoutManager.VERTICAL,
        dividerHeight: Int = 1,
        dividerColor: Int = Color.parseColor("#efefef")
    ) {
        this.addItemDecoration(
            RecyclerViewDivider(
                context,
                orientation,
                dividerHeight,
                dividerColor
            )
        )
    }
}