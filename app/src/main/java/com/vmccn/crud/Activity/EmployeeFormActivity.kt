package com.vmccn.crud.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.vmccn.crud.Model.Employee
import com.vmccn.crud.R
import com.vmccn.crud.Room.AppDatabase
import com.vmccn.crud.Tools.EdtTextChange
import com.vmccn.crud.ViewModel.EmplyeeViewModel
import com.vmccn.crud.databinding.ActivityEmployeeFormBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeFormActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEmployeeFormBinding
    private lateinit var viewModel : EmplyeeViewModel
    private lateinit var db : AppDatabase
    private val PERMISSION_CODE : Int = 1001;
    private var uriString : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this).get(EmplyeeViewModel::class.java)
        binding.atvCountry.setText("")
        binding.atvAccType.setText("")
        db = AppDatabase(baseContext)
        initListeners();
        adapters();

    }

    private fun adapters() {
        CoroutineScope(Dispatchers.Main).launch {
            val countries = listOf("Philippines","USA","Japan","South Korea")
            val accountTypes = listOf("Team Leader","Team Member")

            val country_adapter= ArrayAdapter(baseContext,android.R.layout.simple_list_item_1,countries)
            val account_adapter = ArrayAdapter(baseContext,android.R.layout.simple_list_item_1,accountTypes)
            binding.atvCountry.setAdapter(country_adapter)
            binding.atvAccType.setAdapter(account_adapter)
        }
    }

    private fun initListeners() {
        binding.atvCountry.addTextChangedListener(EdtTextChange(binding.tilCountry))
        binding.atvAccType.addTextChangedListener(EdtTextChange(binding.tilAccountType))
        binding.edtUsername.addTextChangedListener(EdtTextChange(binding.tilUsername))
        binding.edtFname.addTextChangedListener(EdtTextChange(binding.tilFirstName))
        binding.edtLname.addTextChangedListener(EdtTextChange(binding.tilLastName))
        binding.edtEmail.addTextChangedListener(EdtTextChange(binding.tilEmail))


        binding.btnAdd.setOnClickListener(View.OnClickListener {
            if(initFields()){

                try{
                    val employee = Employee(0,binding.edtFname.text.toString()
                        ,binding.edtLname.text.toString()
                        ,binding.atvCountry.text.toString()
                        ,binding.edtUsername.text.toString()
                        ,binding.atvAccType.text.toString()
                        ,binding.edtEmail.text.toString()
                        ,binding.edtContactNo.text.toString()
                        ,uriString)

                    viewModel.createNewEmployee(db,employee)
                    Toast.makeText(baseContext,"Created Successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(baseContext,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                catch (e : Exception){
                    Log.e("TAG", e.toString())
                }


            }
        })

        binding.ivChooseImage.setOnClickListener(View.OnClickListener {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            } else{
                chooseImageGallery();
            }
        })

    }

    private fun chooseImageGallery() {
        val intent  = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data!=null) {
            var uri : Uri? = result.data!!.data
            uriString = uri.toString()
            Glide.with(baseContext).load(uri).error(R.drawable.baseline_person_24).into(binding.ivEmpImage)
        }
    }

    private fun initFields() : Boolean{
        if(binding.atvCountry.text.isNullOrEmpty()){
            binding.tilCountry.isErrorEnabled = true;
            binding.tilCountry.error = "This is required field"
            return false;
        }
        if(binding.atvAccType.text.isNullOrEmpty()){
            binding.tilAccountType.isErrorEnabled = true;
            binding.tilAccountType.error = "This is required field"
            return false;
        }
        if(binding.edtUsername.text.isNullOrEmpty()){
            binding.tilUsername.isErrorEnabled = true;
            binding.tilUsername.error = "This is required field"
            return false;
        }
        if(binding.edtLname.text.isNullOrEmpty()){
            binding.tilLastName.isErrorEnabled = true;
            binding.tilLastName.error = "This is required field"
            return false;

        }
        if(binding.edtFname.text.isNullOrEmpty()){
            binding.tilFirstName.isErrorEnabled = true;
            binding.tilFirstName.error = "This is required field"
            return false;
        }
        if(binding.edtEmail.text.isNullOrEmpty()){
            binding.tilEmail.isErrorEnabled = true;
            binding.tilEmail.error = "This is required field"
            return false;
        }


        return true;
    }
}