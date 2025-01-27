package com.capstone.loanrepayment
import android.content.Intent
import android.media.session.MediaSession.Token
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.capstone.loanrepayment.databinding.ActivityForgotPasswordBinding
import com.capstone.loanrepayment.services.AuthService
import com.capstone.loanrepayment.util.CommonFunctionUtil
import com.capstone.loanrepayment.util.ToastUtil
import com.capstone.loanrepayment.util.TokenManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

//class ForgotPasswordActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityForgotPasswordBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.resetPasswordButton.setOnClickListener {
//            // Handle password reset
//            val email = binding.emailEditText.text.toString()
//            // Perform password reset logic here
//        }
//    }
//}

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var emailInput: TextInputLayout
    private lateinit var resetPasswordButton: MaterialButton
    private lateinit var codeInput: TextInputLayout
    private lateinit var codeEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        emailInput= findViewById(R.id.emailInput)
        emailEditText = findViewById(R.id.emailEditText)
        resetPasswordButton = findViewById(R.id.resetPasswordButton)
        codeEditText = findViewById(R.id.codeEditText);

        resetPasswordButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val resetCodeInput = codeEditText.text.toString();

            if (!resetCodeInput.isEmpty() && !emailEditText.isVisible) {
                if(resetCodeInput == TokenManager.getResetCode(this)){
                    Toast.makeText(this,"Reset to new password",Toast.LENGTH_LONG).show()
                    ToastUtil.showSuccessToast(this,getString(R.string.reset_to_new))
                    val intent=Intent(this, ResetPasswordActivity::class.java)
                    startActivity(intent.putExtra("email",email))
                    finish()
                }
                else{
                    ToastUtil.showErrorToast(this,getString(R.string.invalid_code))
                }

            } else {
                if(email.isEmpty()){
                    emailInput.error = getString(R.string.email_required)
                    return@setOnClickListener
                }else if(!CommonFunctionUtil.isValidEmail(email)){
                    emailInput.error = getString(R.string.invalid_email)
                    return@setOnClickListener
                }else{
                    emailInput.error = null
                }
                if(emailEditText.isVisible && resetCodeInput.isEmpty()){
                    codeInput = findViewById(R.id.codeInput)
                    codeInput.error =  getString(R.string.empty_code);
                    return@setOnClickListener
                }

                val call = AuthService.forgotPassword(email){success, resetCode, errorMessage ->
                    if (success) {
                        // Store the token if SharedPreferences
                        if (resetCode != null) {
                            TokenManager.saveResetCode(this, resetCode)
                            codeInput = findViewById(R.id.codeInput)
                            codeInput.visibility = View.VISIBLE
                            emailEditText.visibility = View.GONE
                            ToastUtil.showSuccessToast(this,getString(R.string.code_sent))
                        };
                    } else {
                        ToastUtil.showErrorToast(this,getString(R.string.rest_failed))
                    }

                }
            }
        }
    }
}
