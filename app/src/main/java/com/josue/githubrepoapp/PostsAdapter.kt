package com.josue.githubrepoapp

import android.content.Context
import android.content.Intent
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class PostsAdapter(private val userData: Array<UserGitData>, private val context: Context): RecyclerView.Adapter<UserDataViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.activity_main, parent, false)
        return UserDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserDataViewHolder, position: Int) {
        val data = userData[position]
        holder.itemView.findViewById<TextView>(R.id.userName).text = data.login
        val userImage = holder.itemView.findViewById<ImageView>(R.id.avatarImage)
        val imageUrl = data.avatar_url
            Picasso.get()
                .load(imageUrl)
                .placeholder(getDrawable(context,R.drawable.coffee)!!)
                .error(getDrawable(context,R.drawable.ic_baseline_error_24)!!)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(userImage)
        holder.itemView.findViewById<TextView>(R.id.userFullName).text = data.name
        holder.itemView.findViewById<TextView>(R.id.userWorkplace).text = data.company
        holder.itemView.findViewById<TextView>(R.id.userAddress).text = data.location
        holder.itemView.findViewById<TextView>(R.id.userFollows).text = data.followers.toString()+data.following.toString()
    }

    override fun getItemCount(): Int {
        return userData.size
    }


}

class UserDataViewHolder (val v: View): RecyclerView.ViewHolder(v)