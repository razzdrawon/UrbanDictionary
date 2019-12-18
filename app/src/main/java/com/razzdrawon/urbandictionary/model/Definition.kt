package com.razzdrawon.urbandictionary.model

import com.google.gson.annotations.SerializedName

data class Definition(
    @SerializedName("defid")
    val defId: Long,
    @SerializedName("word")
    val word: String,
    @SerializedName("definition")
    val definition: String,
    @SerializedName("example")
    val example: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("thumbs_up")
    val thumbsUp: Int,
    @SerializedName("thumbs_down")
    val thumbsDown: Int
)