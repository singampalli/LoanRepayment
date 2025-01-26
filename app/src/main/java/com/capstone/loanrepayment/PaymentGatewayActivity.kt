package com.capstone.loanrepayment

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.google.android.material.snackbar.Snackbar

class PaymentGatewayActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_gateway)
        val payableAmount=findViewById<TextView>(R.id.payableAmount)
        val amount=intent.getStringExtra("amount")
        payableAmount.text=amount.toString()


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
                payDialogDisplay(amount)
            }
        }

        findViewById<TextView>(R.id.phonePe).setOnClickListener {
//            Toast.makeText(this, "PhonePe selected", Toast.LENGTH_SHORT).show()
            Snackbar.make(it,"Redirecting...",Snackbar.LENGTH_SHORT).show()
            if (amount != null) {
                payDialogDisplay(amount)
            }
        }

        findViewById<TextView>(R.id.paytm).setOnClickListener {
//            Toast.makeText(this, "Paytm selected", Toast.LENGTH_SHORT).show()
            Snackbar.make(it,"Redirecting...",Snackbar.LENGTH_SHORT).show()
            if (amount != null) {
                payDialogDisplay(amount)
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
                payDialogDisplay(amount)
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
                payDialogDisplay(amount)
            }
        }

        findViewById<TextView>(R.id.walletOption2).setOnClickListener {
//            Toast.makeText(this, "Amazon Pay selected", Toast.LENGTH_SHORT).show()
            Snackbar.make(it,"Redirecting...",Snackbar.LENGTH_SHORT).show()
            if (amount != null) {
                payDialogDisplay(amount)
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
    private fun payDialogDisplay(amount:String){
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
                val dialogBuilderSuccess=AlertDialog.Builder(this)
                val dialogView=layoutInflater.inflate(R.layout.payment_sucess,null)
                val gif=dialogView.findViewById<ImageView>(R.id.gif)
                gif.load(R.drawable.success){
                    crossfade(true)
                }
                dialogBuilderSuccess.setView(dialogView)
                val dialogSuccess=dialogBuilderSuccess.create()
                dialogSuccess.setCancelable(true)
                dialogSuccess.show()
            }
//        }
    }

}