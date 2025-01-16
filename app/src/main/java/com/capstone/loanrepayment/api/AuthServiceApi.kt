package com.capstone.loanrepayment.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val username: String, val password: String)
data class LoginResponse( val token: String)

interface AuthServiceApi {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}


