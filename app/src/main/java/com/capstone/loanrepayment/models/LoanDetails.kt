package com.capstone.loanrepayment.models

import android.os.Parcelable
import androidx.lifecycle.LiveData
import kotlinx.parcelize.Parcelize
import kotlin.time.Duration

@Parcelize
data class LoanDetails(
    val id:Int,
    val loanAccountNumber: String,
    val loanType:String,
    val loanAmount:String,
    val loanEMI:String,
    val loanStartDate:String,
    val loanEndDate: String,
    val loanStatus:String,
    val username: String
):Parcelable
