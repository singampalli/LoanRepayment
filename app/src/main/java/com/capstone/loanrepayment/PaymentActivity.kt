package com.capstone.loanrepayment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.capstone.loanrepayment.models.LoanDetails
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.w3c.dom.Text

class PaymentActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val partialPayment=findViewById<TextView>(R.id.clickableBoxPartial)
        val fullPayment=findViewById<TextView>(R.id.clickableBoxFull)
//        val payDetails=findViewById<FrameLayout>(R.id.payDetails)
        lateinit var partialAmount:EditText
        lateinit var payPartialButton:Button

        val id=intent.getIntExtra("id",0)
        val intent=Intent(this@PaymentActivity,PaymentGatewayActivity::class.java)



            lifecycleScope.launch {
                lateinit var viewFragment: Fragment
                val job:Job=launch {
                    viewFragment = DetailFragment.newInstance(id, false)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.payDetails, viewFragment)
                        .addToBackStack(null)
                        .commit()
                }
                job.join()
                val user = viewFragment.arguments?.getParcelable<LoanDetails>("user")
                fullPayment.setOnClickListener{
                    intent.putExtra(
                    "amount",
                    user?.loanEMI.toString()
                )
                startActivity(intent)
                }

            }

        partialPayment.setOnClickListener{
            val dialogBuilder=AlertDialog.Builder(this@PaymentActivity)
            val dialogView=layoutInflater.inflate(R.layout.dialog_box, null)

            dialogBuilder.setView(dialogView)
            val dialog=dialogBuilder.create()
            dialog.setCancelable(true)
            dialog.show()

            partialAmount=dialogView.findViewById(R.id.partialAmount)
            payPartialButton=dialogView.findViewById(R.id.payPartialButton)
            payPartialButton.setOnClickListener{
            if(partialAmount != null && partialAmount.text.toString().toDouble() > 0){
                intent.putExtra("amount",partialAmount.text.toString())
                startActivity(intent)
            }
            else{
                Toast.makeText(this@PaymentActivity,"Enter valid amount",Toast.LENGTH_SHORT).show()
            }
        }
        }

    }
}