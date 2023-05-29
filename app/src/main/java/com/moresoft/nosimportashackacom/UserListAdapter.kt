package com.moresoft.nosimportashackacom

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moresoft.domain.ConfidenceUser
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

class UserListAdapter(private var items: List<ConfidenceUser>, val context: ConfidenceUsersFragment): RecyclerView.Adapter<UserListAdapter.UserListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_confidence, parent, false)
        return UserListViewHolder(v)
    }
    override fun getItemCount(): Int {
        return items.count()
    }
    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.findViewById<TextView>(R.id.tv_confidence_name)?.text = item.first_name
        holder.itemView.findViewById<TextView>(R.id.tv_confidence_lastname)?.text = item.last_name
        holder.itemView.findViewById<TextView>(R.id.tv_confidence_email)?.text = item.email

        val imageView = holder.itemView.findViewById<ImageView>(R.id.iv_confidence_image)
        if (imageView != null) {
            Picasso.get()
                .load(item.avatar)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView)
        }
    }
    fun updateData(users: List<ConfidenceUser>) {
        items = users
        notifyDataSetChanged()
    }
    class UserListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}

