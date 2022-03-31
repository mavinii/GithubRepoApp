package com.josue.githubrepoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException


// APP IS USING GITHUB API
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //starting fun to get data from API
        fetchData()

    }//finish fun onCreate

    //fun to get data from API
    private fun fetchData() {
        //start okHttpClient
        val client = OkHttpClient()
        val request = Request.Builder().url("https://api.github.com/users/Torvalds").build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error","Exception $e")
            }
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
                    findViewById<TextView>(R.id.userName).text = gitHubData.login

                    }
                }
            }
        })//finish okHttpClient

    }//finish fun fetchData


}//finish Class MainActivity