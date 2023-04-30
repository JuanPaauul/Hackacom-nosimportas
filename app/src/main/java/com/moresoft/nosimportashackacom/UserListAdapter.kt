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

class UserListAdapter(private val items: ArrayList<ConfidenceUser>, val context: ConfidenceUsersFragment): RecyclerView.Adapter<UserListAdapter.UserListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_confidence, parent, false)
        return UserListViewHolder(v)
    }
    override fun getItemCount(): Int {
        return items.count()
    }
    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val item = items[position]
        val nameTextView = holder.itemView.findViewById<TextView>(R.id.tv_confidence_name)
        nameTextView?.text = item.name
        holder.itemView.findViewById<TextView>(R.id.tv_confidence_lastname)?.text = item.lastName
        holder.itemView.findViewById<TextView>(R.id.tv_confidence_email)?.text = item.email

        val imageView = holder.itemView.findViewById<ImageView>(R.id.iv_confidence_image)

        if (imageView != null) {
            Picasso.get()
                .load("https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8dXNlcnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60")
                .error(R.mipmap.ic_launcher_round)
                .into(imageView)
        }

    }
    class UserListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}

