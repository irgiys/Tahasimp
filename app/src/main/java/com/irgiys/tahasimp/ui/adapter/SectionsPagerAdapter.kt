package com.irgiys.tahasimp.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.irgiys.tahasimp.ui.fragment.CompletedFragment
import com.irgiys.tahasimp.ui.fragment.ProgressFragment

class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragmentsMap = mutableMapOf<Int, Fragment>()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProgressFragment().also { fragmentsMap[position] = it }
            else -> CompletedFragment().also { fragmentsMap[position] = it }
        }
    }

    fun getFragment(position: Int): Fragment? = fragmentsMap[position]
}
