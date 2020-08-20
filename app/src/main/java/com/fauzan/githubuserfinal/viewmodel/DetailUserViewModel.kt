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
import java.lang.Exception

class DetailUserViewModel: ViewModel() {

    val dataUser = MutableLiveData<UserList>()

    fun setDataUser(name: String?) {

        RetrofitClient.instance.getDetailUser(name).enqueue(object: Callback<UserList> {
            override fun onFailure(call: Call<UserList>, t: Throwable) {
                Log.d("onFailure: ", t.message.toString())
            }

            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                val detailUser = response.body()

                try {
                    if (detailUser != null) {
                        dataUser.postValue(detailUser)
                    }
                } catch (e: Exception) {
                    Log.d("Exception ", e.message.toString())
                }
            }

        })
    }

    fun getDataUser(): LiveData<UserList> {
        return dataUser
    }
}