package com.tyme.feature_account.presentation.ui

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import app.futured.donut.DonutSection
import com.tyme.base_feature.common.Result
import com.tyme.feature_account.R
import com.tyme.feature_account.databinding.ActivityBudgetGoalBinding
import com.tyme.feature_account.domain.model.TransactionWeek
import com.tyme.feature_account.presentation.adapter.BudgetAdapter
import com.tyme.feature_account.presentation.util.Item
import com.tyme.feature_account.presentation.viewmodel.BudgetViewModel
import com.tyme.feature_account.presentation.viewmodel.TransactionSummary
import org.koin.android.ext.android.inject

class BudgetGoalActivity : AppCompatActivity(), BudgetAdapter.OnClickListener, BudgetUpdateDialog.NumberDialogListener {
    private lateinit var binding: ActivityBudgetGoalBinding
    private var lastItem = 0
    private var itemList : ArrayList<Item> = ArrayList()
    private lateinit var amounts : List<Double>
    val categories = listOf("Food", "Investment", "Entertainment", "Others", "Travel", "Utilities")
    private val viewModel: BudgetViewModel by inject()
    private lateinit var transactionSummary: TransactionSummary

    private lateinit var budgetAdapter: BudgetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBudgetGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkAndInitializeValues(this)
        amounts = loadAmountsFromSharedPreferences(this)
        viewModel.initialize()
        binding.backBtn.setOnClickListener {
            finish()
        }


        viewModel.transactionWeeks.observe(this) {
                response ->
            when (response) {
                is Result.Success -> {
                    transactionSummary = viewModel.getSummary(response.data?:ArrayList<TransactionWeek>())
                    val spending = listOf(transactionSummary.totalFood, transactionSummary.totalInvestment, transactionSummary.totalEntertainment, transactionSummary.totalOthers, transactionSummary.totalTravel, transactionSummary.totalUtilities)
                    for (i in categories.indices) {
                        Log.d("tai", categories[i] + ": "+ amounts[i].toString())
                        val category = categories[i]
                        val amount = amounts[i]
                        val spending = spending[i]
                        itemList.add(Item(category, amount, spending))
                    }
                    budgetAdapter = BudgetAdapter(this, itemList, this)
                    val budgetLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

                    binding.maxBudget.text = "of " + amounts.sum().toString()
                    binding.currentBudget.text= "$"+transactionSummary.totalOutcomes.toString()
                    if(amounts.sum() > transactionSummary.totalOutcomes) {
                        binding.warningMain.visibility = View.INVISIBLE
                    }
                    binding.spendTotalText.text = (amounts.sum() - transactionSummary.totalOutcomes).toString() + "$"
                    binding.budgetList.apply {
                        adapter = budgetAdapter
                        layoutManager = budgetLayoutManager
                    }

                    val section1 = DonutSection(
                        name = "section_1",
                        color = Color.parseColor("#fad43c"),
                        amount =  (transactionSummary.totalOutcomes / amounts.sum() * 100).toFloat()
                    )

                    binding.donutProgressView.apply {
                        cap = 100f
                        submitData(listOf(section1))
                        animate()
                    }
                }
                is Result.Loading -> {

                } else -> {
            }
            }
        }
    }

    fun showMyDialog(arg1: Double, arg2: Double, arg3: String) {
        val dialogFragment = BudgetUpdateDialog()

        val args = Bundle()
        args.putDouble("arg1", arg1)
        args.putDouble("arg2", arg2)
        args.putString("arg3", arg3)

        dialogFragment.arguments = args

        dialogFragment.show(supportFragmentManager, "MyDialogFragment")
    }

    // Category item click listener
    override fun onItemClick(item: Item, position: Int) {
        lastItem = position
        showMyDialog(item.spendAmount, item.categoryAmount, item.categoryName)
    }

    // Add new value for category
    override fun onNumberSelected(number: Double) {
        itemList.get(lastItem).categoryAmount = number

        // Re-render th eUI
        binding.maxBudget.text = "of " + calculateTotalAmount(itemList).toString()
        val section1 = DonutSection(
            name = "section_1",
            color = Color.parseColor("#fad43c"),
            amount = 80f
        )

        updateCategoryAmount(itemList.get(lastItem).categoryName, number)
        if(amounts.sum() > transactionSummary.totalOutcomes) {
            binding.warningMain.visibility = View.INVISIBLE
        }
        binding.spendTotalText.text = (amounts.sum() - transactionSummary.totalOutcomes).toString() + "$"
        binding.donutProgressView.apply {
            cap = 100f
            clear()
            submitData(listOf(section1))
            animate()
        }
        budgetAdapter.notifyDataSetChanged()
    }

    // Method to calculate the amount of all budget
    fun calculateTotalAmount(itemList: ArrayList<Item>): Double {
        var totalSpendAmount = 0.0
        for (item in itemList) {
            totalSpendAmount += item.categoryAmount
        }
        return totalSpendAmount
    }

    // Get data from Local Storage
    private fun loadAmountsFromSharedPreferences(context: Context): List<Double> {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        // Initialize the amounts list with values from SharedPreferences
        val loadedAmounts = mutableListOf<Double>()
        for (category in categories) {
            val amount = sharedPreferences.getFloat(category, 0.0f).toDouble()
            loadedAmounts.add(amount)
        }

        return loadedAmounts
    }

    // Update category amount in Local Storage
    private fun updateCategoryAmount(category: String, newValue: Double) {
        val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Update the category with the new value
        editor.putFloat(category, newValue.toFloat())
        editor.apply()
    }

    // Create local storage data if not exists

    private fun checkAndInitializeValues(context: Context) {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // List of keys to check and initialize if they don't exist
        val keysToInitialize = listOf(
            "Travel",
            "Food",
            "Entertainment",
            "Utilities",
            "Others",
            "Investment",
            "Income"
        )

        for (key in keysToInitialize) {
            if (!sharedPreferences.contains(key)) {
                // Key doesn't exist, initialize it to 0
                editor.putFloat(key, 0f)
            }
        }

        // Apply the changes
        editor.apply()
    }
}



