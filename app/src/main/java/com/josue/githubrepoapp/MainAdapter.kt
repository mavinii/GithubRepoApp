package com.josue.githubrepoapp

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter (private val userRepoData: UserRepoGitData , private val context: Context): RecyclerView.Adapter<UserDataViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycle_template, parent, false)
        return UserDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserDataViewHolder, position: Int) {
        val data = userRepoData[position]
        holder.itemView.findViewById<TextView>(R.id.repoName).text = data.name
        holder.itemView.findViewById<TextView>(R.id.repoDescription).text = data.description
        //holder.itemView.visibility = if (data.private) View.VISIBLE else View.GONE
        holder.itemView.findViewById<TextView>(R.id.repoLanguage).text = data.language
        holder.itemView.findViewById<TextView>(R.id.repoStared).text = data.forks_count.toString()
        holder.itemView.findViewById<TextView>(R.id.repoFork).text = data.stargazers_count.toString()
        holder.itemView

    }

    override fun getItemCount(): Int {
       return userRepoData.size
    }


}


class UserDataViewHolder (val v: View): RecyclerView.ViewHolder(v)
