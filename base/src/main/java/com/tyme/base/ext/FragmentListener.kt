package com.tyme.base.ext

import com.tyme.base.Common.FragmentEnum

interface FragmentListener {
    fun onNavigate(content: FragmentEnum)

    fun openDrawer()
}

