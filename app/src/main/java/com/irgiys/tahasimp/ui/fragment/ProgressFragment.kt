package com.irgiys.tahasimp.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.irgiys.tahasimp.R
import com.irgiys.tahasimp.databinding.FragmentProgressBinding
import com.irgiys.tahasimp.ui.activity.AddSavingActivity
import com.irgiys.tahasimp.ui.adapter.SavingListAdapter
import com.irgiys.tahasimp.utils.ViewModelFactory
import com.irgiys.tahasimp.utils.formatCurrency
import com.irgiys.tahasimp.viewmodel.SavingViewModel


class ProgressFragment : Fragment() {
    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    private lateinit var savingViewModel: SavingViewModel
    private lateinit var adapter: SavingListAdapter

    private var currentQuery: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savingViewModel = obtainViewModel(this)
        setupRecyclerView()
        observeSavings()
        binding.fabAdd.setOnClickListener {
            val intent = Intent(activity, AddSavingActivity::class.java)
            startActivity(intent)
        }

        binding.searchView.apply {
            isIconified = false
            queryHint = "Cari tabungan yang sedang berjalan..."

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
                    currentQuery = newText
                    adapter.filter(newText ?: "")
                    return true
                }
            })
        }

    }

    private fun observeSavings() {
        savingViewModel.allProgressSavings.observe(viewLifecycleOwner) { savings ->
            adapter.submitFullList(savings)
            currentQuery?.let {
                adapter.filter(it)
            }
            if (savings.isEmpty()) {
                binding.tvEmptySaving.visibility = View.VISIBLE
                binding.rvSavings.visibility = View.GONE
                binding.searchView.visibility = View.GONE
            } else {
                binding.tvEmptySaving.visibility = View.GONE
                binding.rvSavings.visibility = View.VISIBLE
                binding.searchView.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = SavingListAdapter()
        binding.rvSavings.apply {
            this.adapter = this@ProgressFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }



    private fun obtainViewModel(fragment: Fragment): SavingViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory)[SavingViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        currentQuery?.let {
            binding.searchView.setQuery(it, false)
        }
        binding.searchView.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}