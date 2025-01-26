package com.capstone.loanrepayment.services
import com.capstone.loanrepayment.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.capstone.loanrepayment.api.AuthServiceApi
import com.capstone.loanrepayment.api.ForgotPasswordRequest
import com.capstone.loanrepayment.api.ForgotPasswordResponse
import com.capstone.loanrepayment.api.LoginRequest
import com.capstone.loanrepayment.api.LoginResponse
import com.capstone.loanrepayment.api.ResetPasswordRequest
import com.capstone.loanrepayment.api.ResetPasswordResponse
import com.capstone.loanrepayment.api.RetrofitClient
import com.capstone.loanrepayment.api.SignUpRequest
import com.capstone.loanrepayment.api.SignUpResponse
import com.capstone.loanrepayment.api.userDetailsResponse
import com.capstone.loanrepayment.models.User

object AuthService {
    private val users = mutableListOf<User>()
    private val api = RetrofitClient.instance.create(AuthServiceApi::class.java)

    fun authenticateUser(username: String, password: String,callback: (Boolean, String?, String?) -> Unit){
        val call = api.login(LoginRequest(username, password))
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    callback(true, loginResponse.token, null)
                } else {
                    callback(false, null, R.string.login_error_message.toString())
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(false, null, "Network error: ${t.message}")
            }
        })
    }
    //authenticateUser(username: String, callback: (Boolean, String?, String?) -> Unit){
    fun forgotPassword(email: String,callback: (Boolean, String?, String?) -> Unit){
        // Add password reset logic here
        val call = api.forgotPassword(ForgotPasswordRequest(email));
        call.enqueue(object : Callback<ForgotPasswordResponse>{
            override fun onResponse(call: Call<ForgotPasswordResponse>, response: Response<ForgotPasswordResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val forgotPasswordResponse = response.body()!!
                    callback(true, forgotPasswordResponse.resetCode, null)
                }else {
                    callback(false, null, R.string.login_error_message.toString())
                }
            }
            override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                callback(false, null, "Network error: ${t.message}")
            }
        })
    }
    fun resetPassword(userEmail: String, password: String,callback: (Boolean, String?, String?) -> Unit){
        val call = api.resetPassword(ResetPasswordRequest(userEmail, password))
        call.enqueue(object : Callback<ResetPasswordResponse> {
            override fun onResponse(call: Call<ResetPasswordResponse>, response: Response<ResetPasswordResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val resetPasswordResponse = response.body()!!
                    callback(resetPasswordResponse.status, resetPasswordResponse.message, null)
                } else {
                    callback(false, null, R.string.login_error_message.toString())
                }
            }
            override fun onFailure(call: Call<ResetPasswordResponse>, t: Throwable) {
                callback(false, null, "Network error: ${t.message}")
            }
        })
    }
    fun registerUser(
        username: String,
        email: String,
        password: String,
        callback: (Boolean, String?, String?) -> Unit
    ) {
        // Add password reset logic here
        val call = api.register(SignUpRequest(username, email, password));
        call.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val signupResponse = response.body()!!
                    callback(signupResponse.status, signupResponse.message, null)
                } else {
                    callback(false, null, R.string.login_error_message.toString())
                }
            }
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                callback(false, null, "Network error: ${t.message}")
            }
        })
    }
    fun userDetails(username: String,callback: (Boolean, userDetailsResponse?, String?) -> Unit){
        val call = api.userDetails(username);
        call.enqueue(object : Callback<userDetailsResponse> {
            override fun onResponse(
                call: Call<userDetailsResponse>,
                response: Response<userDetailsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val userDetails = response.body()!!
                    callback(true, userDetails, null)
                } else {
                    callback(false, null, R.string.login_error_message.toString())
                }
            }
            override fun onFailure(call: Call<userDetailsResponse>, t: Throwable) {
                callback(false, null, "Network error: ${t.message}")
            }
        })
    }
}
