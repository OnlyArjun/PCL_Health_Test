package com.endlessloopsoftwares.intern_assign.layout_manager

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.sqrt

class MeasurementPickerRVLayoutManager(val currContext: Context): LinearLayoutManager(currContext) {
    init {
        orientation = HORIZONTAL
    }

    var itemCallback: OnItemSelectedListener? = null
    private lateinit var myRecyclerView: RecyclerView

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        myRecyclerView = view!!
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        scaleDownView()
    }

    private fun scaleDownView() {
        val mid = width / 2.0f
        for (i in 0 until childCount) {

            // Calculating the distance of the child from the center
            val child = getChildAt(i)!!
            val childMid = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2.0f
            val distanceFromCenter = abs(mid - childMid)

            // The scaling formula
            val scale = 1- sqrt((distanceFromCenter/width).toDouble()).toFloat()*0.66f

            // Set scale to view
            child.scaleX = scale
            child.scaleY = scale
        }
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        super.smoothScrollToPosition(recyclerView, state, position)
        val mySmoothScroller = object : LinearSmoothScroller(currContext) {
            override fun getHorizontalSnapPreference(): Int {
                return super.getHorizontalSnapPreference()

            }
        }

        mySmoothScroller.targetPosition = position
        startSmoothScroll(mySmoothScroller)
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            scaleDownView()
            scrolled
        } else {
            0
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)

        // When scroll stops we notify on the selected item
        if (state == RecyclerView.SCROLL_STATE_IDLE) {

            // Find the closest child to the recyclerView center --> this is the selected item.
            val recyclerViewCenterX = getRecyclerViewCenterX()
            var minDistance = myRecyclerView.width
            var position = -1
            for (i in 0 until myRecyclerView.childCount) {
                val child = myRecyclerView.getChildAt(i)
                val childCenterX = getDecoratedLeft(child) + (getDecoratedRight(child) - getDecoratedLeft(child)) / 2
                val newDistance = abs(childCenterX - recyclerViewCenterX)
                if (newDistance < minDistance) {
                    minDistance = newDistance
                    position = myRecyclerView.getChildLayoutPosition(child)
                }
            }

            // Notify on item selection
            itemCallback?.onItemSelected(position)
        }
    }

    private fun getRecyclerViewCenterX() : Int {
        return (myRecyclerView.right - myRecyclerView.left)/2 + myRecyclerView.left
    }

    interface OnItemSelectedListener {
        fun onItemSelected(layoutPosition: Int)
    }
}