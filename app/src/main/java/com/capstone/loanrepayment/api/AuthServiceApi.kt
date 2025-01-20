package com.capstone.loanrepayment.api

import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class LoginRequest(val username: String, val password: String)
data class LoginResponse( val token: String)



interface AuthServiceApi {
    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("loans/{username}/{status}")
    fun getLoans(@Path("username") username: String,@Path("status") status:String): Call<List<LoanType>>

    @GET("loan/{id}")
    fun getDetails(@Path("id") id:Int):Call<List<LoanDetails>>
}


