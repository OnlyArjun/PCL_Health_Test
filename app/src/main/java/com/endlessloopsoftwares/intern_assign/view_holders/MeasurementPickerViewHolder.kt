package com.endlessloopsoftwares.intern_assign.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.endlessloopsoftwares.intern_assign.R

class MeasurementPickerViewHolder(givenView: View): RecyclerView.ViewHolder(givenView) {
    val mtStick: View = givenView.findViewById(R.id.measurement_stick)
    val mtValue: TextView = givenView.findViewById(R.id.measurement_value)
}