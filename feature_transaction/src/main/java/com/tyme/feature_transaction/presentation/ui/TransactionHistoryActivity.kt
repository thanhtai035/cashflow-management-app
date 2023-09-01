package com.tyme.feature_transaction.presentation.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.tyme.base.presentation.activity.BaseActivity
import com.tyme.base.Common.Constant
import com.tyme.base_feature.common.Result
import com.tyme.feature_transaction.R
import com.tyme.feature_transaction.databinding.ActivityTransactionHistoryBinding
import com.tyme.feature_transaction.domain.model.TransactionDetail
import com.tyme.feature_transaction.domain.model.TransactionWeek
import com.tyme.feature_transaction.presentation.adapter.TransactionListAdapter
import com.tyme.feature_transaction.presentation.viewmodel.TransactionSummary
import com.tyme.feature_transaction.presentation.viewmodel.TransactionViewModel
import org.koin.android.ext.android.inject

class TransactionHistoryActivity : BaseActivity(R.layout.activity_transaction_history)   {
    private lateinit var binding: ActivityTransactionHistoryBinding
    private var entries: ArrayList<PieEntry> = ArrayList<PieEntry>()
    private lateinit var dataSet: PieDataSet
    private lateinit var transactionSummary: TransactionSummary

    private val viewModel: TransactionViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.transactionBackBtn.setOnClickListener{
            finish()
        }
    }

    override fun onStart() {
        viewModel.initialize()
        viewModel.transactionPage.observe(this) {
                response ->
            when (response) {
                is Result.Success -> {
                    initTransactionAdapter(response.data?.transactionDetailList?:ArrayList<TransactionDetail>())
                }
                is Result.Loading -> {
                    binding.transactionList.visibility = View.GONE
                    binding.listShimmer.startShimmer()
                    binding.listShimmer.visibility = View.VISIBLE
                } else -> {
            }
            }
        }

        viewModel.state.observe(this) {
            viewModel.getTransactionPage(Constant.TEST_USER_ID)
        }

        viewModel.transactionWeeks.observe(this) {
                response ->
            when (response) {
                is Result.Success -> {
                    loadPieChartData(response.data?:ArrayList<TransactionWeek>())
                    setupPieChart()
                }
                is Result.Loading -> {

                } else -> {
            }
            }
        }
        super.onStart()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupPieChart() {
        val pieChart = binding.pieChart
        val initialHighlight = Highlight(entries.indexOf(getCurrentSliceAt90Degrees()?:0).toFloat(), 0, 0)

        pieChart.apply {
            setUsePercentValues(false)
            description.isEnabled = false
            isRotationEnabled = true
            isDragDecelerationEnabled = true
            dragDecelerationFrictionCoef = 0.1f

            legend.isEnabled = false

            centerText = createCenterTextFormatter(getCurrentSliceAt90Degrees()?.label?:"", getCurrentSliceAt90Degrees()?.value.toString() + " $")
            setCenterTextColor(Color.WHITE)
            setExtraOffsets(0f, 20f, 0f, 0f)

            animateY(1400)

            setHoleColor(Color.TRANSPARENT)
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            this.holeRadius = 95f
            transparentCircleRadius = holeRadius + 4f

            setDrawEntryLabels(false)
            // Enable scaling animation

            pieChart.highlightValue(initialHighlight)
            centerText = createCenterTextFormatter(getCurrentSliceAt90Degrees()?.label?:"", getCurrentSliceAt90Degrees()?.value.toString() + "$")

            setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        val highlight = Highlight(entries.indexOf(getCurrentSliceAt90Degrees()?:0).toFloat(), 0, 0)
                        pieChart.highlightValue(highlight)
                        centerText = createCenterTextFormatter(getCurrentSliceAt90Degrees()?.label?:"", getCurrentSliceAt90Degrees()?.value.toString() + "$")
                    }
                    MotionEvent.ACTION_UP -> {
                        viewModel.setCategory(getCurrentSliceAt90Degrees()?.label)
                    }
                }
                false
            }
        }
    }

    private fun initTransactionAdapter(items: List<TransactionDetail>) {
        Handler().postDelayed({
            binding.transactionList.apply {
                adapter = TransactionListAdapter(items)
                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false

            }
        }, 2000)

        Handler().postDelayed({
            binding.listShimmer.stopShimmer()
            binding.listShimmer.visibility = View.GONE
            binding.transactionList.visibility = View.VISIBLE
        }, 4000)
    }

    private fun loadPieChartData(items: List<TransactionWeek>) {
        transactionSummary = viewModel.getSummary(items)
        var colors : ArrayList<Int> = ArrayList()
        for (item in 0 until transactionSummary.categoryDistribution.size) {
            entries.add(PieEntry(transactionSummary.categoryDistribution[item]*-1, transactionSummary.categoryTitleList[item].toString()))
            colors.add(transactionSummary.color[item])
        }
        val colorList = listOf(
            Color.parseColor("#FFC107"), // Custom color for Entry 1
            Color.parseColor("#03A9F4"), // Custom color for Entry 2
            Color.parseColor("#4CAF50"), // Custom color for Entry 3
            Color.parseColor("#9C27B0"), // Custom color for Entry 4
            Color.parseColor("#FF5722"), // Custom color for Entry 5
            Color.parseColor("#E91E63"), // Custom color for Entry 6
            Color.parseColor("#607D8B") // Custom color for Entry 7
        )
        dataSet = PieDataSet(entries, "")
        dataSet.colors = colorList
        val data = PieData(dataSet)
        data.setDrawValues(false)
        binding.pieChart.data = data

    }

    private fun getCurrentSliceAt90Degrees(): PieEntry? {
        val targetAngle = 90f
        val chartRotationAngle = binding.pieChart.rotationAngle
        val totalValue = entries.sumByDouble { it.value.toDouble() }.toFloat()

        val entryAngles = entries.map { (it.value / totalValue) * 360f }
        val numEntries = entries.size
        val sliceAngle = 360f / numEntries

        // Calculate the raw angle of the slice at 90 degrees
        val rawAngleAt90Degrees = (targetAngle - chartRotationAngle + 360f) % 360f

        // Find the index of the slice closest to the raw angle
        val closestIndex = (rawAngleAt90Degrees / sliceAngle).toInt()

        return entries.getOrNull(closestIndex)
    }

    private fun createCenterTextFormatter(aboveText: String, belowText: String): SpannableStringBuilder {
        val centerTextFormatter = SpannableStringBuilder()
            .append("$aboveText\n")
            .append(belowText)

        centerTextFormatter.setSpan(
            RelativeSizeSpan(2.5f), // Set the size of the above text (adjust the value as needed)
            0,
            aboveText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        centerTextFormatter.setSpan(
            StyleSpan(android.graphics.Typeface.BOLD),
            aboveText.length + 1, // Start index of the below text (+1 for the line break)
            centerTextFormatter.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Apply a different text size to the below text
        centerTextFormatter.setSpan(
            RelativeSizeSpan(1.5f), // Modify the scale factor as needed
            aboveText.length + 1, // Add 1 to account for the line break character
            centerTextFormatter.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return centerTextFormatter
    }
}