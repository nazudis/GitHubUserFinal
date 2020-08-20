package com.fauzan.favoriteuserapp.helper

import android.database.Cursor
import com.fauzan.favoriteuserapp.db.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.fauzan.favoriteuserapp.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.fauzan.favoriteuserapp.db.DatabaseContract.UserColumns.Companion.USER_ID
import com.fauzan.favoriteuserapp.model.UserList

object MappingHelper {

    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<UserList> {

        val userList = arrayListOf<UserList>()

        userCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(USER_ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val avatar = getString(getColumnIndexOrThrow(AVATAR_URL))
                userList.add(UserList(id = id, login = username, avatar = avatar))
            }
        }

        return userList
    }
}