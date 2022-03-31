package com.josue.githubrepoapp

import android.icu.text.NumberFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException


// APP IS USING GITHUB API
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //starting fun to get user data from API
        fetchUserData()

        //staring fun to get user repos data from API
        fetchUserReposData()

    }//finish fun onCreate

    //start fun to get user data from API
    private fun fetchUserData() {
        //start okHttpClient
        val client = OkHttpClient()
        val request = Request.Builder().url("https://api.github.com/users/Torvalds").build()
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
    private fun fetchUserReposData() {
    }


}//finish Class MainActivity