package com.tyme.feature_history.presentation.TransactionHistory
//package com.tyme.transaction_feature.presentation.TransactionDetail
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.tyme.cashflow_management_app.databinding.FragmentHistoryBinding
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//
//class HistoryFragment : Fragment() {
//    private var binding: FragmentHistoryBinding? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val fragmentBinding = FragmentHistoryBinding.inflate(inflater, container, false)
//        binding = fragmentBinding
//
//        return fragmentBinding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding?.apply {
//            // Specify the fragment as the lifecycle owner
//            lifecycleOwner = viewLifecycleOwner
//
//            // Assign the view model to a property in the binding class
// //           transactionDetailViewModel = transactionDetailViewModel
//
//        }
//    }
//
//
//
//}