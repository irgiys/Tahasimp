package com.irgiys.tahasimp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.irgiys.tahasimp.R
import com.irgiys.tahasimp.ui.adapter.SavingListAdapter
import com.irgiys.tahasimp.utils.ViewModelFactory
import com.irgiys.tahasimp.viewmodel.SavingViewModel


class ProgressFragment : Fragment() {
    private lateinit var savingViewModel: SavingViewModel
    private lateinit var adapter: SavingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savingViewModel = obtainViewModel(this)
        adapter = SavingListAdapter()

        savingViewModel.allSavings.observe(viewLifecycleOwner, Observer { savings ->
            // Perbarui UI dengan daftar tabungan
            if(savings!= null){
            adapter.setListSaving(savings)

            }
        })



    }

    private fun obtainViewModel(fragment: Fragment): SavingViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory).get(SavingViewModel::class.java)
    }

}