package org.company.app.domain.model

data class AppUser(
    val email: String,
    val password: String,
    val name: String,
    val registrationDate: String,
//    val lastLoginDate: String? = "",
)
