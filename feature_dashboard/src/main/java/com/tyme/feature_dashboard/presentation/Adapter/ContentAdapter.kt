package com.tyme.feature_dashboard.presentation.Adapter

//Import Chart
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar
import com.straiberry.android.charts.tooltip.PointTooltip
import com.tyme.feature_dashboard.R
import com.tyme.feature_dashboard.databinding.CreditPageItemBinding
import com.tyme.feature_dashboard.databinding.LoaningPageItemBinding
import com.tyme.feature_dashboard.databinding.SavingPageItemBinding
import com.tyme.feature_dashboard.domain.model.TransactionWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.temporal.WeekFields
import java.util.*
import kotlin.collections.ArrayList

class ContentAdapter(var items: ArrayList<Int>, var transactionList: List<TransactionWeek>) :
    RecyclerView.Adapter<ContentAdapter.Viewholder>()  {
    var context: Context? = null
    var balance = 5000.0 //GET VALUE REFERENCE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val inflater = LayoutInflater.from(parent.context)

        var binding : ViewBinding = CreditPageItemBinding.inflate(inflater, parent, false)
        when (items[viewType]) {
            // Credit Card page
            0 -> {
                binding = CreditPageItemBinding.inflate(inflater, parent, false)


            }

            // Saving page item
            1 -> {
                binding = SavingPageItemBinding.inflate(inflater, parent, false)
            }
            // Loadning Page item
            2 -> {
                binding = LoaningPageItemBinding.inflate(inflater, parent, false)
            }
            else ->                 CreditPageItemBinding.inflate(inflater, parent, false)

        }
        context = parent.context
        return Viewholder(binding.root)
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
}

data class WeeklyBalance(val title: String, val balance: Double)

fun weeklyBalances(
    transactions: List<TransactionWeek>,
    currentBalance: Double,
    startMonth: Int
): List<WeeklyBalance> {
    val startWeek = 1
    val currentYear = LocalDateTime.now().year

    // Filter relevant transactions based on the start week and month
    val relevantTransactions = transactions.filter {
        (it.year == currentYear && it.month >= startMonth && it.week >= startWeek) ||
                (it.year == currentYear && it.month > startMonth)
    }

    val balances = mutableListOf<WeeklyBalance>()
    var balance = currentBalance

    for (transaction in relevantTransactions) {
        balance += transaction.totalIncome
        balance -= transaction.totalOutcome
        val monthName = Month.of(transaction.month).name.toLowerCase().capitalize()
        val title = "week ${transaction.week} $monthName"
        balances.add(WeeklyBalance(title, balance))
    }

    return balances
}



