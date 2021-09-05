package com.gsoft.interconsulta.data.models

data class Patient(
    val dni: String,
    val nombre: String,
    val laboratory: MutableList<String>? = null,
    val studies: MutableList<String>? = null,
    val notes: MutableList<String>? = null
) {
    constructor() : this(
        "", "", null,
        null, notes = null
    )
}