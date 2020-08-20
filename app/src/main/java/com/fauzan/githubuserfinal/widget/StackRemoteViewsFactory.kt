package com.fauzan.githubuserfinal.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.fauzan.githubuserfinal.R
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.fauzan.githubuserfinal.helper.FavoriteItem
import java.util.concurrent.ExecutionException

class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private var listFavoriteUser: Cursor? = null

    override fun onCreate() {
        listFavoriteUser = mContext.contentResolver.query(CONTENT_URI, null, null, null, null)
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(p0: Int): Long = 0

    override fun onDataSetChanged() {
        if (listFavoriteUser != null) listFavoriteUser?.close()

        val identityToken = Binder.clearCallingIdentity()
        listFavoriteUser = mContext.contentResolver.query(CONTENT_URI, null, null, null, null)
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val item: FavoriteItem = getItem(position)
        val remoteViews = RemoteViews(mContext.packageName, R.layout.widget_item)

        var bitmap: Bitmap? = null
        try {
            bitmap = Glide.with(mContext)
                .asBitmap()
                .load(item.avatar)
                .submit()
                .get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

        remoteViews.setImageViewBitmap(R.id.image_widget, bitmap)
        val extras = Bundle()

        //Get Username
        extras.putString(FavoriteUserWidget.EXTRA_ITEM, item.username)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        remoteViews.setOnClickFillInIntent(R.id.image_widget, fillInIntent)

        return remoteViews

    }

    override fun getCount(): Int {
        return listFavoriteUser?.count ?: 0
    }

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() { }

    private fun getItem(position: Int): FavoriteItem {
        listFavoriteUser?.moveToPosition(position)?.let { check(it) { "Invalid Position" } }
        return FavoriteItem(listFavoriteUser)
    }


}