package com.razzdrawon.urbandictionary.model

data class Definition (val defid: Number,
                       val word: String,
                       val definition: String,
                       val example: String,
                       val author: String,
                       val thumbs_up: Number,
                       val thumbs_down: Number,
                       val current_vote: String) {
}