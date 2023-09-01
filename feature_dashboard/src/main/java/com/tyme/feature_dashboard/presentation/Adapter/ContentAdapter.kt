package com.tyme.feature_dashboard.presentation.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tyme.feature_dashboard.R
import com.tyme.feature_dashboard.domain.model.TransactionWeek
//Import Chart
import android.widget.CompoundButton
import android.widget.ToggleButton
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class ContentAdapter(var items: ArrayList<Int>, var transactionList: List<TransactionWeek>) :
    RecyclerView.Adapter<ContentAdapter.Viewholder>()
{
    var context: Context? = null
    var balance = 5000 //GET VALUE REFERENCE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var inflator =
            LayoutInflater.from(parent.context).inflate(R.layout.credit_page_item, parent, false)



        when (items[viewType]) {
            // Credit Card page
            0 -> {
                inflator = LayoutInflater.from(parent.context).inflate(R.layout.credit_page_item, parent, false)
                var inputList = transactionList


                // Mock Data END
                //val chartView = chartsActivity(this, R.layout.activity_main,mockList,balance)

                //}
                val lineChart = inflator.findViewById<LineChart>(R.id.LineChart)
                val barChart = inflator.findViewById<BarChart>(R.id.BarChart)

                val buttonC1M1 : ToggleButton = inflator.findViewById(R.id.buttonChart1M1)
                val buttonC1M2 : ToggleButton = inflator.findViewById(R.id.buttonChart1M2)
                val buttonC1M3 : ToggleButton = inflator.findViewById(R.id.buttonChart1M3)

                val buttonC2M1 : ToggleButton = inflator.findViewById(R.id.buttonChart2M1)
                val buttonC2M2 : ToggleButton = inflator.findViewById(R.id.buttonChart2M2)
                val buttonC2M3 : ToggleButton = inflator.findViewById(R.id.buttonChart2M3)


                var dataListM1 = mutableListOf<TransactionWeek>()
                var dataListM2 = mutableListOf<TransactionWeek>()
                var dataListM3 = mutableListOf<TransactionWeek>()
                var dataListTotalM1 = mutableListOf<Double>()
                var dataListTotalM2 = mutableListOf<Double>()
                var dataListTotalM3 = mutableListOf<Double>()
                var displayListBar = mutableListOf<TransactionWeek>()
                var displayListLine = mutableListOf<Double>()

                val currentMonth = inputList[0].month
                var monthRange = arrayOf<String>()

                when (currentMonth){
                    1 -> monthRange = arrayOf("January","February", "March")
                    2 -> monthRange = arrayOf("February","March","April")
                    3 -> monthRange = arrayOf("March","April","May")
                    4 -> monthRange = arrayOf("April","May","June")
                    5 -> monthRange = arrayOf("May","June","July")
                    6 -> monthRange = arrayOf("June","July","August")
                    7 -> monthRange = arrayOf("July","August","September")
                    8 -> monthRange = arrayOf("August","September","October")
                    9 -> monthRange = arrayOf("September","October","November")
                    10 -> monthRange = arrayOf("October","November","December")
                    11 -> monthRange = arrayOf("November","December","January")
                    12 -> monthRange = arrayOf("December","January","February")
                }
                setTextButton(buttonC1M1,monthRange[0])
                setTextButton(buttonC2M1,monthRange[0])
                setTextButton(buttonC1M2,monthRange[1])
                setTextButton(buttonC2M2,monthRange[1])
                setTextButton(buttonC1M3,monthRange[2])
                setTextButton(buttonC2M3,monthRange[2])

                for (items in inputList) {
                    if (items.month == currentMonth) {
                        dataListM1.add(items)
                    } else if (items.month == currentMonth + 1) {
                        dataListM2.add(items)
                    } else if (items.month == currentMonth + 2) {
                        dataListM3.add(items)
                    }
                }
                var balanceM1 = balance.toDouble()
                for (items in dataListM1) {
                    balanceM1 = balanceM1 + items.totalIncome - items.totalOutcome
                    dataListTotalM1.add(balanceM1)
                }
                var balanceM2 = balanceM1
                for (items in dataListM2) {
                    balanceM2 = balanceM2 + items.totalIncome - items.totalOutcome
                    dataListTotalM2.add(balanceM2)
                }
                var balanceM3 = balanceM2
                for (items in dataListM3) {
                    balanceM3 = balanceM3 + items.totalIncome - items.totalOutcome
                    dataListTotalM3.add(balanceM3)
                }

                val mv = MyMarkerView(inflator.context, R.layout.custom_marker)
                //combinedChart.setMarker(mv)

                val entries1 = setEntryTotal(displayListLine,0)
                val entries2 = setBarEntryPerTime(displayListBar)

                setLineChart(entries1,lineChart)
                setBarChart(entries2,barChart)
                barChart.marker = mv
                lineChart.marker = mv

                buttonC1M1.setOnCheckedChangeListener { buttonC1M1, isOn ->
                    addMonthListToMainList(displayListBar,buttonC1M1,dataListM1,buttonC1M2,dataListM2,buttonC1M3,dataListM3)
                    barChart.notifyDataSetChanged()
                    setBarChart(setBarEntryPerTime(displayListBar),barChart)
                    barChart.invalidate()

                }

                buttonC1M2.setOnCheckedChangeListener { buttonC1M2, isOn ->
                    addMonthListToMainList(displayListBar,buttonC1M1,dataListM1,buttonC1M2,dataListM2,buttonC1M3,dataListM3)
                    barChart.notifyDataSetChanged()
                    setBarChart(setBarEntryPerTime(displayListBar),barChart)
                    barChart.invalidate()
                }

                buttonC1M3.setOnCheckedChangeListener { buttonC1M3, isOn ->
                    addMonthListToMainList(displayListBar,buttonC1M1,dataListM1,buttonC1M2,dataListM2,buttonC1M3,dataListM3)
                    barChart.notifyDataSetChanged()
                    setBarChart(setBarEntryPerTime(displayListBar),barChart)
                    barChart.invalidate()
                }

                buttonC2M1.setOnCheckedChangeListener { buttonC2M1, isOn ->
                    addMonthListToMainListLine(displayListLine,buttonC2M1,dataListTotalM1,buttonC2M2,dataListTotalM2,buttonC2M3,dataListTotalM3)
                    lineChart.notifyDataSetChanged()
                    setLineChart(setEntryTotal(displayListLine,balance),lineChart)
                    lineChart.invalidate()
                }

                buttonC2M2.setOnCheckedChangeListener { buttonC2M2, isOn ->
                    addMonthListToMainListLine(displayListLine,buttonC2M1,dataListTotalM1,buttonC2M2,dataListTotalM2,buttonC2M3,dataListTotalM3)
                    lineChart.notifyDataSetChanged()
                    setLineChart(setEntryTotal(displayListLine,balance),lineChart)
                    lineChart.invalidate()
                }

                buttonC2M3.setOnCheckedChangeListener { buttonC2M3, isOn ->
                    addMonthListToMainListLine(displayListLine,buttonC2M1,dataListTotalM1,buttonC2M2,dataListTotalM2,buttonC2M3,dataListTotalM3)
                    lineChart.notifyDataSetChanged()
                    setLineChart(setEntryTotal(displayListLine,balance),lineChart)
                    lineChart.invalidate()
                }
            }

            // Saving page item
            1 ->  inflator =
                LayoutInflater.from(parent.context).inflate(R.layout.saving_page_item, parent, false)
            // Loadning Page item
            2 ->  inflator =
                LayoutInflater.from(parent.context).inflate(R.layout.loaning_page_item, parent, false)
        }

        context = parent.context
        return Viewholder(inflator)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    //Function for charts
    fun setLineChart(dataSetEntry: List<Entry>,lineChart: LineChart) {

        val dataSet = LineDataSet(dataSetEntry,"My Data")
        val linedata = LineData(dataSet)
        lineChart.data = linedata
        lineChart.data.setValueTextSize(15f)
        lineChart.setDrawGridBackground(false)
        //lineChart.axisLeft.setDrawLabels(false)
        lineChart.getXAxis().setEnabled(false)
        lineChart.axisRight.setDrawLabels(false)
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawAxisLine(false)
        lineChart.xAxis.setDrawLabels(false)
        lineChart.legend.isEnabled = false   // Hide the legend

    }

    fun setBarChart(dataSetEntry: MutableList<BarEntry>, barChart: BarChart) {

        val dataSet = BarDataSet(dataSetEntry,"My Data")
        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.data.setValueTextSize(15f)
        barChart.setDrawGridBackground(false)
        barChart.getXAxis().setEnabled(false)
        barChart.axisRight.setDrawLabels(false)
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawAxisLine(false)
        barChart.xAxis.setDrawLabels(true)
        barChart.legend.isEnabled = false   // Hide the legend

    }
    fun setEntryTotal(dataSetEntry: MutableList<Double>, balance: Int): MutableList<Entry> {
        val dataSet = mutableListOf<Entry>()
        var yValue = balance.toFloat()
        var xValue = 0f
        for (items in dataSetEntry) {
            yValue = items.toFloat()
            xValue += 1f
            dataSet.add(Entry(xValue,yValue))
        }
        return dataSet
    }


    fun setBarEntryPerTime(dataSetEntry: List<TransactionWeek>): MutableList<BarEntry> {
        val dataSet = mutableListOf<BarEntry>()
        var yValue = 0f
        var xValue = 0f
        for (items: TransactionWeek in dataSetEntry) {
            yValue = (items.totalIncome - items.totalOutcome).toFloat()
            xValue += 1f
            dataSet.add(BarEntry(xValue, yValue))
        }
        return dataSet
    }


    fun addMonthListToMainList (
        output: MutableList<TransactionWeek>,
        buttonM1: CompoundButton, ListM1: List<TransactionWeek>,
        buttonM2: CompoundButton, ListM2: List<TransactionWeek>,
        buttonM3: CompoundButton, ListM3: List<TransactionWeek>) {
        output.clear()
        if (buttonM1.isChecked) {
            output.addAll(ListM1)
        }
        if (buttonM2.isChecked) {
            output.addAll(ListM2)
        }
        if (buttonM3.isChecked) {
            output.addAll(ListM3)
        }
    }

    fun addMonthListToMainListLine (
        output: MutableList<Double>,
        buttonM1: CompoundButton, ListM1: List<Double>,
        buttonM2: CompoundButton, ListM2: List<Double>,
        buttonM3: CompoundButton, ListM3: List<Double>) {
        output.clear()
        if (buttonM1.isChecked) {
            output.addAll(ListM1)
        }
        if (buttonM2.isChecked) {
            output.addAll(ListM2)
        }
        if (buttonM3.isChecked) {
            output.addAll(ListM3)
        }
    }

    fun setTextButton(button: ToggleButton, text: String) {
        button.textOn = text
        button.textOff = text
        button.setText(text)
    }
}