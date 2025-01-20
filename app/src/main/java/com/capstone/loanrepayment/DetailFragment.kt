package com.capstone.loanrepayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.capstone.loanrepayment.api.AuthServiceApi
import com.capstone.loanrepayment.api.RetrofitClient
import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_detail,container,false)
        

        val user = arguments?.getParcelable<LoanDetails>("user")


        val tvid = view.findViewById<TextView>(R.id.tvName)
        val tvLoanType = view.findViewById<TextView>(R.id.tvUsername)
        val tvLoanAmount = view.findViewById<TextView>(R.id.tvEmail)
        val tvLoanAccountNumber = view.findViewById<TextView>(R.id.tvAddress)
        val tvLoanDuration = view.findViewById<TextView>(R.id.tvPhone)
        val tvloanEmi = view.findViewById<TextView>(R.id.tvWebsite)
        val tvStatus = view.findViewById<TextView>(R.id.tvCompany)

//        tvLoanDuration.text="${mockUser[]}"
        user?.let {
            tvid.text = "Name: ${it.id}"
            tvLoanType.text = "Username: ${it.loanType}"
            tvLoanAmount.text = "Email: ${it.loanAmount}"
            tvLoanAccountNumber.text = "Address: ${it.loanAccountNumber}"
            tvLoanDuration.text = "Phone: ${it.username}"
            tvloanEmi.text = "Website: ${it.loanEMI}"
            tvStatus.text = "Company: ${it.loanStatus}"
        }


        return view
    }

    companion object {
        fun newInstance(id:Int):DetailFragment{
            val userDetails = RetrofitClient.instance.create(AuthServiceApi::class.java)
            lateinit var data :LoanDetails
            val details=userDetails.getDetails(id)
            details.enqueue(object : Callback<List<LoanDetails>>{
                override fun onResponse(
                    call: Call<List<LoanDetails>>,
                    response: Response<List<LoanDetails>>
                ) {
                    data= response?.body()?.get(0)!!
                }

                override fun onFailure(call: Call<List<LoanDetails>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
            val fragment=DetailFragment()
            val args= Bundle()
            args.putParcelable("user",data)
            fragment.arguments=args
            return fragment
        }
    }
}