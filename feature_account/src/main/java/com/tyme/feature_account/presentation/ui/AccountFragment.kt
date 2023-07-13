package com.tyme.feature_account.presentation.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.isPopupLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.faskn.lib.Slice
import com.faskn.lib.buildChart
import com.tyme.base.Common.FragmentEnum
import com.tyme.base.ext.FragmentListener
import com.tyme.base_feature.common.Constant
import com.tyme.feature_account.presentation.viewmodel.AccountViewModel
import com.tyme.feature_history.R
import com.tyme.feature_history.databinding.AccountFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.tyme.base_feature.common.Result
import com.tyme.feature_account.domain.model.TransactionDetail
import com.tyme.feature_account.domain.model.TransactionWeek
import com.tyme.feature_account.presentation.adapter.TransactionListAdapter
import com.tyme.feature_account.presentation.viewmodel.TransactionState
import org.koin.android.ext.android.inject
import kotlin.random.Random

class AccountFragment: Fragment(R.layout.account_fragment) {
    private lateinit var binding: AccountFragmentBinding
    private var fragmentListener: FragmentListener? = null
    private val viewModel: AccountViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AccountFragmentBinding.inflate(layoutInflater) //initializing the binding class
        viewModel.initialize()
        return binding.root
    }

    // Call from the fragment to the main activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the activity implements the callback interface
        if (context is FragmentListener) {
            fragmentListener = context
        } else {
            throw IllegalArgumentException("Activity implements listener wrongly")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signOutBtn.setOnClickListener{
            fragmentListener?.onNavigate(FragmentEnum.Dashboard)
        }
        viewModel.transactionPage.observe(viewLifecycleOwner) {
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

        viewModel.transactionWeeks.observe(viewLifecycleOwner) {
                response ->

            when (response) {
                is Result.Success -> {
                    initPieChart(response.data?:ArrayList<TransactionWeek>())
                }
                is Result.Loading -> {

                } else -> {
            }
            }
        }

        viewModel.state.observe(viewLifecycleOwner) {
            Log.d("tai", "change")
            viewModel.getTransactionPage(Constant.TEST_USER_ID)
        }
    }

    private fun initTransactionAdapter(items: List<TransactionDetail>) {
        Handler().postDelayed({
            binding.transactionList.apply {
                visibility = View.VISIBLE
                adapter = TransactionListAdapter(items)
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false

            }
            binding.listShimmer.stopShimmer()
            binding.listShimmer.visibility = View.GONE
        }, 2000)

    }

    private fun initPieChart(items: List<TransactionWeek>) {
        val transactionSummary = viewModel.getSummary(items)
        val dataEntries: ArrayList<Slice> = ArrayList()
        for (item in 0 until transactionSummary.categoryDistribution.size) {
            dataEntries.add(Slice(transactionSummary.categoryDistribution[item]*-1, transactionSummary.color[item] ,transactionSummary.categoryTitleList[item].toString()))
        }
        val pieChartDSL = buildChart {
            slices { dataEntries }
            sliceWidth { 150f }
            sliceStartPoint { 0f }
            clickListener { angle, index ->
                val num = index.toInt()
                viewModel.setCategory(transactionSummary.categoryTitleList[num].toString())
            }
        }

        binding.pieChart.apply {
            setPieChart(pieChartDSL)
            showLegend(binding.legendLayout)
        }

    }
}



