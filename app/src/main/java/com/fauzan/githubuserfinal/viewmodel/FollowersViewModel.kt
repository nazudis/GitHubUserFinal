package com.fauzan.githubuserfinal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fauzan.githubuserfinal.model.UserList
import com.fauzan.githubuserfinal.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {

    private val TAG = FollowersViewModel::class.java.simpleName
    val listFollowers = MutableLiveData<ArrayList<UserList>>()
    val listFollowing = MutableLiveData<ArrayList<UserList>>()

    fun setFollowers(name: String?) {
        val list = ArrayList<UserList>()

        RetrofitClient.instance.getFollowers(name).enqueue(object : Callback<ArrayList<UserList>> {
            override fun onFailure(call: Call<ArrayList<UserList>>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<UserList>>,
                response: Response<ArrayList<UserList>>
            ) {
                try {
                    val listResponse = response.body()
                    listResponse?.let { list.addAll(it) }

                    listFollowers.postValue(listResponse)
                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                }
            }
        })
    }

    fun setFollowing(name: String?) {
        val list = ArrayList<UserList>()

        RetrofitClient.instance.getFollowing(name).enqueue(object : Callback<ArrayList<UserList>> {
            override fun onFailure(call: Call<ArrayList<UserList>>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<UserList>>,
                response: Response<ArrayList<UserList>>
            ) {
                try {
                    val listResponse = response.body()
                    listResponse?.let { list.addAll(it) }

                    listFollowing.postValue(listResponse)
                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                }
            }

        })
    }

    fun getFollowers(): LiveData<ArrayList<UserList>> {
        return listFollowers
    }

    fun getFollowing(): LiveData<ArrayList<UserList>> {
        return listFollowing
    }
}