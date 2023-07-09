package com.tyme.feature_dashboard.presentation.util

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class CubeInScalingAnimation : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.setCameraDistance(10000f);

        if (position < -1){
            page.setAlpha(0f);
        }
        else if (position <= 0){
            page.setAlpha(1f);
            page.setPivotX(page.getWidth().toFloat());
            page.setRotationY(90*Math.abs(position));
        }
        else if (position <= 1){
            page.setAlpha(1f);
            page.setPivotX(0f);
            page.setRotationY(-90*Math.abs(position));
        }
        else{
            page.setAlpha(0f);
        }
    }
}