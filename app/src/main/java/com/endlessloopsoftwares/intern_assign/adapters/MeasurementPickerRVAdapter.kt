package com.endlessloopsoftwares.intern_assign.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.endlessloopsoftwares.intern_assign.R
import com.endlessloopsoftwares.intern_assign.view_holders.MeasurementPickerViewHolder

class MeasurementPickerRVAdapter(private val isUnitCm: Boolean, private val givenContext: Context) :
    RecyclerView.Adapter<MeasurementPickerViewHolder>() {

    var eachItemCallback: ItemsCallback? = null
    private val clickListener =
        View.OnClickListener { v -> v?.let { eachItemCallback?.onItemClicked(it) } }

    private val cmUnitArray = Array(310) {
        it + 1
    }

    private val ftUnitArray = Array(120) {
        it + 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MeasurementPickerViewHolder {
        val viewToInflate = LayoutInflater.from(parent.context).inflate(
            R.layout.measurement_rv_item,
            parent,
            false
        )

        viewToInflate.setOnClickListener(clickListener)
        return MeasurementPickerViewHolder(
            viewToInflate
        )
    }

    // setting up all the lines and number notations for the rv items
    override fun onBindViewHolder(holder: MeasurementPickerViewHolder, position: Int) {
        if (isUnitCm) {
            val currItem = cmUnitArray[position]
            if (currItem % 10 == 0) {
                val scale: Float = givenContext.resources.displayMetrics.density
                val heightToApply = (54 * scale + 0.5f).toInt()
                val widthToApply = (2 * scale + 0.5f).toInt()
                holder.mtStick.layoutParams = LinearLayout.LayoutParams(widthToApply, heightToApply)
                holder.mtValue.text = currItem.toString()
                holder.mtValue.visibility = View.VISIBLE
            } else if (currItem % 5 == 0) {
                val scale: Float = givenContext.resources.displayMetrics.density
                val heightToApply = (36 * scale + 0.5f).toInt()
                val widthToApply = (2 * scale + 0.5f).toInt()
                holder.mtStick.layoutParams = LinearLayout.LayoutParams(widthToApply, heightToApply)
                holder.mtValue.visibility = View.INVISIBLE
            } else {
                val scale: Float = givenContext.resources.displayMetrics.density
                val heightToApply = (18 * scale + 0.5f).toInt()
                val widthToApply = (2 * scale + 0.5f).toInt()
                holder.mtStick.layoutParams = LinearLayout.LayoutParams(widthToApply, heightToApply)
                holder.mtValue.visibility = View.INVISIBLE
            }
        } else {
            val currItem = ftUnitArray[position]
            if (currItem % 12 == 0) {
                val scale: Float = givenContext.resources.displayMetrics.density
                val heightToApply = (54 * scale + 0.5f).toInt()
                val widthToApply = (2 * scale + 0.5f).toInt()
                holder.mtStick.layoutParams = LinearLayout.LayoutParams(widthToApply, heightToApply)
                holder.mtValue.text =
                    givenContext.getString(R.string.footText, (currItem / 12).toString())
                holder.mtValue.visibility = View.VISIBLE
            } else if (currItem % 6 == 0) {
                val scale: Float = givenContext.resources.displayMetrics.density
                val heightToApply = (36 * scale + 0.5f).toInt()
                val widthToApply = (2 * scale + 0.5f).toInt()
                holder.mtStick.layoutParams = LinearLayout.LayoutParams(widthToApply, heightToApply)
                holder.mtValue.visibility = View.INVISIBLE
            } else {
                val scale: Float = givenContext.resources.displayMetrics.density
                val heightToApply = (18 * scale + 0.5f).toInt()
                val widthToApply = (2 * scale + 0.5f).toInt()
                holder.mtStick.layoutParams = LinearLayout.LayoutParams(widthToApply, heightToApply)
                holder.mtValue.visibility = View.INVISIBLE
            }
        }

    }

    override fun getItemCount(): Int {
        return if (isUnitCm) cmUnitArray.size else ftUnitArray.size
    }

    interface ItemsCallback {
        fun onItemClicked(givenView: View)
    }
}