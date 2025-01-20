package com.capstone.loanrepayment.api

import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class LoginRequest(val username: String, val password: String)
data class LoanRequest(val username: String, val status: String)
data class LoanByIdRequest(val id: Int)
data class LoginResponse( val token: String)



interface AuthServiceApi {
    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/loans")
    fun getLoans(@Body request: LoanRequest): Call<LoanType>

    @POST("/loan")
    fun getDetails(@Body request: LoanByIdRequest):Call<List<LoanDetails>>
}


