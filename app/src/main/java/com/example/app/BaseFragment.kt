package com.example.app

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.R
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

abstract class BaseFragment(open val showProgress: Boolean = false) : Fragment(), IBase {

    private var progressIndex: Int = 0
    private lateinit var progressDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressDialog = AlertDialog.Builder(requireContext())
            .setView(R.layout.dialog_loading_progress)
            .setCancelable(false)
            .create()
        if (showProgress) {
            showProgress()
        }
        return inflater.inflate(getLayoutId(), container, false)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun showProgress() {
        progressIndex++
        if (progressIndex > 0 && !progressDialog.isShowing) {
            progressDialog.show()

            val dip = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                1F,
                this.resources.displayMetrics
            )
            progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams: WindowManager.LayoutParams? = progressDialog.window?.attributes
            layoutParams?.dimAmount = 0.0F
            progressDialog.window?.attributes = layoutParams
            progressDialog.window?.setLayout((128 * dip).toInt(), (128 * dip).toInt())
        }
    }

    override fun popProgress() {
        if (progressIndex > 1) {
            progressIndex--
        } else {
            progressIndex = 0
            if (!(activity?.isFinishing == true || activity?.isDestroyed == true)) {
                progressDialog.dismiss()
            }
        }
    }

    override fun showToast(message: CharSequence, length: Int) {
        Toast.makeText(requireContext(), message, length).show()
    }

    override fun showSnackBar(message: CharSequence, length: Int) {
        activity?.let { it -> Snackbar.make(it.findViewById(android.R.id.content), message, length).show() }
    }

    protected infix fun TextView.notifyWith(onTextChanged: () -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged()
            }
        })
    }
}

fun Float.formatFloat(places: Int): String {
    val df = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.getDefault()))
    df.maximumFractionDigits = places
    return df.format(this)
}