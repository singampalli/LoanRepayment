package com.capstone.loanrepayment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.loanrepayment.databinding.ActivityMainBinding
import com.capstone.loanrepayment.util.TokenManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnItemSelectedListener  { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Toast.makeText(this,"Item 1 Selected",Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.navigation_dashboard -> {
                    // Handle navigation to dashboard
                    Toast.makeText(this," Item 2 Selected",Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.navigation_notifications -> {
                    // Handle navigation to notifications
                    Toast.makeText(this,"Item 3 Selected",Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }

        }
            val token = TokenManager.getToken(this)

        binding.logout.setOnClickListener {
            // Clear the token using TokenManager
            TokenManager.clearToken(this)

            // Redirect to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
//        val username=intent.getStringExtra("username")
        //saved username on success ful login
        val username=TokenManager.getUserName(this);
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
