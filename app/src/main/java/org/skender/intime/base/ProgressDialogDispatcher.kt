package org.skender.intime.base

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

interface ProgressDialogDispatcher {

    val propagateModalProgressBackPress: Boolean

    private val progressDialog: DialogFragment?
        get() = getChildFragmentManager().findFragmentByTag(PROGRESS_DIALOG_TAG) as? DialogFragment

    fun getChildFragmentManager(): FragmentManager

    fun showModalProgress() {
        hideModalProgress()

        ProgressDialog
            .newInstance(propagateBackPress = propagateModalProgressBackPress)
            .showNow(getChildFragmentManager(), PROGRESS_DIALOG_TAG)
    }

    fun hideModalProgress() {
        progressDialog?.dismissAllowingStateLoss()
    }

    fun isModalProgressVisible(): Boolean = progressDialog?.isVisible ?: false

    companion object {

        private const val PROGRESS_DIALOG_TAG = "progress"
    }
}