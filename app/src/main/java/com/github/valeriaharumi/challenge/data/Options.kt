package com.github.valeriaharumi.challenge.data

data class Options(
    val educationOptions: List<String> = listOf(
        "Ensino Médio",
        "Ensino Superior",
        "Pós-graduação",
        "Mestrado",
        "Doutorado"
    )
)