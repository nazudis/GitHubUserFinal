package com.fauzan.githubuserfinal.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fauzan.githubuserfinal.R
import com.fauzan.githubuserfinal.holder.FavoriteHolder
import com.fauzan.githubuserfinal.model.UserList
import com.fauzan.githubuserfinal.ui.DetailUserActivity

class FavoriteUserAdapter: RecyclerView.Adapter<FavoriteHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallbak

    fun setOnItemClickCallback(onItemClickCallbak: OnItemClickCallbak) {
        this.onItemClickCallback = onItemClickCallbak
    }

    var listFavUser = ArrayList<UserList>()
        set(listFavUser) {
            if (listFavUser.size >= 0) {
                this.listFavUser.clear()
            }
            this.listFavUser.addAll(listFavUser)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_favorite_user, parent, false)
        return FavoriteHolder(view)
    }

    override fun getItemCount(): Int = this.listFavUser.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val list = listFavUser[position]

        holder.bind(list)

        holder.itemView.setOnClickListener { view ->
            onItemClickCallback.onItemClicked(list)
            val moveToDetailUser = Intent(view.context.applicationContext, DetailUserActivity::class.java)
            moveToDetailUser.putExtra(DetailUserActivity.EXTRA_LOGIN, list)
            view.context.startActivity(moveToDetailUser)
        }
    }

    interface OnItemClickCallbak {
        fun onItemClicked(data: UserList)
    }
}