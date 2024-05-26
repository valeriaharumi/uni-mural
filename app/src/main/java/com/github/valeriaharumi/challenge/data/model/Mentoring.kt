package com.github.valeriaharumi.challenge.data.model

data class Mentoring(
    val mentorId: String = "",
    val title: String = "",
    val description: String = "",
    val details: String = "",
    val area: String = "",
    val location: String = "",
    val modality: String = "",
    val vacancies: Int = 0,
    val matchMethod: String = "",
    val mentees: List<String> = listOf(),
    val interested: List<String> = listOf(),
    val notInterested: List<String> = listOf(),
    val duration: String = "",
    val frequency: String = "",
    val period: String = "",
    val registrationPeriod: String = ""
) {
    constructor() : this("")
}
