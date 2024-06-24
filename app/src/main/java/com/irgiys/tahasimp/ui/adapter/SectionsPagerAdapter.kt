package com.irgiys.tahasimp.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.irgiys.tahasimp.ui.fragment.CompletedFragment
import com.irgiys.tahasimp.ui.fragment.ProgressFragment

class SectionsPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ProgressFragment()
            }
            else -> {
                CompletedFragment()
            }
        }
    }

//    companion object {
//        const val ARGS_USERNAME = "username"
//    }
}