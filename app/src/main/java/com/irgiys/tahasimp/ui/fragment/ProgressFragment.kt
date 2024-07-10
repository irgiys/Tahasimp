package com.irgiys.tahasimp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.irgiys.tahasimp.databinding.FragmentProgressBinding
import com.irgiys.tahasimp.ui.activity.AddSavingActivity
import com.irgiys.tahasimp.ui.adapter.SavingListAdapter
import com.irgiys.tahasimp.utils.ViewModelFactory
import com.irgiys.tahasimp.viewmodel.SavingViewModel


class ProgressFragment : Fragment() {
    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    private lateinit var savingViewModel: SavingViewModel
    private lateinit var adapter: SavingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savingViewModel = obtainViewModel(this)
        setupRecyclerView()
        observeSavings()
        binding.fabAdd.setOnClickListener {
            val intent = Intent(activity, AddSavingActivity::class.java)
            startActivity(intent)
        }
    }
    private fun observeSavings() {
        savingViewModel.allProgressSavings.observe(viewLifecycleOwner) { savings ->
            adapter.submitList(savings)
        }
    }

    private fun setupRecyclerView() {
        adapter = SavingListAdapter()
        binding.rvSavings.apply {
            this.adapter = this@ProgressFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    fun updateSearchQuery(query: String) {
        savingViewModel.searchProgressSavings(query).observe(viewLifecycleOwner) { filteredSavings ->
            adapter.submitList(filteredSavings)
        }
    }

    private fun obtainViewModel(fragment: Fragment): SavingViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory)[SavingViewModel::class.java]
    }

}