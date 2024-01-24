package com.trs.goingup.ui.dialog

import android.app.AlertDialog
import android.content.Context

class DialogUtil {

    companion object {

        fun openConfirmationDialog(
            context: Context,
            message: String,
            title: String?,
            onConfirm: (() -> Unit?)? = null,
            onCancel: (() -> Unit?)? = null
        ) {
            val builder = AlertDialog.Builder(context)

            title?.let { builder.setTitle(title) }
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok) { _, _ -> onConfirm?.invoke() }
            builder.setNegativeButton(android.R.string.cancel) { _, _ -> onCancel?.invoke() }

            builder.show()
        }
    }
}