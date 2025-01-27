package com.capstone.loanrepayment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.widget.Toolbar

import androidx.appcompat.app.AppCompatActivity
import com.capstone.loanrepayment.databinding.ActivityMainBinding
import com.capstone.loanrepayment.util.TokenManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var username:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnItemSelectedListener  { item ->
            when (item.itemId) {
                R.id.navigation_home -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentSpace, UserDetailsFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.navigation_dashboard -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentSpace, LoanFragment(username,"active"))
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.navigation_notifications -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentSpace, LoanFragment(username,"closed"))
                        .addToBackStack(null)
                        .commit()
                    true
                }

                else -> false
            }

        }
        username= TokenManager.getUserName(this).toString();
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentSpace, LoanFragment(username,"active"))
            .commit()
        navView.selectedItemId = R.id.navigation_dashboard
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return when (item.itemId) {
            R.id.action_logout -> {
                // Clear the token using TokenManager
                TokenManager.clearToken(this)

                // Redirect to LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
