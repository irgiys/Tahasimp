package com.irgiys.tahasimp.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.*

class NumberTextWatcher(private val editText: EditText) : TextWatcher {

    private val formatter: NumberFormat = NumberFormat.getNumberInstance(Locale.getDefault())

    init {
        formatter.isGroupingUsed = true
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        editText.removeTextChangedListener(this)

        val cleanString = s.toString().replace("[^\\d]".toRegex(), "")
        if (cleanString.isNotEmpty()) {
            val formattedString = formatter.format(cleanString.toLong())
            editText.setText(formattedString)
            editText.setSelection(formattedString.length)
        } else {
            editText.text = null
        }
        editText.addTextChangedListener(this)
    }
}

// Usage
//val edtTarget = findViewById<EditText>(R.id.edt_target)
//
//edtTarget.addTextChangedListener(NumberTextWatcher(edtTarget))
