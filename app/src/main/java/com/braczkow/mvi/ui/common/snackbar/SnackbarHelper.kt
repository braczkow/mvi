package com.braczkow.mvi.ui.common.snackbar

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.braczkow.mvi.R

class SnackbarHelper(private val rootView: View) {
    fun showMessageWithHide(message: String) {
        val snackbar = Snackbar
                .make(rootView, message, Snackbar.LENGTH_INDEFINITE)

        snackbar
                .setAction(R.string.action_hide) {
                    snackbar.dismiss()
                }
                .show()
    }
}
