package org.skender.intime.feature.medical_data.view

import android.text.Selection
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import org.skender.intime.base.extensions.EMPTY
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

interface InputEditText {
    var maxLength: Int
    var inputListener: ((Long) -> Unit)?
    var oldString: String
    val editText: EditText
    val suffix: String

    fun getValue(): Long {
        val amountText = editText.text
            .toString()
            .replace("\\s".toRegex(), "")
            .replace(',', '.')

        val integer = amountText.substringBeforeLast('.').filter(Char::isDigit)
        val decimal = amountText.substringAfterLast('.', missingDelimiterValue = String.EMPTY)
            .filter(Char::isDigit)
            .take(DECIMAL_PART_SIZE)
            .padEnd(DECIMAL_PART_SIZE, '0')

        return (integer + decimal).toLong()
    }

    fun setListener(inputListener: (Long) -> Unit) {
        this.inputListener = inputListener
    }

    fun initInputEditText() {
        setFormattedText(DEFAULT_VALUE.withSuffix())
        editText.post {
            editText.setSelection(DEFAULT_VALUE.length)
        }

        editText.doOnTextChanged { text, cursorPosition, lengthBefore, length ->
            onTextChanged(text.toString(), cursorPosition, lengthBefore, length)
        }
    }

    fun onTextChanged(text: String,
                              cursorPosition: Int,
                              lengthBefore: Int,
                              length: Int) {
        val newString = text.withoutSuffix()
        when {
            newString.isNullOrEmpty() && newString != DEFAULT_VALUE -> {
                setFormattedText(
                    stringFormat(DEFAULT_VALUE, cursorPosition, lengthBefore, length)
                )
            }

            newString != oldString && length != lengthBefore -> {
                val formattedAmount =
                    stringFormat(newString, cursorPosition, lengthBefore, length)
                setFormattedText(formattedAmount)
            }
        }
    }

    private fun stringFormat(
        newString: String,
        cursorPosition: Int,
        lengthBefore: Int,
        length: Int
    ): String {
        return if (checkAmountLength(newString, maxLength)) {
            var formattedString = getSpacedAmount(newString)

            val isSpaceDeleted = newString.length < formattedString.length && lengthBefore > 0

            val isAmountFromClipboard = length == 0

            if (cursorPosition == 0 &&
                length == 1 &&
                formattedString.length == 2 &&
                formattedString[1] == DEFAULT_VALUE[0]
            ) {
                formattedString = formattedString.filter { it != DEFAULT_VALUE[0] }
                val newCursorPosition = editText.length()
                editText.setSelection(newCursorPosition)
            }

            if (isSpaceDeleted && isAmountFromClipboard && cursorPosition > 0) {
                val correctedAmount = newString.removeRange(cursorPosition - 1, cursorPosition)
                formattedString = getSpacedAmount(correctedAmount)
            }

            val suffixLength = suffix.length
            if (cursorPosition > editText.text.length - suffixLength &&
                editText.text.length  > suffixLength
            ) {
                Selection.setSelection(
                    editText.text,
                    editText.text.length - suffixLength
                )
            }

            formattedString.withSuffix()
        } else {
            oldString
        }
    }

    private fun checkAmountLength(amount: String, integerLength: Int = 12, decimalLength: Int = 2): Boolean {
        if (amount.isEmpty()) return true

        val parts = getDigitFromString(amount).split(DECIMAL_SEPARATOR_COMMA)

        return if (parts.size > 1) {
            parts[0].length <= integerLength && parts[1].length <= decimalLength
        } else {
            parts[0].length <= integerLength
        }
    }

    private fun getDigitFromString(string: String): String {
        val withoutDots = string.replace(
            DECIMAL_SEPARATOR_DOT,
            DECIMAL_SEPARATOR_COMMA
        )
        var commaFound = false
        return withoutDots.filter {
            val result = !commaFound && it == DECIMAL_SEPARATOR_COMMA || it in '0'..'9'
            if (it == DECIMAL_SEPARATOR_COMMA) commaFound = true
            result
        }
    }

    private fun getSpacedAmount(value: String): String {
        if (value.isEmpty()) return String.EMPTY

        val parts = getDigitFromString(value).split(DECIMAL_SEPARATOR_COMMA)
        val mainPart = spacedSum(
            if (parts[0].isEmpty()) {
                0
            } else {
                parts[0].toLong()
            }
        )
        val centsPart = if (parts.size > 1) {
            ",${parts[1]}"
        } else {
            ""
        }
        return "$mainPart$centsPart"
    }

    private fun spacedSum(value: Long): String {
        return value.toString().replace("""(\d)(?=(\d\d\d)+([^\d]|$))""".toRegex(), """$1 """)
    }

    private fun setFormattedText(text: String) {
        val cursorPosition = calculateSelectionPosition(text.length)

        oldString = text

        editText.setText(text)
        editText.setSelection(cursorPosition)
        inputListener?.invoke(getValue())
    }

    private fun calculateSelectionPosition(newAmountTextLength: Int): Int {
        val oldAmountTextLength = editText.length()
        val oldCursorEndPosition = editText.selectionEnd

        val newCursorPosition =
            newAmountTextLength - abs(oldCursorEndPosition - oldAmountTextLength)

        return min(max(0, newCursorPosition), newAmountTextLength)
    }

    private fun String.withSuffix(): String = "$this$suffix"

    private fun String.withoutSuffix(): String {
        return if (this.contains(suffix)) {
            this.replace(suffix, "").trim()
        } else {
            this
        }
    }

    companion object {
        const val DECIMAL_PART_SIZE = 2
        const val DEFAULT_VALUE = "0"
        const val DEFAULT_MAX_LENGTH = 12
        private const val DECIMAL_SEPARATOR_COMMA = ','
        private const val DECIMAL_SEPARATOR_DOT = '.'
    }
}