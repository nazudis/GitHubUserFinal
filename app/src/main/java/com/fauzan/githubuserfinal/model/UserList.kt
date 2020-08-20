package com.fauzan.githubuserfinal.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserList(
    var id: Int? = 0,
    var login: String? = "",
    @SerializedName("avatar_url")
    var avatar: String? = "",

    var name: String? = "",
    var company: String? = "",
    var location: String? = "",

    @SerializedName("public_repos")
    var repo: Int? = 0,

    var followers: Int? = 0,
    var following: Int? = 0
): Parcelable
