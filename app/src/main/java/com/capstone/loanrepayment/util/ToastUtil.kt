package com.capstone.loanrepayment.util



import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.capstone.loanrepayment.R

object ToastUtil {
    fun showToast(context: Context, message: String, layoutId: Int) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(layoutId, null)

        val text: TextView = layout.findViewById(R.id.custom_toast_message)
        text.text = message

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun showErrorToast(context: Context, message: String) {
        showToast(context, message, R.layout.custom_toast_error)
    }

    fun showSuccessToast(context: Context, message: String) {
        showToast(context, message, R.layout.custom_toast_success)
    }

    fun showWarningToast(context: Context, message: String) {
        showToast(context, message, R.layout.custom_toast_warning)
    }
}
