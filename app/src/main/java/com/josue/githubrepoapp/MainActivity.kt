package com.josue.githubrepoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*


// APP IS USING GITHUB API
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }//finish fun onCreate


    fun onRadioButtonClicked(view: View) {
        val userInput = userSearch.text.toString()
            if (view is RadioButton) {
                // Is the button now checked?
                val checked = view.isChecked
                // Check which radio button was clicked
                when (view.getId()) {
                    R.id.radioUsers ->
                        if (checked) {
                            val button = findViewById<RadioButton>(R.id.radioUsers)
                            button.setOnClickListener(View.OnClickListener() {
                                val intent = Intent(this@MainActivity, ApiUser::class.java)
                                intent.putExtra("UserName", userInput )
                                startActivity(intent)})
                        }
                    R.id.radioOrgs ->
                        if (checked) {
                            val buttonSecond = findViewById<RadioButton>(R.id.radioOrgs)
                            buttonSecond.setOnClickListener(View.OnClickListener() {
                                val intent = Intent(this@MainActivity, ApiOrgs::class.java)
                                intent.putExtra("UserName", userInput )
                                startActivity(intent)})
                        }
                }
            }
        }

}//finish Class MainActivity

