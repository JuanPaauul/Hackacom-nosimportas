package com.moresoft.domain

data class User (
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val city: String,
    val province: String,
    val confidenceUsers: List<ConfidenceUser>
)