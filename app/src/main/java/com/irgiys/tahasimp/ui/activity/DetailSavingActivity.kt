package com.irgiys.tahasimp.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.irgiys.tahasimp.R
import com.irgiys.tahasimp.databinding.ActivityDetailSavingBinding
import com.irgiys.tahasimp.db.entity.HistoryTransactionEntity
import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.ui.adapter.HistoryListAdapter
import com.irgiys.tahasimp.utils.NumberTextWatcher
import com.irgiys.tahasimp.utils.ViewModelFactory
import com.irgiys.tahasimp.utils.formatCurrency
import com.irgiys.tahasimp.utils.formatDate
import com.irgiys.tahasimp.utils.parseStringToLong
import com.irgiys.tahasimp.viewmodel.SavingViewModel
import java.util.Date

class DetailSavingActivity : AppCompatActivity() {
    private var _activityDetailSavingBinding: ActivityDetailSavingBinding? = null
    private val binding get() = _activityDetailSavingBinding!!

    private lateinit var saving: SavingEntity
    private lateinit var savingViewModel: SavingViewModel

    private lateinit var adapter: HistoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailSavingBinding = ActivityDetailSavingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savingViewModel = obtainViewModel(this@DetailSavingActivity)
        saving = intent.getParcelableExtra(EXTRA_SAVING)!!

        savingViewModel.loadNetSavings(saving.id)
        savingViewModel.netSavings.observe(this, Observer { netSavings ->
            if (netSavings != null) {
                binding.tvAccumulatedSaving.text = formatCurrency(netSavings)
                if (netSavings < saving.target!!) {
                    val kurang = saving.target!! - netSavings
                    binding.tvSavingMinus.text = formatCurrency(kurang)
                }else {
                    binding.tvSavingMinus.text = formatCurrency(0L)
                }

            }
        })

        binding.apply {
            tvTitle.text = saving.title
            tvTargetSaving.text = formatCurrency(saving.target)
            tvDailySaving.text = formatCurrency(saving.dailyTarget)
            tvDayTarget.text = "${saving.dayTarget.toString()} hari"
            tvDateCreated.text = formatDate(saving.dateCreated)
            btnAddSaving.setOnClickListener {
                showInputDialog(true)
            }
            btnReduceSaving.setOnClickListener {
                showInputDialog(false)
            }
        }
        adapter = HistoryListAdapter()
        binding.rvHistorySavings.adapter = adapter
        binding.rvHistorySavings.layoutManager = LinearLayoutManager(this)

        savingViewModel.historyTransactions.observe(this, Observer { transactions ->
            adapter.submitList(transactions)
        })

        savingViewModel.loadHistoryTransactions(saving.id)

    }

    private fun showInputDialog(isSaving: Boolean) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input_saving, null)
        val edtSavingAmount = dialogView.findViewById<TextInputEditText>(R.id.edt_saving_amount)
        val edtSavingAmountLayout =
            dialogView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.edt_saving_amount_layout)
        edtSavingAmount.addTextChangedListener(NumberTextWatcher(edtSavingAmount))
        val title = if (isSaving) "Tambah Tabungan" else "Kurangin"
        val hint = if (isSaving) "Masukkin jumlah tabungan" else "Masukkin jumlah penarikan"
        edtSavingAmountLayout.hint = hint
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle(title)
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val amountStr = edtSavingAmount.text.toString()
                if (!amountStr.isEmpty()) {
                    val amount = parseStringToLong(amountStr)
                    saveSaving(amount, isSaving)
                }
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun saveSaving(amount: Long, isSaving: Boolean) {
        val savingId = saving.id
        val type = if (isSaving) "saving" else "withdrawal"
        val transaction = HistoryTransactionEntity(
            savingId = savingId,
            date = Date(),
            amount = amount,
            type = type
        )
        savingViewModel.insertTransaction(transaction)
    }

    companion object {
        const val EXTRA_SAVING = "extra_saving"
    }

    private fun obtainViewModel(activity: AppCompatActivity): SavingViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(SavingViewModel::class.java)
    }
}