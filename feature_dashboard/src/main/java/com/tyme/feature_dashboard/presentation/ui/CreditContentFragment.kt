package com.tyme.feature_dashboard.presentation.ui

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.*
import com.hadiidbouk.charts.BarData
import com.straiberry.android.charts.tooltip.PointTooltip
import com.tyme.feature_dashboard.R
import com.tyme.feature_dashboard.databinding.CreditPageItemBinding
import com.tyme.feature_dashboard.domain.model.TransactionDetail
import com.tyme.feature_dashboard.domain.model.TransactionWeek
import com.tyme.feature_dashboard.domain.model.User
import com.tyme.feature_dashboard.presentation.Adapter.weeklyBalances
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.round

// Credit card page
class CreditContentFragment : SuperBottomSheetFragment() {
    private lateinit var binding: CreditPageItemBinding

    // Pass parater
    companion object {
        private const val KEY_USER = "key_user"
        private const val KEY_TRANSACTION = "key_transaction"
        private const val KEY_TRANSACTION_LIST = "key_transactionList"

        fun putData(user: String, list: String, transactionList: String ): CreditContentFragment {
            val args = Bundle().apply {
                putString(KEY_USER, user)
                putString(KEY_TRANSACTION, list)
                putString(KEY_TRANSACTION_LIST, transactionList)

            }
            val fragment = CreditContentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // Get arguement data
        val transactions: List<TransactionWeek> = Json.decodeFromString(ListSerializer(TransactionWeek.serializer()), arguments?.getString(KEY_TRANSACTION)?:"")
        val transactionDetailList: List<TransactionDetail> = Json.decodeFromString(ListSerializer(TransactionDetail.serializer()), arguments?.getString(
            KEY_TRANSACTION_LIST)?:"")

        val user: User =
            Json.decodeFromString(User.serializer(), arguments?.getString(KEY_USER)?:"")
        binding = CreditPageItemBinding.inflate(layoutInflater) //initializing the binding class

        binding.balanceValue.text = user.balance.toString() + " $"
        binding.incomeValue.text = calculateCurrentMonthTotalIncome(transactions).toString() + " $"
        binding.outcomeValue.text = calculateCurrentMonthTotalOutcome(transactions).toString() + " $"

        // Pre-process data
        val currentDate = LocalDate.now()

        val firstDayOfMonth = currentDate.withDayOfMonth(1)
        val dateLabels = mutableListOf<String>()
        var currentDay = firstDayOfMonth
        while (currentDay.isBefore(currentDate.plusDays(1))) {
            dateLabels.add(currentDay.toString())
            currentDay = currentDay.plusDays(1)
        }
        val currentMonthBalanceData = calculateDailyBalances(transactionDetailList, user.balance)

        // Set up balance chart
        val balanceChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .title("")
            .backgroundColor("transparent")
            .yAxisTitle("Amount")
            .yAxisGridLineWidth(0f) // Remove the grid lines
            .titleStyle(AAStyle().color("FFFFFF"))
            .axesTextColor("white")
            .categories(dateLabels.toTypedArray()) // Set date labels as x-axis categories
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Balance")
                        .type(AAChartType.Line)
                        .data(currentMonthBalanceData.toTypedArray())
                )
            )

        val currentMonthNetData = calculateNetIncome(transactionDetailList)

        // Set up net-income chart
        val netChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .title("")
            .backgroundColor("transparent")
            .yAxisTitle("")
            .yAxisGridLineWidth(0f) // Remove the grid lines
            .titleStyle(AAStyle().color("FFFFFF"))
            .axesTextColor("white")
            .markerSymbol(AAChartSymbolType.Diamond)
            .markerSymbolStyle(AAChartSymbolStyleType.BorderBlank)
            .markerRadius(3)
            .categories(dateLabels.toTypedArray()) // Set date labels as x-axis categories
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Net value")
                        .type(AAChartType.Line)
                        .data(currentMonthNetData.toTypedArray())
                )
            )

        // Set up in/outcome chart
        val currentMonthIncomeData = calculateDailyIncomes(transactionDetailList)
        val currentMonthOutcomeData = calculateDailyOutcomes(transactionDetailList)
        val incomeOutcomeModel = AAChartModel()
            .chartType(AAChartType.Column)
            .title("")
            .backgroundColor("transparent")
            .yAxisGridLineWidth(0f) // Remove the grid lines
            .axesTextColor("white")

            .categories(dateLabels.toTypedArray()) // Set date labels as categories on the x-axis
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Income")
                        .type(AAChartType.Column)
                        .size(20)
                        .data(currentMonthIncomeData.toTypedArray()),
                    AASeriesElement()
                        .name("Outcome")
                        .size(20)
                        .type(AAChartType.Column)
                        .data(currentMonthOutcomeData.toTypedArray())
                )
            )



        binding.balanceChart.aa_drawChartWithChartModel(balanceChartModel)
        binding.inOutChart.aa_drawChartWithChartModel(incomeOutcomeModel)
        binding.netChart.aa_drawChartWithChartModel(netChartModel)

        return binding.root
    }

    // Customize for the dialog
    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        return (screenHeight * 0.9).toInt()  // Return 80% of the screen height
    }

    override fun getCornerRadius() = 20f * requireContext().resources.displayMetrics.density

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return true;
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }


    // Method to get balance daily in current month
    fun calculateDailyBalances(
        transactions: List<TransactionDetail>,
        currentBalance: Double
    ): List<Double> {
        val currentDate = LocalDate.now()
        val firstDayOfMonth = currentDate.withDayOfMonth(1)
        val daysInMonth = currentDate.dayOfMonth

        val dailyBalances = MutableList(daysInMonth) { 0.0 } // Initialize with zeros

        for (transaction in transactions) {
            val transactionDate = Instant.ofEpochSecond(transaction.time)
                .atZone(ZoneOffset.UTC)
                .toLocalDate()

            val dayOfMonth = transactionDate.dayOfMonth

            if (transactionDate.isAfter(firstDayOfMonth) && dayOfMonth <= daysInMonth) {
                // Adjust for zero-based index
                val index = dayOfMonth - 1
                dailyBalances[index] += transaction.amount
            }
        }

        // Calculate cumulative balances
        val cumulativeBalances = mutableListOf<Double>()
        var runningBalance = currentBalance
        for (i in 0 until daysInMonth) {
            runningBalance += dailyBalances[i]
            cumulativeBalances.add(runningBalance)
        }

        return cumulativeBalances.reversed()
    }

    // Method to get daily netincome this month
    fun calculateNetIncome(
        transactions: List<TransactionDetail>,
    ): List<Double> {
        val currentDate = LocalDate.now()
        val firstDayOfMonth = currentDate.withDayOfMonth(1)
        val daysInMonth = currentDate.dayOfMonth

        val dailyNet = MutableList(daysInMonth) { 0.0 } // Initialize with zeros

        for (transaction in transactions) {
            val transactionDate = Instant.ofEpochSecond(transaction.time)
                .atZone(ZoneOffset.UTC)
                .toLocalDate()

            val dayOfMonth = transactionDate.dayOfMonth

            if (transactionDate.isAfter(firstDayOfMonth) && dayOfMonth <= daysInMonth) {
                // Adjust for zero-based index
                val index = dayOfMonth - 1
                dailyNet[index] += transaction.amount
            }
        }

        return dailyNet.reversed()
    }

    // Method to get daily income this month
    fun calculateDailyIncomes(
        transactions: List<TransactionDetail>
    ): List<Double> {
        val currentDate = LocalDate.now()
        val firstDayOfMonth = currentDate.withDayOfMonth(1)
        val daysInMonth = currentDate.dayOfMonth

        val dailyIncomes = MutableList(daysInMonth) { 0.0 } // Initialize with zeros

        for (transaction in transactions) {
            val transactionDate = Instant.ofEpochSecond(transaction.time)
                .atZone(ZoneOffset.UTC)
                .toLocalDate()

            val dayOfMonth = transactionDate.dayOfMonth

            if (transactionDate.isAfter(firstDayOfMonth) && dayOfMonth <= daysInMonth && transaction.amount > 0) {
                // Adjust for zero-based index
                val index = dayOfMonth - 1
                dailyIncomes[index] += transaction.amount
            }
        }

        return dailyIncomes.reversed()
    }

    // Method to get daily outcome this month
    fun calculateDailyOutcomes(
        transactions: List<TransactionDetail>
    ): List<Double> {
        val currentDate = LocalDate.now()
        val firstDayOfMonth = currentDate.withDayOfMonth(1)
        val daysInMonth = currentDate.dayOfMonth

        val dailyOutcomes = MutableList(daysInMonth) { 0.0 } // Initialize with zeros

        for (transaction in transactions) {
            val transactionDate = Instant.ofEpochSecond(transaction.time)
                .atZone(ZoneOffset.UTC)
                .toLocalDate()

            val dayOfMonth = transactionDate.dayOfMonth

            if (transactionDate.isAfter(firstDayOfMonth) && dayOfMonth <= daysInMonth && transaction.amount < 0) {
                // Adjust for zero-based index
                val index = dayOfMonth - 1
                dailyOutcomes[index] += transaction.amount
            }
        }

        return dailyOutcomes.reversed()
    }

    // Function to filter transactions for the current month
    fun filterTransactionsForCurrentMonth(transactions: List<TransactionWeek>): List<TransactionWeek> {
        val currentMonth = LocalDate.now().monthValue
        val currentYear = LocalDate.now().year
        return transactions.filter { it.month == currentMonth && it.year == currentYear }
    }

    // Method to get total income
    fun calculateCurrentMonthTotalIncome(transactions: List<TransactionWeek>): Double {
        val currentMonth = LocalDateTime.now().monthValue
        val currentYear = LocalDateTime.now().year

        val totalIncome = transactions.filter { it.month == currentMonth && it.year == currentYear }
            .sumByDouble { it.totalIncome }

        return round(totalIncome * 100) / 100.0
    }
    // Method to get total outcome

    fun calculateCurrentMonthTotalOutcome(transactions: List<TransactionWeek>): Double {
        val currentMonth = LocalDateTime.now().monthValue
        val currentYear = LocalDateTime.now().year

        val totalOutcome = transactions.filter { it.month == currentMonth && it.year == currentYear }
            .sumByDouble { it.totalOutcome }

        return round(totalOutcome * 100) / 100.0
    }

}

