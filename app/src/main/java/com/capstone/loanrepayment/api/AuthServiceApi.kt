package com.capstone.loanrepayment.api

import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.models.LoanType
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class LoginRequest(val username: String, val password: String)
data class LoginResponse( val token: String)

data class ForgotPasswordRequest( val email: String)
data class ForgotPasswordResponse( val resetCode: String)


data class ResetPasswordRequest( val userEmail: String, val password: String)
data class ResetPasswordResponse( val status: Boolean, val message: String)

data class SignUpRequest(val username: String, val email: String,  val password: String)
data class SignUpResponse(val status:  Boolean, val message: String)


data class userDetailsResponse(val name: String, val email: String)

interface AuthServiceApi {
    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/forgotPassword")
    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<ForgotPasswordResponse>

    @POST("/resetPassword")
    fun resetPassword(@Body request: ResetPasswordRequest): Call<ResetPasswordResponse>

    @GET("/user")
    fun userDetails(@Query("username") username: String): Call<userDetailsResponse>

    @POST("/register")
    fun register(@Body request: SignUpRequest): Call<SignUpResponse>

}


