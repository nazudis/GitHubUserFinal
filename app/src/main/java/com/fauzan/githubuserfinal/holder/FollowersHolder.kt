package com.fauzan.githubuserfinal.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fauzan.githubuserfinal.model.UserList
import kotlinx.android.synthetic.main.list_followers.view.*

class FollowersHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(model: UserList) {
        itemView.tv_login_followers.text = model.login
        itemView.tv_avatar_followers.loadImage(model.avatar)
    }

}
