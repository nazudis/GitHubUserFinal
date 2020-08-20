package com.fauzan.githubuserfinal.db

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.fauzan.githubuserfinal"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val USER_ID = "user_id"
            const val USERNAME = "username"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

    fun getColumnInt(cursor: Cursor?, columnName: String?): Int {
        return cursor?.getInt(cursor.getColumnIndex(columnName)) ?: 0
    }

    fun getColumnString(cursor: Cursor?, columnName: String?): String {
        return cursor?.getString(cursor.getColumnIndex(columnName)) ?: ""
    }
}