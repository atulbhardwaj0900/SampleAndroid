package com.example.app

import android.widget.Toast

interface IBase {
    fun showProgress()

    fun popProgress()

    fun showToast(message: CharSequence, length : Int = Toast.LENGTH_SHORT)
    fun showSnackBar(message: CharSequence, length : Int = Toast.LENGTH_SHORT)

}
