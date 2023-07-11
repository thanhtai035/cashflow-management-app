package com.tyme.base.ext

import androidx.fragment.app.Fragment
import com.tyme.base.Common.FragmentEnum

interface FragmentListener {
    fun onNavigate(content: FragmentEnum)
}

