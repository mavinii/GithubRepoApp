package com.josue.githubrepoapp

data class UserRepoGitDataItem(
    val description: String,
    val forks_count: Int,
    val language: String,
    val name: String,
    val visibility: String,
    val stargazers_count: Int,
)