package com.josue.githubrepoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


private lateinit var newRecyclerView : RecyclerView
private lateinit var userGitData: Array<UserGitData>

// APP IS USING GITHUB API
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //start okHttpClient
        val client = OkHttpClient()
        val request = Request.Builder().url("https://api.github.com/users/Torvalds").build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error","Exception $e")
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val bodyString = response.body?.string()
                    val json = Gson()
                    userGitData = arrayOf(json.fromJson(bodyString, UserGitData::class.java))
                    //Log.i("info", "msg: $userGitData")
                }
            }
        })//finish okHttpClient

        userGitData = arrayOf()
        newRecyclerView = findViewById(R.id.recycleView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        var adapter = PostsAdapter(userGitData,this@MainActivity)
        newRecyclerView.adapter = adapter
    }//finish fun onCreate


}//finish Class MainActivity