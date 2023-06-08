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
import java.math.BigInteger
import java.security.MessageDigest

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
        holder.itemView.findViewById<TextView>(R.id.tv_confidence_name)?.text = "Test name"
        holder.itemView.findViewById<TextView>(R.id.tv_confidence_lastname)?.text = "Test last name"
        holder.itemView.findViewById<TextView>(R.id.tv_confidence_email)?.text = item.email

        val imageView = holder.itemView.findViewById<ImageView>(R.id.iv_confidence_image)
        if (imageView != null) {
            Picasso.get()
                .load("https://xsgames.co/randomusers/avatar.php?g=male")
                .error(R.mipmap.ic_launcher_round)
                .into(imageView)
        }
    }
    fun updateData(users: List<ConfidenceUser>) {
        items = users
        notifyDataSetChanged()
    }
    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    class UserListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}

