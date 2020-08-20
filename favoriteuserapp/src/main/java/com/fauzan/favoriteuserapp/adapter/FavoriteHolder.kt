package com.fauzan.favoriteuserapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fauzan.favoriteuserapp.model.UserList
import kotlinx.android.synthetic.main.list_favorite_user.view.*

class FavoriteHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(model: UserList){
        with(itemView){
            tv_login_fav.text = model.login
            tv_avatar_fav.loadImage(model.avatar)

        }

    }
}
