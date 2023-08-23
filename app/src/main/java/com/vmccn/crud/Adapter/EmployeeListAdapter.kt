package com.vmccn.crud.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmccn.crud.Activity.EditEmployeeActivity
import com.vmccn.crud.Model.Employee
import com.vmccn.crud.R
import com.vmccn.crud.Room.AppDatabase
import com.vmccn.crud.ViewModel.EmplyeeViewModel


class EmployeeListAdapter(val context : Context,
                           val db : AppDatabase, val viewModel: EmplyeeViewModel ) : RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {
    private var list = ArrayList<Employee>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage : ImageView =itemView.findViewById(R.id.iv_empImage)
        val userFullName : TextView = itemView.findViewById(R.id.tv_empName)
        val userCountry : TextView = itemView.findViewById(R.id.tv_empCountry)
        val userAccType : TextView = itemView.findViewById(R.id.tv_empAccountType)
        val username : TextView = itemView.findViewById(R.id.tv_empUsername)
        val userEmail : TextView = itemView.findViewById(R.id.tv_empEmail)
        val editEmployee : ImageView = itemView.findViewById(R.id.iv_editEmployee)
        val deleteEmployee : ImageView = itemView.findViewById(R.id.iv_deleteEmployee)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setEmployeeList(list : ArrayList<Employee>){
        this.list = list
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchEmployee(searchText : String){
        if(searchText.isNotEmpty())
        {
           val s = list.filter {
                it.firstName.contains(searchText,true)
            }
            setEmployeeList(s as ArrayList<Employee>)

        }
        else{
            setEmployeeList(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_adapter,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userFullName.text = list[position].firstName + " " + list[position].lastName
        if(list[position].image.isEmpty()){
            holder.userImage.setImageResource(R.drawable.baseline_person_24)
        }
        else{

            Glide.with(context).load(Uri.parse(list[position].image)).into(holder.userImage)
        }
        holder.userCountry.text = list[position].country
        holder.userAccType.text = list[position].accountType
        holder.username.text = list[position].username
        holder.userEmail.text = list[position].email
        holder.editEmployee.setOnClickListener(View.OnClickListener {
            val intent = Intent(context,EditEmployeeActivity::class.java).addFlags(FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("id",list[position].uid)
            intent.putExtra("fname",list[position].firstName)
            intent.putExtra("lname",list[position].lastName)
            intent.putExtra("email",list[position].email)
            intent.putExtra("username",list[position].username)
            intent.putExtra("accType",list[position].accountType)
            intent.putExtra("country",list[position].country)
            intent.putExtra("image",list[position].image)
            intent.putExtra("contact",list[position].contactNo)
            context.startActivity(intent)
            //(context as Activity).finish()

        })
        holder.deleteEmployee.setOnClickListener(View.OnClickListener {
            val employee : Employee = list[position]
            viewModel.deleteEmployee(db,list[position])
            Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_SHORT).show()
        })

    }

}