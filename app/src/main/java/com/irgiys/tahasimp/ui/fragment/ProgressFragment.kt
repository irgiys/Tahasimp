package com.irgiys.tahasimp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.irgiys.tahasimp.databinding.FragmentProgressBinding
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
        // Inflate the layout for this fragment
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savingViewModel = obtainViewModel(this)
        adapter = SavingListAdapter()
        binding.rvSavings.adapter = adapter
        binding.rvSavings.layoutManager = LinearLayoutManager(requireContext())
//        binding.rvSavings.layoutManager = LinearLayoutManager(requireContext())
//        binding.rvSavings.setHasFixedSize(true)
//        binding.rvSavings.adapter = adapter
//        savingViewModel.allSavings.observe(viewLifecycleOwner, Observer { savings ->
//            if (savings != null) {
//                adapter.setListSaving(savings)
//            }
//        })
        savingViewModel.allSavings.observe(viewLifecycleOwner, Observer { savings ->
            adapter.submitList(savings)
        })

    }

    private fun obtainViewModel(fragment: Fragment): SavingViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory).get(SavingViewModel::class.java)
    }

}