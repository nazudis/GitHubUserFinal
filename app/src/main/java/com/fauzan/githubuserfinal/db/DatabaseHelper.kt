package com.fauzan.githubuserfinal.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.USER_ID

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbfavuser"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                "($USER_ID INT UNIQUE," +
                " $USERNAME TEXT," +
                " $AVATAR_URL TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}