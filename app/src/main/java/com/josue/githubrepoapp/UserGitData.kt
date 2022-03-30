package com.josue.githubrepoapp

import com.google.gson.annotations.SerializedName

data class UserGitData(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url")val avatar_url: String,
    @SerializedName("name")val name: String,
    @SerializedName("company")val company: String,
    @SerializedName("location")val location: String,
    @SerializedName("followers")val followers: Int,
    @SerializedName("following")val following: Int
)