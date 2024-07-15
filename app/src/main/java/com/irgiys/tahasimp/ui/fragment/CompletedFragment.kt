package com.irgiys.tahasimp.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.irgiys.tahasimp.R
import com.irgiys.tahasimp.databinding.FragmentCompletedBinding
import com.irgiys.tahasimp.databinding.FragmentProgressBinding
import com.irgiys.tahasimp.ui.activity.AddSavingActivity
import com.irgiys.tahasimp.ui.adapter.SavingListAdapter
import com.irgiys.tahasimp.utils.ViewModelFactory
import com.irgiys.tahasimp.viewmodel.SavingViewModel


class CompletedFragment : Fragment() {
    private var _binding: FragmentCompletedBinding? = null
    private val binding get() = _binding!!

    private lateinit var savingViewModel: SavingViewModel
    private lateinit var adapter: SavingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompletedBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savingViewModel = obtainViewModel(this)
        setupRecyclerView()
        observeSavings()

        binding.searchView.apply {
            isIconified = false
            queryHint = "Cari tabungan yang sudah selesai..."

            val searchPlate = findViewById<View>(androidx.appcompat.R.id.search_plate)
            searchPlate?.setBackgroundColor(Color.TRANSPARENT)

            try {
                val searchAutoComplete = findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)
                searchAutoComplete.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter(newText ?: "")
                    return true
                }
            })
        }
    }

    private fun observeSavings() {
        savingViewModel.allCompletedSavings.observe(viewLifecycleOwner) { savings ->
            adapter.submitFullList(savings)
        }
    }

    private fun setupRecyclerView() {
        adapter = SavingListAdapter()
        binding.rvSavings.apply {
            this.adapter = this@CompletedFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun obtainViewModel(fragment: Fragment): SavingViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory)[SavingViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.setQuery("", false)
        binding.searchView.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}