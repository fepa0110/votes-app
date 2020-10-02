package com.example.votesapp.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SalaApiService {
    @GET("salas/user/{username}")
    fun getSalasByUser(@Path("username") username: String): Call<List<Sala>>

}