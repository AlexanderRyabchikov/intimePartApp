package org.skender.intime.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.skender.intime.R

class ProgressDialog : DialogFragment() {

    private var propagateBackPress: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also { dialog ->
            setStyle(STYLE_NO_TITLE, 0)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            if (propagateBackPress) {
                dialog.setOnKeyListener { _, keyCode, keyEvent ->
                    if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                        requireActivity().onBackPressed()
                    }

                    false
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_progress, container, false)
    }

    companion object {

        fun newInstance(
            propagateBackPress: Boolean = false,
        ): ProgressDialog {
            return ProgressDialog().apply {
                isCancelable = false
                this.propagateBackPress = propagateBackPress
            }
        }
    }
}
