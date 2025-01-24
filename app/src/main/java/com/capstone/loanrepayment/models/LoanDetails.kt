package com.capstone.loanrepayment.models

import android.os.Parcelable
import androidx.lifecycle.LiveData
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlin.time.Duration

@Parcelize
data class Value(
    val date:String,
    val emiPaid:Float,
    val interestPaid:Float,
    val principalPaid:Float,
    val principalLeft:Float
):Parcelable

@Parcelize
data class LoanDetails(
    val id:Int,
    val loanAccountNumber: String,
    val loanType:String,
    val loanAmount:String,
    val loanEMI:Float,
    val loanStartDate:String,
    val loanEndDate: String,
    val loanStatus:String,
    val username: String,
    val loanHistory:List<Value>
):Parcelable
