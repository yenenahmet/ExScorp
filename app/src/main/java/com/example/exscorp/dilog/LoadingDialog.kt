package com.example.exscorp.dilog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import com.example.exscorp.databinding.DialogLoadingBinding
import java.lang.Exception

class LoadingDialog(private val activity: Activity) : Dialog(activity) {

    private lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(
            LayoutInflater.from(context), null, false
        )
        setContentView(binding.root)
        setCancelable(false)
    }

    fun showDialog() {
        if (!activity.isFinishing) {
            activity.runOnUiThread {
                show()
            }
        }
    }

    fun dismissDialog() {
        try {
            if (!activity.isFinishing && isShowing){
                activity.runOnUiThread {
                    dismiss()
                }
            }
        }catch (ignored:Exception){}
    }

}
