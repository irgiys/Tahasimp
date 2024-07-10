package com.irgiys.tahasimp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.irgiys.tahasimp.R
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        saving = SavingEntity()

        binding.edtTarget.editText?.addTextChangedListener(NumberTextWatcher(binding.edtTarget.editText!!))
        setupTextWatchers()

        binding.btnSave.setOnClickListener {
            val view: View? = this.currentFocus
            if (view != null) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
             }
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
                        if (targetLong % 500 != 0L) {
                            edtTarget.error = "Inputkan nilai rupiah dengan benar"
                        } else {
                            val dayTargetInt = dayTarget.toInt()
                            val dailySaving =
                                (ceil(targetLong / dayTargetInt.toDouble() / 500) * 500).toLong()
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
                                saving?.target = adjustedTarget
                                saving?.dailyTarget = dailySaving
                                saving?.dayTarget = dayTargetInt
                            }
                            savingViewModel.insertSaving(saving!!)
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun setupTextWatchers() {
        binding.apply {
            edtTitle.editText?.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty().not()) edtTitle.error = null
            }
            edtTarget.editText?.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty().not()) edtTarget.error = null
            }
            edtDayTarget.editText?.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty().not()) edtDayTarget.error = null
            }

        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): SavingViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(SavingViewModel::class.java)
    }
}