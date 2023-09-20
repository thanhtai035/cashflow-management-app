package com.tyme.feature_dashboard.presentation.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.tyme.base.Common.Constant
import com.tyme.base.Common.FragmentEnum
import com.tyme.base.ext.FragmentListener
import com.tyme.base_feature.common.Result
import com.tyme.feature_chatbot.presentation.ui.ChatBotFragment
import com.tyme.feature_dashboard.R
import com.tyme.feature_dashboard.databinding.DashboardMigrationBinding
import com.tyme.feature_dashboard.domain.model.Advertisement
import com.tyme.feature_dashboard.domain.model.TransactionDetail
import com.tyme.feature_dashboard.domain.model.TransactionWeek
import com.tyme.feature_dashboard.domain.model.User
import com.tyme.feature_dashboard.presentation.Adapter.*
import com.tyme.feature_dashboard.presentation.util.*
import com.tyme.feature_dashboard.presentation.viewmodel.DashboardViewModel
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.koin.android.ext.android.inject
import java.lang.Math.sqrt


class DashboardFragment : Fragment(R.layout.fragment_dashboard), CardAdapter.OnItemClickListener {
    private lateinit var binding: DashboardMigrationBinding
    private val viewModel: DashboardViewModel by inject()
    private var fragmentListener: FragmentListener? = null // Call to Navigation Activity to change screen
    private var spendingScore = 0
    private var complianceScore = 0
    private var investmentScore = 0
    private var savingScore = 0
    private var count = 5
    private var message = ""
    private lateinit var user: User
    private lateinit var list: List<TransactionWeek>
    private lateinit var transactionList: List<TransactionDetail>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DashboardMigrationBinding.inflate(layoutInflater) //initializing the binding class
        viewModel.initialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSetUp()
        initChatBot()

        // Get Transaction Data
        viewModel._transaction.observe(viewLifecycleOwner) {
            response ->
            when(response) {
                is Result.Success -> {
                    // Set up data for graph
                    initContentPager(response.data?:ArrayList<TransactionWeek>())
                    list = response.data?: ArrayList<TransactionWeek>()
                }
                is Result.Loading -> {

                } else -> {

                }
            }
        }

        viewModel.transactionPage.observe(viewLifecycleOwner) {
                response ->
            when(response) {
                is Result.Success -> {
                    // Set up data for graph
                    transactionList = response.data?: ArrayList<TransactionDetail>()
                }
                is Result.Loading -> {

                } else -> {

            }
            }
        }

        // Get Analysis Data
        viewModel._analysis.observe(viewLifecycleOwner) {
                response ->
            when(response) {
                is Result.Success -> {
                    binding.progressShimmer.apply {
                        stopShimmer()
                        hideShimmer()
                    }
                    // Store analysis data
                    spendingScore = response.data?.spending ?: 0
                    complianceScore = response.data?.compliance ?: 0
                    investmentScore = response.data?.investment ?: 0
                    savingScore = response.data?.saving ?: 0
                    message = response.data?.string ?: ""
                    binding.analysisText.visibility = View.VISIBLE
                    val score = ((spendingScore + complianceScore + investmentScore + savingScore) / 4) * 10
                    binding.totalScore.text = score.toString()
                    binding.modalLayout.visibility = View.VISIBLE
                    if (score <= 30) {
                        binding.analysisTitle.text = "Urgent! "
                    } else if (score <= 50) {
                        binding.analysisTitle.text = "Warning!"
                    } else if (score <= 75) {
                        binding.analysisTitle.text = "Good job!"
                    } else {
                        binding.analysisTitle.text = "Well done!"
                    }
                    binding.percentageView.apply {
                        visibility = View.VISIBLE
                        setProgress(score.toFloat(), true)
                    }                    // Enable Analysis model
                    initModal()
                }
                is Result.Loading -> {

                } else -> {
                    viewModel.getAnalysis(Constant.TEST_USER_ID)
                }
            }
        }

        //Get user data
        viewModel._user.observe(viewLifecycleOwner) {
            response ->
            when (response) {
                is Result.Success -> {
                    initAdsView()
                    user = response.data!!
                    binding.userName.text = (response.data?.firstName ?: "") + " " + (response.data?.lastName ?: "")
                    // Use handler for testing the effect
                    Handler().postDelayed({
                        initCardPager(response.data?.getCards()?:ArrayList<Card>())
                        binding.cardShimmer.stopShimmer()
                        binding.cardShimmer.visibility = View.GONE
                    }, 2000)

                    Handler().postDelayed({
//                        binding.progressShimmer.stopShimmer()
//                        binding.progressShimmer.visibility = View.GONE
                    }, 2000)

                }
                is Result.Loading -> {
                }
                else -> {
                    // Handle error
                }
            }
        }
    }

    // Call from the fragment to the main activity to full screen
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the activity implements the callback interface
        if (context is FragmentListener) {
            fragmentListener = context
        } else {
            throw IllegalArgumentException("Activity implements listener wrongly")
        }
    }

    // Set up navigation button and shimmer
    private fun initSetUp() {
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val paramCard = binding.shimmerCard1.layoutParams
        paramCard.height = (screenHeight * 0.25).toInt()
        paramCard.width = (paramCard.height * 1.5 ).toInt()

        binding.shimmerCard1.layoutParams = paramCard
        binding.shimmerCard2.layoutParams = paramCard

        val adLayout = binding.adShimmer.layoutParams
        adLayout.height = (screenHeight * 0.09).toInt()

        binding.transactionNavigate.setOnClickListener{
            fragmentListener?.onNavigate(FragmentEnum.Transaction)
        }

        binding.budgetNavigate.setOnClickListener {
            fragmentListener?.onNavigate(FragmentEnum.Budget)
        }

        binding.notiNavigate.setOnClickListener {
            fragmentListener?.onNavigate(FragmentEnum.Notification)
        }
        binding.dashboardFragment.apply {
            setFadeOnClickListener { setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED) }
            isTouchEnabled = false
        }

        binding.signOutBtn.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Sign Out")
            builder.setMessage("Are you sure you want to sign out?")

            builder.setPositiveButton("Sign Out") { dialog, which ->
                fragmentListener?.onNavigate(FragmentEnum.SignOut)
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                // Dismiss the dialog if the user cancels
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()        }
    }

    // Initialize Ad RecycleView and stories ViewPager2
    private fun initAdsView() {
        // Put up mock data
        val items: ArrayList<Advertisement> = ArrayList<Advertisement>()
        items.add(
            Advertisement(
                "Earn up to 10% a year with GoalSave ",
                "20 June, 2023",
                "ad1"
            )
        )
        items.add(
            Advertisement(
                "Find kiosks near your place ?",
                "19 June, 2023",
                "ad2"
            )
        )
        items.add(
            Advertisement(
                "Earn Double Smart Shop",
                "17 June, 2023",
                "ad3"
            )
        )

        val fadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in) // Fade i animation
        val adAdapter = AdsAdapter(items)
        val storyAdapter = StoryAdapter(items)

        // Set adapter for Ad Recycle View
        Handler().postDelayed({
            binding.adList.apply {
                adapter = adAdapter
                visibility = View.VISIBLE
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            }
            binding.adShimmer.stopShimmer()
            binding.adShimmer.visibility = View.GONE
        }, 5000)

        // Click Ad item to open Story full-screen view
        adAdapter.setOnItemClickListener { position ->
            binding.storyPager.visibility = View.VISIBLE
            binding.contentLayout.visibility = View.GONE
            binding.contentPager.visibility = View.GONE

            binding.storyPager.postDelayed({
                binding.storyPager.currentItem = position
                binding.storyPager.startAnimation(fadeIn)
            }, 20)
        }

        // Set adapter for story ViewPager
        binding.storyPager.apply {
            adapter = storyAdapter
            setPageTransformer(CubeInScalingAnimation())
        }

        // Back button - from full-screen story view to normal
        storyAdapter.buttonClickCallback {
            binding.storyPager.visibility = View.INVISIBLE
            binding.contentLayout.visibility = View.VISIBLE
            binding.contentPager.visibility = View.VISIBLE
            binding.dashboardFragment.startAnimation(fadeIn)
        }
    }

    // Initialize Card ViewPager
    private fun initCardPager(cards: ArrayList<Card>) {

        val cardAdapter = CardAdapter(cards, this)
        val customlayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val metrics = resources.displayMetrics
        val referenceX = metrics.widthPixels / 2  // middle of the screen
        binding.cardPager.apply {
            visibility = View.VISIBLE
            adapter = cardAdapter
            layoutManager = customlayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if(firstVisibleItemPosition == layoutManager.findLastVisibleItemPosition()) {
                        binding.pageIndicatorView.selection = firstVisibleItemPosition
                        return
                    }
                    binding.pageIndicatorView.selection = firstVisibleItemPosition
                }
            })
        }
    }

    // Initialize the ViewPager in the Slide Up Panel
    private fun initContentPager(list: List<TransactionWeek>) {
        val items2: ArrayList<Int> = ArrayList<Int>()
        items2.add(0)
        items2.add(1)
        items2.add(2)

        val contentAdapter = ContentAdapter(items2, list)

        binding.contentPager.apply {
            adapter = contentAdapter
            setPageTransformer(SlideUpPanelTransformer())
            isUserInputEnabled = false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initChatBot() {
        binding.chatbot.apply {
            var dX = translationX
            var dY = translationY
            var isDragged = false

            var previousX = 0f
            var previousY = 0f

            setOnTouchListener { view, motionEvent ->
                when (motionEvent.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        // Remove constraints
                        val params = layoutParams as ConstraintLayout.LayoutParams
                        params.topToTop = ConstraintLayout.LayoutParams.UNSET
                        params.startToStart = ConstraintLayout.LayoutParams.UNSET
                        params.endToEnd = ConstraintLayout.LayoutParams.UNSET
                        params.bottomToBottom = ConstraintLayout.LayoutParams.UNSET
                        params.setMargins(0,0,0,0)
                        layoutParams = params
                        previousX = motionEvent.rawX + dX
                        previousY = motionEvent.rawY + dY

                        dX = view.x - motionEvent.rawX
                        dY = view.y - motionEvent.rawY

                        alpha = 1.0f  // Change alpha to 1 while dragging
                        isDragged = false
                        true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        animate()
                            .x(motionEvent.rawX + dX)
                            .y(motionEvent.rawY + dY)
                            .setDuration(0)
                            .start()

                        isDragged = true
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        val currentX = motionEvent.rawX
                        val currentY = motionEvent.rawY
                        val deltaX = currentX - previousX
                        val deltaY = currentY - previousY
                        val distance = Math.sqrt((deltaX * deltaX + deltaY * deltaY).toDouble())
                        if (distance < 220) {
                            val sheet = ChatBotFragment()
                            sheet.show(childFragmentManager, "DemoBottomSheetFragment")
                        }

                        alpha = 0.6f  // Reset alpha to 0.6 after dropping
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initModal() {
        binding.modalBtn.setOnClickListener {
            val modalDialogFragment = ModalFragment.newInstance(spendingScore, savingScore, investmentScore, complianceScore, message)
            modalDialogFragment.show(childFragmentManager, "modalDialog")
        }
    }

    override fun onItemClick(position: Int) {
        if (position == 0 && !transactionList.isEmpty()) {
            val sheet = CreditContentFragment.putData(Json.encodeToString(User.serializer(), user), Json.encodeToString(
                ListSerializer(TransactionWeek.serializer()), list), Json.encodeToString(
                ListSerializer(TransactionDetail.serializer()), transactionList))
            sheet.show(childFragmentManager, "CreditFragment")
        }
    }
}

