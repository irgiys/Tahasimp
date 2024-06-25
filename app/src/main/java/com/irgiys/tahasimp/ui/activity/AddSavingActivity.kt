package com.irgiys.tahasimp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.irgiys.tahasimp.databinding.ActivityAddSavingBinding
import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.utils.NumberTextWatcher
import com.irgiys.tahasimp.utils.ViewModelFactory
import com.irgiys.tahasimp.utils.formatCurrency
import com.irgiys.tahasimp.utils.parseStringToLong
import com.irgiys.tahasimp.viewmodel.SavingViewModel
import kotlin.math.ceil

class AddSavingActivity : AppCompatActivity() {
    private var _activityAddSavingBinding: ActivityAddSavingBinding? = null
    private val binding get() = _activityAddSavingBinding!!

    private var saving: SavingEntity? = null
    private lateinit var savingViewModel: SavingViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityAddSavingBinding = ActivityAddSavingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savingViewModel = obtainViewModel(this@AddSavingActivity)
        supportActionBar?.title = "Tambah tabungan"
        saving = SavingEntity()

        binding.edtTarget.editText?.addTextChangedListener(NumberTextWatcher(binding.edtTarget.editText!!))

        binding.btnSave.setOnClickListener {
            binding.apply {
                val title = edtTitle.editText?.text.toString()
                val target = edtTarget.editText?.text.toString()
                val dayTarget = edtDayTarget.editText?.text.toString()

                when {
                    title.isEmpty() -> edtTitle.error = "Kolom tidak boleh kosong"
                    target.isEmpty() -> edtTarget.error = "Kolom tidak boleh kosong"
                    dayTarget.isEmpty() -> edtDayTarget.error = "Kolom tidak boleh kosong"
                    else -> {
                        val targetLong = parseStringToLong(target)
                        val dayTargetInt = dayTarget.toInt()
                        val dailySaving = (ceil(targetLong / dayTargetInt.toDouble() / 1000) * 1000).toLong()
                        val adjustedTarget = dailySaving * dayTargetInt

                        binding.cvSavingInfo.visibility = View.VISIBLE
                        val formDaily = formatCurrency(dailySaving)
                        val formTarget = formatCurrency(adjustedTarget)

                        binding.tvSavingInfo.text =
                            "Dengan menabung ${formDaily} per hari selama ${dayTargetInt} hari, kamu dapat mengumpulkan uang sebanyak ${formTarget}."

                        binding.btnSave.isEnabled = false
                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.btnSave.isEnabled = true
                        }, 5000)

                        saving.let {
                            saving?.title = title
                            saving?.target = targetLong
                            saving?.dailyTarget = dailySaving
                            saving?.dayTarget = dayTargetInt
                        }
                        savingViewModel.insertSaving(saving!!)
//                        finish()
                    }
                }
            }
        }
    }
//    private fun inputNumWatcher(){
//        binding.edtTarget.editText?.addTextChangedListener(NumberTextWatcher(binding.edtTarget.editText!!))
//    }

    private fun obtainViewModel(activity: AppCompatActivity): SavingViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(SavingViewModel::class.java)
    }
}