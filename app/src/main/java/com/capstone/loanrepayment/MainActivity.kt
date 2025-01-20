package com.capstone.loanrepayment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.loanrepayment.databinding.ActivityMainBinding
import com.capstone.loanrepayment.util.TokenManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = TokenManager.getToken(this)

        binding.logout.setOnClickListener {
            // Clear the token using TokenManager
            TokenManager.clearToken(this)

            // Redirect to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
//        val username=intent.getStringExtra("username")
        val username="username1"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentSpace, LoanFragment(username!!))
            .commit()
    }


    override fun onResume() {
        super.onResume()
        // Check if the token exists on resume
        val token = TokenManager.getToken(this)
        if (token == null) {
            // Token does not exist, redirect to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
