package com.abc.app.presentation.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackbarUtils {

    fun showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {Snackbar.make(view, message, duration).show()
    }
}