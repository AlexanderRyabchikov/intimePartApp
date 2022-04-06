package org.skender.intime.feature.medical_data.view

import androidx.core.widget.doOnTextChanged
import org.skender.intime.base.extensions.EMPTY
import org.skender.intime.feature.medical_data.view.InputEditText.Companion.DEFAULT_VALUE

interface InputClearable : InputEditText {

    var forceUpdated: Boolean

    override fun initInputEditText() {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (editText.text.toString().isNotBlank()) return@setOnFocusChangeListener
            if (hasFocus) {
                setDefaultText()
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
        val newString = String.EMPTY
        oldString = newString
        editText.setText(newString)
        editText.setSelection(newString.length)
        inputListener?.invoke(editText.hint.toString(), newString.withoutSuffix())
    }

    private fun setDefaultText() {
        editText.setText(DEFAULT_VALUE)
    }
}