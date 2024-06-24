package com.irgiys.tahasimp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.irgiys.tahasimp.R
import com.irgiys.tahasimp.databinding.ActivityAddSavingBinding
import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.utils.ViewModelFactory
import com.irgiys.tahasimp.viewmodel.SavingViewModel

class AddSavingActivity : AppCompatActivity() {
    private var _activityAddSavingBinding: ActivityAddSavingBinding? = null
    private val binding get() = _activityAddSavingBinding!!
    private var saving: SavingEntity? = null
    private lateinit var savingViewModel: SavingViewModel
//    private val savingViewModel: SavingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityAddSavingBinding = ActivityAddSavingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savingViewModel = obtainViewModel(this@AddSavingActivity)

        supportActionBar?.title = "Tambah tabungan"
        saving = SavingEntity()
        binding.btnSave.setOnClickListener {
            binding.apply{
                val title = edtTitle.editText?.text.toString()
                val target = edtTarget.editText?.text.toString()
                val dayTarget = edtDayTarget.editText?.text.toString()
                when {
                    title.isEmpty() -> edtTitle.error = "Kolom tidak boleh kosong"
                    target.isEmpty() -> edtTarget.error = "Kolom tidak boleh kosong"
                    dayTarget.isEmpty() -> edtDayTarget.error = "Kolom tidak boleh kosong"
                    else -> {
                        saving.let{
                            saving?.title = title
                            saving?.target = target.toLong()
                            saving?.dayTarget = dayTarget.toInt()
                        }
                        savingViewModel.insertSaving(saving!!)
                        finish()
                    }
                }

            }
        }
    }

    private fun obtainViewModel(activity :AppCompatActivity): SavingViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(SavingViewModel::class.java)
    }
}