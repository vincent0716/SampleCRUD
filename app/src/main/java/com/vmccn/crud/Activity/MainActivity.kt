package com.vmccn.crud.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vmccn.crud.Adapter.EmployeeListAdapter
import com.vmccn.crud.Model.Employee
import com.vmccn.crud.R
import com.vmccn.crud.Room.AppDatabase
import com.vmccn.crud.ViewModel.EmplyeeViewModel
import com.vmccn.crud.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : EmplyeeViewModel
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbar.toolbar)
        viewModel = ViewModelProvider(this).get(EmplyeeViewModel::class.java)
        db = AppDatabase(baseContext)
        viewModel.getAllEmployee(db)
        viewModel.list.observe(this, Observer {
            CoroutineScope(Dispatchers.Main).launch {
                binding.rvProfileList.adapter = EmployeeListAdapter(baseContext, it as ArrayList<Employee>,db,viewModel)
                binding.rvProfileList.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false);
            }

        })



        initListeners();

    }

    private fun initListeners() {
       binding.fbNewEmployee.setOnClickListener(View.OnClickListener {
           val intent = Intent(baseContext,EmployeeFormActivity::class.java)
           startActivity(intent)
       })


    }
}