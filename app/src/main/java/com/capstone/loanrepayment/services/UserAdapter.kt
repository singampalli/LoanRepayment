package com.capstone.loanrepayment.services

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.capstone.loanrepayment.LoanFragment
import com.capstone.loanrepayment.PaymentActivity
import com.capstone.loanrepayment.R
import com.capstone.loanrepayment.models.Data
import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import com.capstone.loanrepayment.models.User

class UserAdapter(val users:List<Data>,val onItemClick:(Data)->Unit,val onPayClick:(Int)->Unit):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view:View): RecyclerView.ViewHolder(view){
        val name:TextView=view.findViewById(R.id.loanType)
        val loanNumber:TextView=view.findViewById(R.id.loanNumber)
        val details:ImageView=view.findViewById(R.id.details)
        val pay:ImageView=view.findViewById(R.id.pay)
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
        holder.loanNumber.text=user.loanAccountNumber
        holder.name.text=user.loanType.toUpperCase()
        if(user.loanStatus=="closed"){
            holder.pay.visibility=View.GONE
        }
        else{
            holder.pay.visibility=View.VISIBLE
        }
//        holder.email.text=user.email
        holder.details.setOnClickListener{
            onItemClick(user)
        }
        holder.pay.setOnClickListener{
            onPayClick(user.id)
        }
    }
}