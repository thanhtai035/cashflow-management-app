package com.tyme.feature_dashboard.presentation.util

import android.view.View
import androidx.viewpager2.widget.ViewPager2


class SlideUpPanelTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        if (position < -1){    // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0f);

        }
        else if (position <= 0){    // [-1,0]
            page.setAlpha(1f);
            page.setTranslationX(0f);
            page.setScaleX(1f);
            page.setScaleY(1f);
        }
        else if (position <= 1){    // (0,1]
            page.setTranslationX(-position*page.getWidth());
            page.setAlpha(1-Math.abs(position));
            page.setScaleX(1-Math.abs(position));
            page.setScaleY(1-Math.abs(position));
        }
        else {    // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0f);
        }
    }
}
