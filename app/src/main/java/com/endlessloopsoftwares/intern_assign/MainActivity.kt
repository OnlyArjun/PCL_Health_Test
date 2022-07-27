package com.endlessloopsoftwares.intern_assign

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import com.endlessloopsoftwares.intern_assign.adapters.MeasurementPickerRVAdapter
import com.endlessloopsoftwares.intern_assign.databinding.ActivityMainBinding
import com.endlessloopsoftwares.intern_assign.layout_manager.MeasurementPickerRVLayoutManager

class MainActivity : AppCompatActivity() {
    private lateinit var myViewBinding: ActivityMainBinding
    private var currSelection: Float = 1.0F
    private var currUnit: String = "cm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(myViewBinding.root)

        setUpMeasurementChoices()
        setUpLayoutManager(true)
        setUpRVAdapter(true)
        myViewBinding.nextButton.setOnClickListener {
            Toast.makeText(this, "$currSelection $currUnit", Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpLayoutManager(isUnitCm: Boolean) {
        val currContext = this
        myViewBinding.rvMeasurement.layoutManager =
            MeasurementPickerRVLayoutManager(currContext).apply {
                itemCallback = object : MeasurementPickerRVLayoutManager.OnItemSelectedListener {
                    override fun onItemSelected(layoutPosition: Int) {
                        myViewBinding.displayStick.visibility = View.INVISIBLE
                        myViewBinding.displayMeasurement.visibility = View.INVISIBLE
                        val scale: Float = currContext.resources.displayMetrics.density
                        if (isUnitCm) {
                            currSelection = layoutPosition.toFloat()
                            myViewBinding.displayMeasurement.text = layoutPosition.toString()
                            if (layoutPosition % 10 == 0) {
                                val heightToApply = (54 * scale + 0.5f).toInt()
                                val widthToApply = (2 * scale + 0.5f).toInt()
                                myViewBinding.displayStick.layoutParams =
                                    LinearLayout.LayoutParams(widthToApply, heightToApply)
                            } else if (layoutPosition % 5 == 0) {
                                val heightToApply = (36 * scale + 0.5f).toInt()
                                val widthToApply = (2 * scale + 0.5f).toInt()
                                myViewBinding.displayStick.layoutParams =
                                    LinearLayout.LayoutParams(widthToApply, heightToApply)
                            } else {
                                val heightToApply = (18 * scale + 0.5f).toInt()
                                val widthToApply = (2 * scale + 0.5f).toInt()
                                myViewBinding.displayStick.layoutParams =
                                    LinearLayout.LayoutParams(widthToApply, heightToApply)
                            }
                        } else {
                            currSelection = layoutPosition / 12 + layoutPosition % 12 * 0.01F
                            if (layoutPosition % 12 == 0) {
                                val heightToApply = (54 * scale + 0.5f).toInt()
                                val widthToApply = (2 * scale + 0.5f).toInt()
                                myViewBinding.displayStick.layoutParams =
                                    LinearLayout.LayoutParams(widthToApply, heightToApply)
                                myViewBinding.displayMeasurement.text = currContext.getString(
                                    R.string.footText,
                                    (layoutPosition / 12).toString()
                                )
                            } else if (layoutPosition % 6 == 0) {
                                val heightToApply = (36 * scale + 0.5f).toInt()
                                val widthToApply = (2 * scale + 0.5f).toInt()
                                myViewBinding.displayStick.layoutParams =
                                    LinearLayout.LayoutParams(widthToApply, heightToApply)
                                myViewBinding.displayMeasurement.text = currContext.getString(
                                    R.string.footInchText,
                                    (layoutPosition / 12).toString(),
                                    (layoutPosition % 12).toString()
                                )
                            } else {
                                val heightToApply = (18 * scale + 0.5f).toInt()
                                val widthToApply = (2 * scale + 0.5f).toInt()
                                myViewBinding.displayStick.layoutParams =
                                    LinearLayout.LayoutParams(widthToApply, heightToApply)
                                myViewBinding.displayMeasurement.text = currContext.getString(
                                    R.string.footInchText,
                                    (layoutPosition / 12).toString(),
                                    (layoutPosition % 12).toString()
                                )
                            }
                        }
                        myViewBinding.displayStick.visibility = View.VISIBLE
                        myViewBinding.displayMeasurement.visibility = View.VISIBLE
                    }
                }
            }
    }

    private fun setUpMeasurementChoices() {
        // setting up the snap helper for the picker
        LinearSnapHelper().attachToRecyclerView(myViewBinding.rvMeasurement)

        // setting the listener for changes when we change the checked unit
        myViewBinding.measurementUnitGroup.setOnCheckedChangeListener { _, i ->
            if (i == R.id.cm_radio_button) {
                currUnit = "cm"
                setUpRVAdapter(true)
            } else {
                currUnit = "ft"
                setUpRVAdapter(false)
            }
        }
    }

    private fun setUpRVAdapter(isUnitCm: Boolean) {
        myViewBinding.displayStick.visibility = View.INVISIBLE
        myViewBinding.displayMeasurement.visibility = View.INVISIBLE
        // setting up padding for the RV to display items at the center
        val currScreenWidth = Resources.getSystem().displayMetrics.widthPixels / 2
        val dpToPxscale: Float = this.resources.displayMetrics.density
        var currRatio = if (isUnitCm) 38 else 30
        currRatio = (currRatio * dpToPxscale + 0.5f).toInt()
        val padding: Int = currScreenWidth - currRatio
        myViewBinding.rvMeasurement.setPadding(padding, 0, padding, 0)

        myViewBinding.rvMeasurement.adapter = MeasurementPickerRVAdapter(isUnitCm, this).apply {
            eachItemCallback = object : MeasurementPickerRVAdapter.ItemsCallback {
                override fun onItemClicked(givenView: View) {
                    val currItemPos = myViewBinding.rvMeasurement.getChildLayoutPosition(givenView)
                    myViewBinding.rvMeasurement.smoothScrollToPosition(
                        currItemPos
                    )
                }
            }
        }
        setUpLayoutManager(isUnitCm)
    }

}