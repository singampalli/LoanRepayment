package com.capstone.loanrepayment.api

import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class ApiResponse(
    val success: Boolean,
    val message: String
)

data class LoanRequest(val username: String, val status: String)
data class LoanByIdRequest(val loanId: Int)
data class LoanHistory(val loanId:Int, val amount:Float)

interface LoanServiceApi{
    @POST("/loans")
    fun getLoans(@Body request: LoanRequest): Call<LoanType>

    @POST("/loan")
    fun getDetails(@Body request: LoanByIdRequest): Call<List<LoanDetails>>

    @POST("/prepay")
    fun postHistory(@Body request: LoanHistory): Call<ApiResponse>
}