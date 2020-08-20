package com.fauzan.githubuserfinal.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fauzan.githubuserfinal.model.UserList
import kotlinx.android.synthetic.main.list_favorite_user.view.*

class FavoriteHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(model: UserList){
        itemView.tv_login_fav.text = model.login
        itemView.tv_avatar_fav.loadImage(model.avatar)
    }

}
