package com.capstone.loanrepayment.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.loanrepayment.R
import com.capstone.loanrepayment.models.Data
import com.capstone.loanrepayment.models.Value


class HistoryAdapter(val history:List<Value>): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view){
        val date=view.findViewById<TextView>(R.id.date)
        val emiPaid=view.findViewById<TextView>(R.id.emiPaid)
        val interestPaid=view.findViewById<TextView>(R.id.interestPaid)
        val principalPaid=view.findViewById<TextView>(R.id.principalPaid)
        val principalLeft=view.findViewById<TextView>(R.id.principalLeft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.history_loan,parent,false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        val his=history[position]
        holder.date.text=his.date
        holder.emiPaid.text=his.emiPaid.toString()
        holder.interestPaid.text=his.interestPaid.toString()
        holder.principalPaid.text=his.principalPaid.toString()
        holder.principalLeft.text=his.principalLeft.toString()
        if(his.principalLeft<=0) holder.principalLeft.text="0"
    }

    override fun getItemCount(): Int {
        return history.size
    }

}