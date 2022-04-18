package com.example.android_cw_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchMovieAdvanced : AppCompatActivity() {

    lateinit var txtMovieSearchAdvanced:TextView
    lateinit var editTextSearchMovieAdvanced:EditText
    lateinit var btnAdvancedTemp:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie_advanced)

        editTextSearchMovieAdvanced = findViewById<EditText>(R.id.editTextSearchMovieAdvanced)
        txtMovieSearchAdvanced = findViewById<TextView>(R.id.txtSearchMovieAdvanced)
        btnAdvancedTemp = findViewById(R.id.btnAdvancedSearchTemp)
        btnAdvancedTemp.setOnClickListener {
            if(editTextSearchMovieAdvanced.text.toString().length>2) {
                getMovie()
            }else{
                var alertBonus= Toast.makeText(applicationContext,"Enter at least 3 characters!",Toast.LENGTH_SHORT)
                alertBonus.show()
            }
        }
    }
    fun getMovie() {

        val movieName = editTextSearchMovieAdvanced!!.text.toString().trim()
        if (movieName == "") {
            return
        }
        var url_string ="https://www.omdbapi.com/?s=*${movieName}*&apikey=56835c20"
        var data: String = ""

        // start the fetching of data in the background
        runBlocking {
            withContext(Dispatchers.IO) {
                // this will contain the whole of JSON
                val stb = StringBuilder("")

                val url = URL(url_string)
                val con = url.openConnection() as HttpURLConnection
                val bf: BufferedReader
                try {
                    bf = BufferedReader(InputStreamReader(con.inputStream))
                } catch (e: IOException) {
                    e.printStackTrace()
                    return@withContext
                }

                var line = bf.readLine()
                while (line != null) {
                    stb.append(line)
                    line = bf.readLine()
                }

                // pick up all the data
                data = parseJSON(stb)
            }

            // display the data
            txtMovieSearchAdvanced?.setText(data)
        }
    }

    // extracts the relevant info from the JSON returned by the Web Service
    fun parseJSON(stb: StringBuilder): String {
        // extract the actual data
        val json = JSONObject(stb.toString())
        var jsonArray: JSONArray = json.getJSONArray("Search")

        var movieDetails = java.lang.StringBuilder()
        // extract all the books from the JSON array

        for (i in 0..jsonArray.length()-1) {
            var movie=jsonArray[i] as JSONObject
            movieDetails.append("\nMovie : "+movie.getString("Title"))

        }

        return movieDetails.toString()
    }
}