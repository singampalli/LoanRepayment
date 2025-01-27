package com.capstone.loanrepayment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
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

        val chooseAmount=findViewById<EditText>(R.id.chooseAmount)
        val choose=findViewById<RadioButton>(R.id.chooseOption)

        lifecycleScope.launch {
            lateinit var viewFragment: Fragment
            val job: Job = launch {
                viewFragment = DetailFragment.newInstance(id, false)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.payDetails, viewFragment)
//                    .addToBackStack(null)
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
                chooseAmount.isEnabled=false
                chooseAmount.setText("")
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
            chooseAmount.setText("")
            chooseAmount.isEnabled=false

            maxAmount.text= getString(R.string.outstanding_amount,minPrincipal.toString())

            maxAmount.setOnClickListener {
                chooseAmount.isEnabled=false
                chooseAmount.setText("")
                setValueShow(minPrincipal.toString())
            }


            if(minn>=maxx){
                chooseAmount.visibility= View.GONE
                minAmount.visibility=View.GONE
                choose.visibility=View.GONE
            }
        }




        choose.setOnClickListener{
            chooseAmount.isEnabled=true
            chooseAmount.setText("")

        }

        val pay=findViewById<Button>(R.id.clickableBoxPay)
        pay.setOnClickListener{
            var sendAmount="${findViewById<TextView>(R.id.totalAmountShow).text.toString()}"
            val data=chooseAmount.text.toString()
            if(data!="") sendAmount=chooseAmount.text.toString()
            intent.putExtra("id",id)
            intent.putExtra("amount", sendAmount)
            startActivity(intent)
        }
    }
    private fun setValueShow(value:String){

        val show=findViewById<TextView>(R.id.totalAmountShow)
        show.text=value
    }
}