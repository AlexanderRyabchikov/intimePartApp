package org.skender.intime.feature.medical_data.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import org.skender.intime.R
import org.skender.intime.base.extensions.EMPTY
import org.skender.intime.databinding.InputMedicalDataViewBinding

class MedicalDataInput @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet), InputClearable {

    private val binding: InputMedicalDataViewBinding =
        InputMedicalDataViewBinding.inflate(LayoutInflater.from(context), this)

    override var forceUpdated: Boolean = false

    override var maxLength: Int = InputEditText.DEFAULT_MAX_LENGTH

    override var inputListener: ((Long) -> Unit)? = null
    override var oldString: String = String.EMPTY
    override val editText: EditText = binding.inputField
    override val suffix: String = " cm"

    var textHint: String = String.EMPTY
        set(value) {
            field = value
            binding.inputLayout.hint =value
        }

    var text: String = String.EMPTY
        set(value) {
            field = value
            binding.inputField.setText(value)
        }

    var menuItems: List<String> = emptyList()
        set(value) {
            field = value
            //val adapter = ArrayAdapter(context, R.layout.list_item, value)
           // (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }

    init {
        textHint = "Medical data"
        menuItems = listOf("cm", "an")

        initInputEditText()
    }
}