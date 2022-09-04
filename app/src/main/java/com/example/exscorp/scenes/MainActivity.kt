package com.example.exscorp.scenes

import Person
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exscorp.BaseActivity
import com.example.exscorp.listeners.PaginationScrollListener
import com.example.exscorp.databinding.ActivityMainBinding
import com.example.exscorp.dilog.ErrorDialog
import com.example.exscorp.dilog.LoadingDialog
import com.example.exscorp.model.Status

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class.java) {

    private val adapter = PeopleAdapter()
    private lateinit var loadingDialog:LoadingDialog
    private lateinit var errorDialog:ErrorDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        listenerPagination()
        observeViewModel()
        loadingDialog.showDialog()
        viewModel.getData()
    }

    private fun initView(){
        binding.recyclerView.adapter = adapter
        loadingDialog = LoadingDialog(this)
        errorDialog = ErrorDialog(this)
    }

    private fun observeViewModel(){
        with(viewModel){
            liveDataPeople.observe(this@MainActivity,{response->
                loadingDialog.dismissDialog()
                if(response.status == Status.SUCCESS){
                    initData(response?.data?: mutableListOf())
                }else{
                    errorDialog.showDialog(response?.errMessage?:"")
                }
            })
        }
    }


    private fun initData(people:List<Person>){
        adapter.addPeople(people)
    }

    private fun listenerPagination(){
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        binding.recyclerView.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                viewModel.isItLoadingPeople = true
                loadingDialog.showDialog()
                viewModel.getData()
            }
            override fun isLastPage(): Boolean  = false
            override fun isLoading(): Boolean = viewModel.isItLoadingPeople
        })
    }


    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }
}