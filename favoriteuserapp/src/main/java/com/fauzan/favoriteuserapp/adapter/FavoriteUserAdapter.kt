package com.fauzan.favoriteuserapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fauzan.favoriteuserapp.R
import com.fauzan.favoriteuserapp.model.UserList
import kotlinx.android.synthetic.main.list_favorite_user.view.*

class FavoriteUserAdapter: RecyclerView.Adapter<FavoriteHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
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

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(list)
        }

        holder.itemView.bt_delete.setOnClickListener {
            onItemClickCallback.onDeleteClick(list)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserList)
        fun onDeleteClick(data: UserList)
    }
}