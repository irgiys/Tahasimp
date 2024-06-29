package com.irgiys.tahasimp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.irgiys.tahasimp.R
import com.irgiys.tahasimp.databinding.FragmentHomeBinding
import com.irgiys.tahasimp.ui.activity.AddSavingActivity
import com.irgiys.tahasimp.ui.adapter.SectionsPagerAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setupSearch()
        binding.fabAdd.setOnClickListener {
            val intent = Intent(activity, AddSavingActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setupSearch() {
        with(binding) {
            searchBar.setOnClickListener {
                fabAdd.visibility = View.GONE
            }
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.toString()
                searchBar.setText(query)
                searchView.hide()
                updateSearchQuery(query)
                false
            }
            searchView.editText.setOnFocusChangeListener { _, hasFocus ->
                fabAdd.visibility = if (hasFocus) View.GONE else View.VISIBLE
            }
        }
    }
    private fun updateSearchQuery(query: String) {
        val progressFragment = sectionsPagerAdapter.getFragment(0) as? ProgressFragment
        progressFragment?.updateSearchQuery(query)
    }

    private fun setViewPager() {
        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs

        sectionsPagerAdapter = SectionsPagerAdapter(this)
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.on_progress, R.string.completed
        )
    }
}