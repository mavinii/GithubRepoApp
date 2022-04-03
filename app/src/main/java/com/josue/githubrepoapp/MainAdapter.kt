package com.josue.githubrepoapp

import android.content.Context
import android.icu.text.NumberFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainAdapter (private val userRepoData: UserRepoGitData , private val context: Context): RecyclerView.Adapter<UserDataViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycle_template, parent, false)
        return UserDataViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: UserDataViewHolder, position: Int) {
        val data = userRepoData[position]
        holder.itemView.findViewById<TextView>(R.id.repoName).text = data.name
        holder.itemView.findViewById<TextView>(R.id.repoDescription).text = data.description
                                                                                        //Format first letter to Upper case
        holder.itemView.findViewById<TextView>(R.id.repoVisible).text = data.visibility.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        holder.itemView.findViewById<TextView>(R.id.repoLanguage).text = data.language
        //formatted starts count
        val formatStarts = NumberFormat.getInstance().format(data.stargazers_count)
        holder.itemView.findViewById<TextView>(R.id.repoStars).text = formatStarts
        //formatted forks count
        val formatForks = NumberFormat.getInstance().format(data.forks_count)
        holder.itemView.findViewById<TextView>(R.id.repoForks).text = formatForks
    }

    override fun getItemCount(): Int {
       return userRepoData.size
    }


}


class UserDataViewHolder (val v: View): RecyclerView.ViewHolder(v)
