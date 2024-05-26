package com.github.valeriaharumi.challenge.data.model

data class User(
    val uid: String = "",
    val email: String = "",
    var name: String = "",
    var userDetails: UserDetails = UserDetails()
){
    constructor() : this("")
}

data class UserDetails(
    val education: String = "",
    val institution: String = "",
    val company: String = "",
    val position: String = "",
    val bio: String = "",
    val interests: List<String> = emptyList(),
    val linkedin: String = "",
    val github: String = "",
    val lattes: String = ""
) {
    constructor() : this("")
}