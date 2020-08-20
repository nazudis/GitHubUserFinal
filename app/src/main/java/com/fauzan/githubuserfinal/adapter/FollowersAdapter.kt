package com.fauzan.githubuserfinal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fauzan.githubuserfinal.R
import com.fauzan.githubuserfinal.holder.FollowersHolder
import com.fauzan.githubuserfinal.model.UserList
import com.fauzan.githubuserfinal.ui.DetailUserActivity

class FollowersAdapter : RecyclerView.Adapter<FollowersHolder>() {

    private val listFollowers = ArrayList<UserList>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(item: ArrayList<UserList>) {
        listFollowers.clear()
        listFollowers.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_followers, parent, false)
        return FollowersHolder(view)
    }

    override fun getItemCount(): Int = listFollowers.size

    override fun onBindViewHolder(holder: FollowersHolder, position: Int) {
        val list = listFollowers[position]

        holder.bind(list)

        holder.itemView.setOnClickListener { view ->
            onItemClickCallback.onItemClicked(list)
            val moveToDetailUser =
                Intent(view.context.applicationContext, DetailUserActivity::class.java)
            moveToDetailUser.putExtra(DetailUserActivity.EXTRA_LOGIN, list)
            view.context.startActivity(moveToDetailUser)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserList)
    }
}