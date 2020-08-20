package com.fauzan.githubuserfinal.helper

import android.database.Cursor
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.USER_ID
import com.fauzan.githubuserfinal.db.DatabaseContract.getColumnInt
import com.fauzan.githubuserfinal.db.DatabaseContract.getColumnString

class FavoriteItem(cursor: Cursor?) {
    var id: Int? = 0
    var username: String? = null
    var avatar: String? = null


    init {
        id = getColumnInt(cursor, USER_ID)
        username = getColumnString(cursor, USERNAME)
        avatar = getColumnString(cursor, AVATAR_URL)
    }

    override fun toString(): String {
        return """FavoriteItem{id = '$id', username = '$username', avatar = '$avatar'}"""
    }
}