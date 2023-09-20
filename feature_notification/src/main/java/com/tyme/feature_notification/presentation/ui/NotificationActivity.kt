package com.tyme.feature_notification.presentation.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.tyme.base.Common.Constant
import com.tyme.base_feature.common.Result
import com.tyme.feature_notification.R
import com.tyme.feature_notification.databinding.ActivityNotificationBinding
import com.tyme.feature_notification.domain.model.UserNotification
import com.tyme.feature_notification.presentation.adapter.NotificationAdapter
import com.tyme.feature_notification.presentation.viewmodel.NotificationViewModel
import org.koin.android.ext.android.inject

class NotificationActivity : AppCompatActivity(), ObservableScrollViewCallbacks {
    private lateinit var binding: ActivityNotificationBinding
    private val viewModel: NotificationViewModel by inject()
    private var notificationList = ArrayList<UserNotification>()
    private lateinit var notiAdapter: NotificationAdapter

    private var currentPage = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        notiAdapter = NotificationAdapter(this, notificationList)
        binding.expandableListView.apply {
            adapter = notiAdapter

        }
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.swipeLayout.setOnRefreshListener {
            viewModel.updateState()
        }

        binding.expandableListView.setScrollViewCallbacks(this)
    }



    override fun onStart() {
        super.onStart()


        viewModel.initialize()
        viewModel.notificationPage.observe(this) {
                response ->
            when (response) {
                is Result.Success -> {

                    val buf = response.data

                    if (buf != null) {
                        for (noti in buf) {
                            notificationList.add(noti)
                        }
                    }
                    notiAdapter.notifyDataSetChanged()
                }
                is Result.Loading -> {

                } else -> {

            }
            }
        }
    }

    override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
    }

    override fun onDownMotionEvent() {
        binding.topbar.visibility = View.GONE

    }

    override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
        binding.topbar.visibility = View.VISIBLE

    }
}