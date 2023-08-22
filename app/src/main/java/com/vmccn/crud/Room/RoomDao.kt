package com.vmccn.crud.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vmccn.crud.Model.Employee

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @Update
    suspend fun  updateEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee : Employee)

    @Query("SELECT * FROM employee")
    fun showAllEmployee() : LiveData<List<Employee>>

    @Query("SELECT * FROM employee WHERE Id=(:id)")
    fun singleEmployee(id : Int) : Employee

    @Query("SELECT * FROM employee WHERE FirstName OR LastNmae LIKE '%' || (:searchText) || '%'")
    fun searchEmployee(searchText : String)  : LiveData<List<Employee>>
}