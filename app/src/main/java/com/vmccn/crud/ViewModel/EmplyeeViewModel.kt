package com.vmccn.crud.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vmccn.crud.Model.Employee
import com.vmccn.crud.Room.AppDatabase
import kotlinx.coroutines.launch

class EmplyeeViewModel : ViewModel() {
    val employeeList : MutableLiveData<List<Employee>> = MutableLiveData()

    var list : LiveData<List<Employee>> = employeeList

    fun createNewEmployee(database : AppDatabase,employee : Employee){
        viewModelScope.launch {
            database.employeeDao().insertEmployee(employee)
        }
    }

    fun updateEmployee(database: AppDatabase , employee: Employee){
        viewModelScope.launch {
            database.employeeDao().updateEmployee(employee)
        }
    }

    fun deleteEmployee(database : AppDatabase , employee: Employee){
        viewModelScope.launch {
            database.employeeDao().deleteEmployee(employee)
        }
    }

    fun getAllEmployee (database: AppDatabase){
        viewModelScope.launch {
            list = database.employeeDao().showAllEmployee()

        }
    }




}