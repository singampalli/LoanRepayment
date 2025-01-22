package com.capstone.loanrepayment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.loanrepayment.api.AuthServiceApi
import com.capstone.loanrepayment.api.LoanByIdRequest
import com.capstone.loanrepayment.api.RetrofitClient
import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import com.capstone.loanrepayment.services.LoanService
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch

class DetailFragment:Fragment() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_detail,container,false)
        

        val user = arguments?.getParcelable<LoanDetails>("user")
        Log.d("LoanDetail", "Data fetched: ${user}")


        val id = view.findViewById<TextView>(R.id.id)
        val LoanType = view.findViewById<TextView>(R.id.loanType)
        val LoanAmount = view.findViewById<TextView>(R.id.loanAmount)
        val LoanAccountNumber = view.findViewById<TextView>(R.id.loanAccountNumber)
        val LoanDuration = view.findViewById<TextView>(R.id.loanDuration)
        val loanEmi = view.findViewById<TextView>(R.id.loanEmi)
        val Status = view.findViewById<TextView>(R.id.status)

//        tvLoanDuration.text="${mockUser[]}"
        user?.let {
            id.text = "Name: ${it.id}"
            LoanType.text = "Username: ${it.loanType}"
            LoanAmount.text = "Email: ${it.loanAmount}"
            LoanAccountNumber.text = "Address: ${it.loanAccountNumber}"
            LoanDuration.text = "Phone: ${it.username}"
            loanEmi.text = "Website: ${it.loanEMI}"
            Status.text = "Company: ${it.loanStatus}"
        }


        return view
    }

    companion object {
        suspend fun newInstance(id: Int): DetailFragment {
            val fragment = DetailFragment()

            // Fetch data in a suspend function
            val data = LoanService.LoanDetails(id)

            // Set the data to arguments only after fetching
            data?.let {
                val args = Bundle().apply {
                    putParcelable("user", it)
                }
                fragment.arguments = args

                val user = fragment.arguments?.getParcelable<LoanDetails>("user")
                Log.d("LoanDetails", "Data fetched: ${user?.loanEMI}")
            }
            val user = fragment.arguments?.getParcelable<LoanDetails>("user")
            Log.d("LoanDetails", "Data fetched: ${user?.loanEMI}")
            return fragment
        }
    }
}