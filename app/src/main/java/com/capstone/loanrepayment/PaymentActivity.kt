package com.capstone.loanrepayment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.capstone.loanrepayment.models.LoanDetails
import com.capstone.loanrepayment.util.ToastUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.chrono.ChronoLocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.min
import kotlin.properties.Delegates

class PaymentActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        lateinit var partialAmount: EditText
        lateinit var payPartialButton: Button

        val id = intent.getIntExtra("id", 0)
        val intent = Intent(this@PaymentActivity, PaymentGatewayActivity::class.java)
        var minn by Delegates.notNull<Double>()
        var maxx by Delegates.notNull<Double>()


        lifecycleScope.launch {
            lateinit var viewFragment: Fragment
            val job: Job = launch {
                viewFragment = DetailFragment.newInstance(id, false)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.payDetails, viewFragment)
                    .addToBackStack(null)
                    .commit()
            }
            job.join()

            val minAmount=findViewById<RadioButton>(R.id.minAmount)
            val maxAmount=findViewById<RadioButton>(R.id.maxAmount)
            val radioGroup=findViewById<RadioGroup>(R.id.paymentRadioGroup)


            val today = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formattedDate: ChronoLocalDate =LocalDate.parse( today.format(formatter),formatter)

            val user = viewFragment.arguments?.getParcelable<LoanDetails>("user")
            minAmount.text=getString(R.string.emi_amount, user?.loanEMI.toString())

            minn=user?.loanEMI.toString().toDouble()

            minAmount.setOnClickListener {

                setValueShow(user?.loanEMI.toString())
            }

            var minPrincipal: Float? =user?.loanAmount?.toFloat()
            val history=user?.loanHistory

            if (history != null) {
                for(data in history){
                    val prevDate = LocalDate.parse(data.date, formatter)
                    if(prevDate<=formattedDate){
                        minPrincipal= minPrincipal?.let { min(it,data.principalLeft) }
                    }
                }
            }

            maxx=minPrincipal.toString().toDouble()

            radioGroup.check(R.id.maxAmount)
            setValueShow(minPrincipal.toString())

            maxAmount.text= getString(R.string.outstanding_amount,minPrincipal.toString())

            maxAmount.setOnClickListener {
                setValueShow(minPrincipal.toString())
            }

        }

        val choose=findViewById<RadioButton>(R.id.chooseOption)
        choose.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this@PaymentActivity)
            val dialogView = layoutInflater.inflate(R.layout.dialog_box, null)

            dialogBuilder.setView(dialogView)
            val dialog = dialogBuilder.create()
            dialog.setCancelable(true)
            dialog.show()

            partialAmount = dialogView.findViewById(R.id.partialAmount)
            payPartialButton = dialogView.findViewById(R.id.payPartialButton)
            payPartialButton.setOnClickListener {
                if (partialAmount != null && (partialAmount.text.toString().toDouble() >= minn
                            && partialAmount.text.toString().toDouble() <= maxx)) {
                    setValueShow(partialAmount.text.toString())
                    dialog.dismiss()

                } else {
                    ToastUtil.showErrorToast(this@PaymentActivity, getString(R.string.valid_amount))
                }
            }
        }

        val pay=findViewById<Button>(R.id.clickableBoxPay)
        pay.setOnClickListener{
            val sendAmount="Amount : ₹ ${findViewById<TextView>(R.id.totalAmountShow).text.toString()}"
            intent.putExtra("amount", sendAmount)
            startActivity(intent)
        }

    }

    private fun setValueShow(value:String){
        val show=findViewById<TextView>(R.id.totalAmountShow)
        show.text=value
    }
}