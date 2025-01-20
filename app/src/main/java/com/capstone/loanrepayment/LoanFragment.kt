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
import com.capstone.loanrepayment.api.RetrofitClient
import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import com.capstone.loanrepayment.services.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class LoanFragment(val username:String):Fragment() {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loan, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val users = RetrofitClient.instance.create(AuthServiceApi::class.java)
        users.getLoans(username,"active").enqueue(object : Callback<List<LoanType>>{
            override fun onResponse(
                call: Call<List<LoanType>>,
                response: Response<List<LoanType>>
            ) {
                val user=response.body()?:return
                val adapter=UserAdapter(user){
                        user ->
                    val userDetailFragment=DetailFragment.newInstance(user.id)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.mainActivity,userDetailFragment)
                        .addToBackStack(null)
                        .commit()
                }
                recyclerView.adapter=adapter
            }

            override fun onFailure(call: Call<List<LoanType>>, t: Throwable) {
                // Log the error message
                 Log.e("LoanTypeFetchError", "Failed to fetch loan types", t)
                Toast.makeText(context,"Fail to fetch",Toast.LENGTH_SHORT).show()
            }
        })

        return view
   }
}