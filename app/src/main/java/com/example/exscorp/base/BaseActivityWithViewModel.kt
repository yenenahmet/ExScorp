package com.example.exscorp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<DB : ViewDataBinding, VM : ViewModel>
(private val viewModelClass: Class<VM>) :AppCompatActivity(){

    protected lateinit var binding: DB

    protected val viewModel by lazy {
        ViewModelProvider(this)[viewModelClass]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding(LayoutInflater.from(this))
        setContentView(binding.root)
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater): DB
}
