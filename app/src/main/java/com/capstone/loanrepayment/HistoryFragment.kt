package com.capstone.loanrepayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.loanrepayment.models.Value
import com.capstone.loanrepayment.services.HistoryAdapter
import com.capstone.loanrepayment.services.LoanService

class HistoryFragment(val data:List<Value>):Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loan, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter=HistoryAdapter(data)
        recyclerView.adapter=adapter
        recyclerView.isNestedScrollingEnabled =true
        return view
    }
}