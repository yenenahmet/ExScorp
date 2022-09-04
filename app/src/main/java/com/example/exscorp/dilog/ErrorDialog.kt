package com.example.exscorp.dilog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import com.example.exscorp.databinding.DialogErrorBinding
import java.lang.Exception

class ErrorDialog(private val activity: Activity) : Dialog(activity) {

    private lateinit var binding: DialogErrorBinding
    private var errorMessage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogErrorBinding.inflate(
            LayoutInflater.from(context), null, false
        )
        with(binding){
            setContentView(root)
            setCancelable(false)
            buttonClose.setOnClickListener {
                dismissDialog()
            }
            textViewText.text = errorMessage
        }
    }

    fun showDialog(errorMessage: String) {
        if (!activity.isFinishing) {
            activity.runOnUiThread {
                this.errorMessage = errorMessage
                show()
            }
        }
    }

    fun dismissDialog() {
        try {
            if (!activity.isFinishing && isShowing) {
                activity.runOnUiThread {
                    dismiss()
                }
            }
        } catch (ignored: Exception) {
        }
    }
}