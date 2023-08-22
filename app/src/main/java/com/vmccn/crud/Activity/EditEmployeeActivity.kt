package com.vmccn.crud.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vmccn.crud.R
import com.vmccn.crud.databinding.ActivityEditEmployeeBinding

class EditEmployeeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditEmployeeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}