package com.capstone.loanrepayment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.compose.material3.Snackbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.capstone.loanrepayment.api.ApiResponse
import com.capstone.loanrepayment.api.LoanHistory
import com.capstone.loanrepayment.api.LoanServiceApi
import com.capstone.loanrepayment.api.RetrofitClient
import com.capstone.loanrepayment.util.ToastUtil
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentGatewayActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_gateway)
        val payableAmount=findViewById<TextView>(R.id.payableAmount)
        val amount=intent.getStringExtra("amount")
        val id=intent.getIntExtra("id",0)
        payableAmount.text="Amount : ₹ ${amount.toString()}"


        // UPI Card Views
        val upiCard = findViewById<CardView>(R.id.upiCard)
        val upiOptions = findViewById<LinearLayout>(R.id.upiOptions)

        // Cards Payment
        val cardsCard = findViewById<CardView>(R.id.cardsCard)
        val cardDetails = findViewById<LinearLayout>(R.id.cardDetails)

        // Wallets
        val walletsCard = findViewById<CardView>(R.id.walletsCard)
        val walletOptions = findViewById<LinearLayout>(R.id.walletOptions)

        // Toggle UPI Options
        upiCard.setOnClickListener {
            toggleVisibility(upiOptions)
        }

        findViewById<TextView>(R.id.googlePay).setOnClickListener {
//            Toast.makeText(this, "Google Pay selected", Toast.LENGTH_SHORT).show()
            Snackbar.make(it,"Redirecting...",Snackbar.LENGTH_SHORT).show()
            if (amount != null) {
                payDialogDisplay(amount,id)
            }
        }

        findViewById<TextView>(R.id.phonePe).setOnClickListener {
//            Toast.makeText(this, "PhonePe selected", Toast.LENGTH_SHORT).show()
            Snackbar.make(it,"Redirecting...",Snackbar.LENGTH_SHORT).show()
            if (amount != null) {
                payDialogDisplay(amount,id)
            }
        }

        findViewById<TextView>(R.id.paytm).setOnClickListener {
//            Toast.makeText(this, "Paytm selected", Toast.LENGTH_SHORT).show()
            Snackbar.make(it,"Redirecting...",Snackbar.LENGTH_SHORT).show()
            if (amount != null) {
                payDialogDisplay(amount,id)
            }
        }

        // Toggle Card Details
        cardsCard.setOnClickListener {
            toggleVisibility(cardDetails)
        }

        findViewById<Button>(R.id.payButtonCard).setOnClickListener {
//            Toast.makeText(this, "Processing Card Payment", Toast.LENGTH_SHORT).show()
            Snackbar.make(it,"Processing Card Payment...",Snackbar.LENGTH_SHORT).show()
            if (amount != null) {
                payDialogDisplay(amount,id)
            }
        }

        // Toggle Wallet Options
        walletsCard.setOnClickListener {
            toggleVisibility(walletOptions)
        }

        findViewById<TextView>(R.id.walletOption1).setOnClickListener {
//            Toast.makeText(this, "Paytm Wallet selected", Toast.LENGTH_SHORT).show()
            Snackbar.make(it,"Redirecting...",Snackbar.LENGTH_SHORT).show()
            if (amount != null) {
                payDialogDisplay(amount,id)
            }
        }

        findViewById<TextView>(R.id.walletOption2).setOnClickListener {
//            Toast.makeText(this, "Amazon Pay selected", Toast.LENGTH_SHORT).show()
            Snackbar.make(it,"Redirecting...",Snackbar.LENGTH_SHORT).show()
            if (amount != null) {
                payDialogDisplay(amount,id)
            }
        }
    }
    private fun toggleVisibility(view: View) {
        if (view.visibility == View.GONE) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
    @SuppressLint("MissingInflatedId")
    private fun payDialogDisplay(amount:String,id:Int){
//        clickable.setOnClickListener{
        val dialogBuilder= AlertDialog.Builder(this)
        val dialogView=layoutInflater.inflate(R.layout.payment_dialog, null)
        val finalAmount=dialogView.findViewById<TextView>(R.id.finalPay)
        val pay=dialogView.findViewById<Button>(R.id.pay)
        finalAmount.text=amount
        dialogBuilder.setView(dialogView)
        val dialog=dialogBuilder.create()
        dialog.setCancelable(true)
        dialog.show()

        pay.setOnClickListener{
            dialog.dismiss()
//            val dialogBuilderSuccess=AlertDialog.Builder(this)
//            val dialogView=layoutInflater.inflate(R.layout.payment_sucess,null)
//            val gif=dialogView.findViewById<ImageView>(R.id.gif)
//            gif.load(R.drawable.success){
//                crossfade(true)
//            }
//            dialogBuilderSuccess.setView(dialogView)
//            val dialogSuccess=dialogBuilderSuccess.create()
//            dialogSuccess.setCancelable(true)
//            dialogSuccess.show()

            addHistory(id,amount.toFloat())
        }
//        }
    }

    private fun addHistory(id:Int,amount:Float){
        val api = RetrofitClient.instance.create(LoanServiceApi::class.java)
//        api.postHistory(LoanHistory(id,amount))
        api.postHistory(LoanHistory(id,amount)).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.body()?.status!!) {
                    ToastUtil.showSuccessToast(this@PaymentGatewayActivity, "Payment Added Successfully")
                    // Add a 5-second delay before redirecting to MainActivity
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(this@PaymentGatewayActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, 5000)
                } else {
                    ToastUtil.showErrorToast(this@PaymentGatewayActivity, "Failed: ${response.body()?.message}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                ToastUtil.showErrorToast(this@PaymentGatewayActivity, "Error: ${t.message}")
            }
        })
    }

}