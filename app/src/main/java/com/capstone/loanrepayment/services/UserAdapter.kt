package com.capstone.loanrepayment.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.loanrepayment.R
import com.capstone.loanrepayment.models.Data
import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import com.capstone.loanrepayment.models.User

class UserAdapter(val users:List<Data>,val onItemClick:(Data)->Unit):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view:View): RecyclerView.ViewHolder(view){
        val name:TextView=view.findViewById(R.id.loanType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user=users[position]
        holder.name.text=user.loanType
//        holder.email.text=user.email
        holder.itemView.setOnClickListener{
            onItemClick(user)
        }
    }
}