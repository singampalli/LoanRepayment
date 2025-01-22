package com.capstone.loanrepayment.services

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.capstone.loanrepayment.DetailFragment
import com.capstone.loanrepayment.LoanFragment
import com.capstone.loanrepayment.R
import com.capstone.loanrepayment.api.AuthServiceApi
import com.capstone.loanrepayment.api.LoanByIdRequest
import com.capstone.loanrepayment.api.LoanRequest
import com.capstone.loanrepayment.api.LoanServiceApi
import com.capstone.loanrepayment.api.RetrofitClient
import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoanService {
    private val api = RetrofitClient.instance.create(LoanServiceApi::class.java)

    fun loanTypes(
        context: Context?,
        username: String,
        recyclerView: RecyclerView,
        parentFragmentManager: FragmentManager
    ) {
        api.getLoans(LoanRequest(username, "active")).enqueue(object : Callback<LoanType> {
            @SuppressLint("ResourceType")
            override fun onResponse(
                call: Call<LoanType>,
                response: Response<LoanType>
            ) {
                val loans = response.body() ?: return
                val adapter = UserAdapter(loans.data) { user ->
                    GlobalScope.launch {
                        val userDetailFragment = DetailFragment.newInstance(user.id)
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentSpace, userDetailFragment)
                            .addToBackStack(null)
                            .commit()
                    }
                }
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<LoanType>, t: Throwable) {
                // Log the error message
                Log.e("LoanTypeFetchError", "Failed to fetch loan types " + t.message, t)
                Toast.makeText(context, "Fail to fetch", Toast.LENGTH_SHORT).show()
            }
        })
    }


    suspend fun LoanDetails(id: Int): LoanDetails? = withContext(Dispatchers.IO) {
        val deferred = CompletableDeferred<LoanDetails?>()

        api.getDetails(LoanByIdRequest(id)).enqueue(object : Callback<List<LoanDetails>> {
            override fun onResponse(
                call: Call<List<LoanDetails>>,
                response: Response<List<LoanDetails>>
            ) {
                deferred.complete(response.body()?.getOrNull(0))
            }

            override fun onFailure(call: Call<List<LoanDetails>>, t: Throwable) {
                Log.e("LoanDetailsFailure", "Failed to fetch loan details: ${t.message}")
                deferred.complete(null)
            }
        })

        deferred.await()
    }
}