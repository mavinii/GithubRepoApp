package com.josue.githubrepoapp

import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.github_user_template.*
import okhttp3.*
import java.io.IOException

class ApiOrgs : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.github_user_template)

        //getting data from first activity
        val userSearch = intent.getStringExtra("UserName")

        //starting layout manager
        recycleViewRepo.layoutManager = LinearLayoutManager(this)

        //starting API's
        if (userSearch != null) {
            //starting fun to get user data from API
            fetchUserData(userSearch)
            //staring fun to get user repos data from API
            fetchUserReposData(userSearch)
        }

    }



    //start fun to get user data from API
    private fun fetchUserData(userInput:String) {
        //start okHttpClient
        val client = OkHttpClient()
        val request = Request.Builder().url("https://api.github.com/orgs/$userInput").build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error","Exception $e")
            }
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    //getting the body from GITHUB API
                    val bodyString = response.body?.string()
                    //using Gson constructor
                    val gson = GsonBuilder().create()
                    //variable to store data using data class UserGitData where is all variables addressed
                    val gitHubData = gson.fromJson(bodyString, UserGitData::class.java)
                    //confirmed its working with println(GITHUB DATA)
                    //println(gitHubData)
                    //addressing to main thread
                    runOnUiThread{
                        //addressing all the info to the right spot in the layout
                        //userName
                        findViewById<TextView>(R.id.userName).text = gitHubData.login
                        //avatar image
                        val userImage = findViewById<ImageView>(R.id.avatarImage)
                        val imageUrl = gitHubData.avatar_url
                        Picasso.get()
                            .load(imageUrl)
                            .placeholder(getDrawable(R.drawable.coffee)!!)
                            .error(getDrawable(R.drawable.ic_baseline_error_24)!!)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(userImage)
                        //full name
                        findViewById<TextView>(R.id.userFullName).text = gitHubData.name
                        //follows
                        //format followers number
                        val formatFollowers = NumberFormat.getInstance().format(gitHubData.followers)
                        findViewById<TextView>(R.id.userFollowers).text = formatFollowers
                        //format following number
                        val formatFollowing = NumberFormat.getInstance().format(gitHubData.following)
                        findViewById<TextView>(R.id.userFollowing).text = formatFollowing
                        //company
                        findViewById<TextView>(R.id.userWorkplace).text = gitHubData.company
                        //location
                        findViewById<TextView>(R.id.userAddress).text = gitHubData.location

                    }
                }
            }
        })//finish okHttpClient
    }//finish fun fetchData

    //start fun to get user repos data from API
    private fun fetchUserReposData(userSearch: String) {
        val client = OkHttpClient()
        val request = Request.Builder().url("https://api.github.com/orgs/$userSearch/repos").build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", "Exception $e")
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    val bodyString = response.body?.string()
                    println(bodyString)
                    val gson = GsonBuilder().create()
                    val userRepoGitData = gson.fromJson(bodyString, UserRepoGitData::class.java)
                    runOnUiThread {
                        recycleViewRepo.adapter = MainAdapter(userRepoGitData, this@ApiOrgs)
                    }
                }
            }

        })
    }//End of fun fetchRepos

}//End of the ApiStored class