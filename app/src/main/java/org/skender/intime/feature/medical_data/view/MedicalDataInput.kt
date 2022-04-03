package org.skender.intime.feature.medical_data.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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

    private var isRotate = false

    override var forceUpdated: Boolean = false

    override var maxLength: Int = InputEditText.DEFAULT_MAX_LENGTH

    override var inputListener: ((Long) -> Unit)? = null
    override var oldString: String = String.EMPTY
    override val editText: EditText = binding.inputField
    override var suffix: String = " cm"
        set(value) {
            val newSuffix = " $value"
            setNewSuffixToEditText(newSuffix, field)
            field = newSuffix
        }

    var textHint: String = String.EMPTY
        set(value) {
            field = value
            binding.inputField.hint = value
        }

    var text: String = String.EMPTY
        set(value) {
            field = value
            binding.inputField.setText(value)
        }

    var menuItems: List<String> = emptyList()
        set(value) {
            field = value
            val adapter = ArrayAdapter(context, R.layout.list_item, value)
            (binding.dropdownMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            suffix = menuItems.first()
        }

    init {
        menuItems = listOf("cm", "an")
        initInputEditText()
        setupViews()
        setupAttributes(attributeSet)
    }

    private fun setupViews() = with(binding) {
        underline.isEnabled = false
        inputField.setOnFocusChangeListener { _, hasFocus ->
            underline.isEnabled = hasFocus
        }
        arrowDropDown.setOnClickListener { view ->
            rotateUpsideDown(view, isRotate).start()
            if (isRotate && !itemMenu.isPopupShowing) {
                itemMenu.showDropDown()
            } else {
                itemMenu.dismissDropDown()
            }
        }

        itemMenu.setOnItemClickListener { _, _, position, _ ->
            rotateUpsideDown(arrowDropDown, isRotate).start()
            suffix = menuItems[position]
        }

        itemMenu.setText(menuItems.first(), false)
    }

    private fun rotateUpsideDown(view: View, isRotate: Boolean): Animator {
        val endPosition = if (isRotate) 0f else 180f
        this.isRotate = !isRotate
        return ObjectAnimator.ofFloat(
            view,
            View.ROTATION,
            view.rotation,
            endPosition
        )
    }

    private fun setupAttributes(attributeSet: AttributeSet) {
        context?.obtainStyledAttributes(attributeSet, R.styleable.MedicalDataInput)!!.use { typedAttrs ->
            textHint = typedAttrs.getString(R.styleable.MedicalDataInput_hint).toString()
        }
    }
}