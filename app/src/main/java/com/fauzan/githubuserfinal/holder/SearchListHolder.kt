package com.fauzan.githubuserfinal.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fauzan.githubuserfinal.model.UserList
import kotlinx.android.synthetic.main.list_gituser.view.*

class SearchListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(model: UserList) {
        itemView.tv_login.text = model.login
        itemView.tv_avatar.loadImage(model.avatar)
    }

}
