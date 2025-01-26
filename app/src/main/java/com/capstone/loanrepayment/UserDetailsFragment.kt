package com.capstone.loanrepayment

import android.content.Intent
import android.media.session.MediaSession.Token
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.loanrepayment.services.AuthService
import com.capstone.loanrepayment.util.TokenManager
import com.google.android.material.textfield.TextInputEditText

class UserDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_details, container, false)

        val usernameValue = view.findViewById<TextView>(R.id.tvUsername)
        val emailValue = view.findViewById<TextView>(R.id.tvEmail)

        val userName = TokenManager.getUserName(requireContext());
        // Fetch user details
        AuthService.userDetails(userName  ?: "") { isSuccess, userDetails, errorMessage ->
            if (isSuccess && userDetails != null) {
                usernameValue.setText(userDetails.name)
                emailValue.setText(userDetails.email)
            } else {
                Toast.makeText(context, errorMessage ?: "Error fetching user details", Toast.LENGTH_SHORT).show()
            }
        }
        val resetPasswordButton = view.findViewById<View>(R.id.btnResetPassword)
        resetPasswordButton.setOnClickListener {
            val intent = Intent(activity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}
