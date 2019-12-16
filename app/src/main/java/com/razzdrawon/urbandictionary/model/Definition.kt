package com.razzdrawon.urbandictionary.model

data class Definition (val defid: Long,
                       val word: String,
                       val definition: String,
                       val example: String,
                       val author: String,
                       val thumbs_up: Int,
                       val thumbs_down: Int,
                       val current_vote: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Definition

        if (definition != other.definition) return false
        if (thumbs_up != other.thumbs_up) return false
        if (author != other.author) return false
        if (word != other.word) return false
        if (defid != other.defid) return false
        if (current_vote != other.current_vote) return false
        if (example != other.example) return false
        if (thumbs_down != other.thumbs_down) return false

        return true
    }
}