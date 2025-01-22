package com.capstone.loanrepayment.api

import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoanRequest(val username: String, val status: String)
data class LoanByIdRequest(val loanId: Int)

interface LoanServiceApi{
    @POST("/loans")
    fun getLoans(@Body request: LoanRequest): Call<LoanType>

    @POST("/loan")
    fun getDetails(@Body request: LoanByIdRequest): Call<List<LoanDetails>>
}