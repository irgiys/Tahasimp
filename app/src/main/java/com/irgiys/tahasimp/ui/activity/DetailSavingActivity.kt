package com.irgiys.tahasimp.ui.activity

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import com.irgiys.tahasimp.utils.calculateDaysSince
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

    private var currentSavings: Long = 0L

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailSavingBinding = ActivityDetailSavingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savingViewModel = obtainViewModel(this@DetailSavingActivity)
        saving = intent.getParcelableExtra(EXTRA_SAVING)!!

        val daysSinceCreated = calculateDaysSince(saving.dateCreated)
        binding.tvSinceCreated.text = "Tabungan ini sudah berjalan selama $daysSinceCreated hari"

        supportActionBar?.title = "Detail tabungan"

        val typedValue = TypedValue()
        val theme = theme
        theme.resolveAttribute(com.google.android.material.R.attr.colorPrimarySurface, typedValue, true)
        val colorPrimarySurface = typedValue.data
        supportActionBar?.setBackgroundDrawable(ColorDrawable(colorPrimarySurface))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var isEverSaving = false
        savingViewModel.loadNetSavings(saving.id)
        savingViewModel.netSavings.observe(this, Observer { netSavings ->
            if (netSavings != null) {
                isEverSaving = true
                currentSavings = netSavings
                binding.tvAccumulatedSaving.text = formatCurrency(netSavings)
                binding.tvAccumulatedSaving.setTextColor(android.graphics.Color.parseColor("#4CAF50"))
                binding.tvSavingMinus.setTextColor(android.graphics.Color.parseColor("#F44336"))
                if (netSavings < saving.target!!) {
                    val kurang = saving.target!! - netSavings
                    binding.tvSavingMinus.text = formatCurrency(kurang)
                    saving.isCompleted = false
                    savingViewModel.updateSaving(saving)
                } else {
                    saving.isCompleted = true
                    savingViewModel.updateSaving(saving)
                    binding.tvSavingMinus.text = formatCurrency(0L)
                }
                binding.cvIsCompleted.visibility = if(saving.isCompleted) View.VISIBLE else View.GONE
            }
        })

        binding.apply {
            tvTitle.text = saving.title
            tvTargetSaving.text = formatCurrency(saving.target)
            tvDailySaving.text = formatCurrency(saving.dailyTarget) + " per hari"
            tvDayTarget.text = "${saving.dayTarget.toString()} hari"
            tvDateCreated.text = formatDate(saving.dateCreated)

            btnAddSaving.setOnClickListener {
                showInputDialog(true)
            }
            btnReduceSaving.setOnClickListener {
                if (isEverSaving) {
                    showInputDialog(false)
                } else {
                    Toast.makeText(
                        this@DetailSavingActivity,
                        "Nabung dulu baru bisa dikurangin",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
    private fun showUpdateDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_update_saving, null)
        val edtTitle = dialogView.findViewById<TextInputEditText>(R.id.edt_title)
        val edtTarget = dialogView.findViewById<TextInputEditText>(R.id.edt_target)
        val edtDayTarget = dialogView.findViewById<TextInputEditText>(R.id.edt_day_target)


        edtTarget.addTextChangedListener(NumberTextWatcher(edtTarget))
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Update Tabungan")
            .setView(dialogView)
            .setPositiveButton("Perbarui", null)
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

//        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
//            val title = edtTitle.text.toString()
//            val target = edtTarget.text.toString()
//            val dayTarget = edtDayTarget.text.toString()
//
//            if (title.isEmpty()) {
//                edtTitle.error = "Kolom tidak boleh kosong"
//            } else if (target == null || target % 500 != 0L) {
//                edtTarget.error = "Inputkan nilai rupiah dengan benar"
//            } else {
//                Toast.makeText(this, "Tabungan berhasil diperbarui", Toast.LENGTH_SHORT).show()
//                alertDialog.dismiss()
//            }
//        }
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
            .setPositiveButton("Simpan", null)
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val amountStr = edtSavingAmount.text.toString()
            if (amountStr.isEmpty()) {
                edtSavingAmountLayout.error = "Field ini tidak boleh kosong"
            } else {
                val amount = parseStringToLong(amountStr)
                if (!isSaving && amount > currentSavings) {
                    edtSavingAmountLayout.error =
                        "Jumlah penarikan tidak boleh lebih besar dari tabungan"
                } else if (amount % 500 != 0L) {
                    edtSavingAmountLayout.error = "Inputkan nilai rupiah dengan benar"
                } else {
                    edtSavingAmountLayout.error = null
                    saveSaving(amount, isSaving)
                    alertDialog.dismiss()
                }
            }
        }
    }

    private fun saveSaving(amount: Long, isSaving: Boolean) {
        val savingId = saving.id
        val type = if (isSaving) "saving" else "withdrawal"
        val transaction = HistoryTransactionEntity(
            savingId = savingId,
            dateCreated = Date(),
            amount = amount,
            type = type
        )
        savingViewModel.insertTransaction(transaction)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update -> {
                showUpdateDialog()
                true
            }
            R.id.action_delete -> {
                showDeleteConfirmationDialog()
                true
            }

            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Saving")
            .setMessage("Yakin hapus tabungan ini?")
            .setPositiveButton("Hapus") { _, _ ->
                Toast.makeText(this, "Tabungan ${saving.title} berhasil dihapus", Toast.LENGTH_SHORT).show()
                savingViewModel.deleteSaving(saving)
                finish()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_saving, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailSavingBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): SavingViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(SavingViewModel::class.java)
    }

    companion object {
        const val EXTRA_SAVING = "extra_saving"
    }
}