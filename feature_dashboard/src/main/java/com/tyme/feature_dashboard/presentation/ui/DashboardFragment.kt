package com.tyme.feature_dashboard.presentation.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.ramijemli.percentagechartview.callback.AdaptiveColorProvider
import com.ramijemli.percentagechartview.callback.ProgressTextFormatter
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.tyme.base.Common.FragmentEnum
import com.tyme.base.ext.FragmentListener
import com.tyme.base_feature.common.Result
import com.tyme.feature_dashboard.R
import com.tyme.feature_dashboard.databinding.FragmentDashboardBinding
import com.tyme.feature_dashboard.domain.model.Advertisement
import com.tyme.feature_dashboard.presentation.Adapter.AdsAdapter
import com.tyme.feature_dashboard.presentation.Adapter.CardAdapter
import com.tyme.feature_dashboard.presentation.Adapter.ContentAdapter
import com.tyme.feature_dashboard.presentation.Adapter.StoryAdapter
import com.tyme.feature_dashboard.presentation.util.*
import com.tyme.feature_dashboard.presentation.viewmodel.DashboardViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by inject()

    private var fragmentListener: FragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater) //initializing the binding class
        viewModel.initialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initContentPager()
        initModal()
        binding.accountNavigate.setOnClickListener{
            fragmentListener?.onNavigate(FragmentEnum.Account)
        }

        viewModel._user.observe(viewLifecycleOwner) {
            response ->
            when (response) {
                is Result.Success -> {
                    initAdsView()
                    binding.userName.setText("Hello, " + response.data?.firstName + " " + response.data?.lastName)

                    // Use handler for testing the effect
                    Handler().postDelayed({
                        initCardPager(response.data?.getCards()?:ArrayList<Card>())
                        binding.cardShimmer.stopShimmer()
                        binding.cardShimmer.visibility = View.GONE
                    }, 10000)

                    Handler().postDelayed({
                        initPercentageView()
                        binding.progressShimmer.stopShimmer()
                        binding.progressShimmer.visibility = View.GONE
                    }, 2000)

                }
                is Result.Loading -> {
                    binding.dashboardFragment.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
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

    // Initialize Ad RecycleView and stories ViewPager2
    private fun initAdsView() {

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
        val fadeOut = AnimationUtils.loadAnimation(activity, R.anim.fade_out)
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
        binding.dashboardFragment.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

        val cardAdapter = CardAdapter(cards)
        val compositePageTransformer: CompositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(CardAnimation(cards.size))

        val onPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.contentPager.currentItem = position % cards.size
            }
        }
        binding.cardPager.apply {
            visibility = View.VISIBLE
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            setPageTransformer(compositePageTransformer)
            adapter = cardAdapter
            registerOnPageChangeCallback(onPageChangeListener)
        }

    }

    // Initialize the ViewPager in the Slide Up Panel
    private fun initContentPager() {
        val items2: ArrayList<Int> = ArrayList<Int>()
        items2.add(0)
        items2.add(1)
        items2.add(2)

        val contentAdapter = ContentAdapter(items2)

        binding.contentPager.apply {
            adapter = contentAdapter
            setPageTransformer(SlideUpPanelTransformer())
            isUserInputEnabled = false
        }
    }

    private fun initPercentageView() {
        binding.progressLayout.visibility = View.VISIBLE
        val colorProvider: AdaptiveColorProvider = object : AdaptiveColorProvider {
            val redColor = Color.RED
            val yellowColor = Color.YELLOW
            val greenColor = Color.parseColor("#008000")

            override fun provideProgressColor(progress: Float): Int {
                if (progress < 50 && progress > 0)
                    return redColor;
                else if (progress >= 50 && progress < 75)
                    return yellowColor;
                else if (progress >= 75)
                    return greenColor
                // Some buf with default value at 0 that mess up color animation
                else return Color.WHITE
            }

            override fun provideTextColor(progress: Float): Int {
                return provideProgressColor(progress)
            }

            override fun provideBackgroundBarColor(progress: Float): Int {
                return ColorUtils.blendARGB(provideProgressColor(progress), Color.WHITE, .7f)
            }
        }

        binding.percentageView.apply {
            visibility = View.VISIBLE
            setAdaptiveColorProvider(colorProvider)
            setProgress(85f, true)
            setTextFormatter(ProgressTextFormatter { progress ->
                var content: String = ""
                if (progress >= 75) {
                    content = "Good"
                } else if (progress < 75 && progress >= 50)
                    content = "Medium"
                else
                    content = "Bad"
                content
            })
        }

    }

    private fun initModal() {
        binding.modalBtn.setOnClickListener {
            val modalDialogFragment = ModalFragment()
            modalDialogFragment.show(childFragmentManager, "modalDialog")
        }
    }
}


