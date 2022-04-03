package com.josue.githubrepoapp


data class UserGitData(
   val login: String,
   val avatar_url: String,
   val name: String,
   val company: String,
   val location: String,
   val followers: Int,
   val following: Int,
)
