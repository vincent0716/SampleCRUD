package com.vmccn.crud.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val uid : Int,
    @ColumnInfo(name = "FirstName") val firstName : String,
    @ColumnInfo(name = "LastNmae") val lastName : String,
    @ColumnInfo(name = "Country") val country : String,
    @ColumnInfo(name = "Username") val username : String,
    @ColumnInfo(name = "AccountType") val accountType : String,
    @ColumnInfo(name = "Email") val email : String,
    @ColumnInfo(name = "ContactNo") val contactNo : String,
    @ColumnInfo(name = "Image" ) val image : String,
)
