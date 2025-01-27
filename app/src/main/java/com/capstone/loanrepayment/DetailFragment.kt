package com.capstone.loanrepayment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.concurrent.CountDownLatch

class DetailFragment(val flag: Boolean):Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_detail,container,false)

        val user = arguments?.getParcelable<LoanDetails>("user")
        Log.d("LoanDetail", "Data fetched: ${user}")


//        val id = view.findViewById<TextView>(R.id.id)
//        val LoanType = view.findViewById<TextView>(R.id.loanType)
        val LoanAmount = view.findViewById<TextView>(R.id.loanAmount)
        val LoanAccountNumber = view.findViewById<TextView>(R.id.loanAccountNumber)
        val LoanDuration = view.findViewById<TextView>(R.id.loanDuration)
        val loanEmi = view.findViewById<TextView>(R.id.loanEmi)
        val Status = view.findViewById<TextView>(R.id.status)
        val history=view.findViewById<TextView>(R.id.history)

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val Enddate = LocalDate.parse(user?.loanEndDate, formatter)
        val Startdate= LocalDate.parse(user?.loanStartDate, formatter)
        val duration= Period.between(Startdate,Enddate)


        user?.let {
            LoanAmount.text = "${it.loanAmount}"
            LoanAccountNumber.text = "${it.loanAccountNumber}"
            LoanDuration.text = "${duration.years} years ${duration.months} months ${duration.days} days"
            loanEmi.text = "Rs. ${it.loanEMI}"
            Status.text = "${it.loanType.toUpperCase()}(${it.loanStatus})"
        }

        if (flag && user != null) {
            history.text = "Loan History"
//            val date=user.loanHistory[0]
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            parentFragmentManager.beginTransaction()
                .replace(R.id.loanHistory, HistoryFragment(user.loanHistory.sortedByDescending { LocalDate.parse(it.date, formatter) }.sortedBy { it.principalLeft }))
//                .addToBackStack(null)
                .commit()
        }

        val statusTextView: TextView = view.findViewById(R.id.status)
        when(user?.loanStatus){
            "active" -> statusTextView.setTextColor(Color.GREEN)
            else -> statusTextView.setTextColor(Color.RED)
        }

        return view
    }



    companion object {
        suspend fun newInstance(id: Int,flag:Boolean): DetailFragment {
            val fragment = DetailFragment(flag)

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