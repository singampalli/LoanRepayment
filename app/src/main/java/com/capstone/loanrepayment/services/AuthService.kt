package com.capstone.loanrepayment.services
import android.widget.Toast
import com.capstone.loanrepayment.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.capstone.loanrepayment.api.AuthServiceApi
import com.capstone.loanrepayment.api.ForgotPasswordRequest
import com.capstone.loanrepayment.api.ForgotPasswordResponse
import com.capstone.loanrepayment.api.LoginRequest
import com.capstone.loanrepayment.api.LoginResponse
import com.capstone.loanrepayment.api.RetrofitClient
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
    fun resetPassword(email: String,callback: (Boolean, String?, String?) -> Unit){
        // Add password reset logic here
        val call = api.forgotPassword(ForgotPasswordRequest(email));
        call.enqueue(object : Callback<ForgotPasswordResponse>{
            override fun onResponse(call: Call<ForgotPasswordResponse>, response: Response<ForgotPasswordResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val forgotPasswordResponse = response.body()!!
                    callback(true, forgotPasswordResponse.resetCode, null)
                }
//                else {
//                    Toast.makeText(this@ForgotPasswordActivity, "Failed to send reset email!", Toast.LENGTH_SHORT).show()
//                }
            }

            override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                callback(false, null, "Network error: ${t.message}")
            }
        })
    }
}
