package com.fauzan.favoriteuserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserList(
    var id: Int? = 0,
    var login: String? = "",
    var avatar: String? = ""
): Parcelable
