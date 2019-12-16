package com.razzdrawon.urbandictionary.model

data class Definition(
    val defid: Long,
    val word: String,
    val definition: String,
    val example: String,
    val author: String,
    val thumbs_up: Int,
    val thumbs_down: Int,
    val current_vote: String
)