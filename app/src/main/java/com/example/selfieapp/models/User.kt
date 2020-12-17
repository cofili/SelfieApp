package com.example.selfieapp.models

data class User(
    var email: String? = null,
    var password: String? = null
){
    companion object{
        const val COLLECTION_NAME = "users"
    }
}