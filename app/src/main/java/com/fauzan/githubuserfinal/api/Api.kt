package com.fauzan.githubuserfinal.api

import com.fauzan.githubuserfinal.BuildConfig
import com.fauzan.githubuserfinal.model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getUser(@Query("q") login: String): Call<ItemResponse>

    @GET("users/{user}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getDetailUser(@Path("user") user: String?): Call<UserList>

    @GET("users/{user}/followers")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowers(@Path("user") followers: String?): Call<ArrayList<UserList>>

    @GET("users/{user}/following")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowing(@Path("user") followers: String?): Call<ArrayList<UserList>>
}