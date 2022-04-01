package org.skender.intime.feature.medical_data.view

import androidx.core.widget.doOnTextChanged
import org.skender.intime.base.extensions.EMPTY
import org.skender.intime.feature.medical_data.view.InputEditText.Companion.DEFAULT_VALUE

interface InputClearable : InputEditText {

    var forceUpdated: Boolean

    override fun initInputEditText() {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (getValue() != 0L) return@setOnFocusChangeListener
            if (hasFocus) {
                setDefaultAmountText()
            } else {
                forceClearText()
            }
        }

        editText.doOnTextChanged { text, cursorPosition, lengthBefore, length ->
            onTextChanged(text.toString(), cursorPosition, lengthBefore, length)
        }
    }

    override fun onTextChanged(
        text: String,
        cursorPosition: Int,
        lengthBefore: Int,
        length: Int
    ) {
        if (forceUpdated) {
            forceUpdated = false
            return
        }
        super.onTextChanged(text, cursorPosition, lengthBefore, length)
    }

    private fun forceClearText() {
        forceUpdated = true
        val newAmount = String.EMPTY
        oldString = newAmount
        editText.setText(newAmount)
        editText.setSelection(newAmount.length)
        inputListener?.invoke(getValue())
    }

    private fun setDefaultAmountText() {
        editText.setText(DEFAULT_VALUE)
    }
}