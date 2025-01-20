package com.capstone.loanrepayment.models

data class LoanType(
    val data: List<Data>,
    val total: Int
)

data class Data(
    val id: Int,
    val loanAccountNumber: String,
    val loanType: String,
    val loanAmount: String,
    val loanEMI: String,
    val loanStartDate: String,
    val loanEndDate: String,
    val loanStatus: String,
    val username: String
)
