package com.capstone.loanrepayment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.loanrepayment.api.AuthServiceApi
import com.capstone.loanrepayment.api.LoanByIdRequest
import com.capstone.loanrepayment.api.LoanRequest
import com.capstone.loanrepayment.api.LoginRequest
import com.capstone.loanrepayment.api.RetrofitClient
import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import com.capstone.loanrepayment.services.LoanService
import com.capstone.loanrepayment.services.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class LoanFragment(val username:String):Fragment() {
//    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loan, container, false)
        val recyclerView:RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        LoanService.loanTypes(context,username,recyclerView,parentFragmentManager)
        return view
   }
}