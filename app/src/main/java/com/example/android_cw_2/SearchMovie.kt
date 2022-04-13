package com.example.android_cw_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchMovie : AppCompatActivity() {
    lateinit var txtRetrived:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        txtRetrived=findViewById<TextView>(R.id.txtRetrived)
        var btnSearch=findViewById<Button>(R.id.btnRetreive)
        var btnSave=findViewById<Button>(R.id.btnSaveMovie)
        var editMovieSearch=findViewById<EditText>(R.id.editTextSearchMovie)

        var name="doom"

        var url_string= "https://www.omdbapi.com/?t=$name&apikey=56835c20"
        // collecting all the JSON string
        var stb = StringBuilder()
        val url = URL(url_string)
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection


        runBlocking {
            launch {
// run the code of the coroutine in a new thread

                withContext(Dispatchers.IO) {
                    var bf = BufferedReader(InputStreamReader(con.inputStream))
                    var line: String? = bf.readLine()
                    while (line != null) {
                        stb.append(line + "\n")
                        line = bf.readLine()
                    }
                    parseJSON(stb)
                }
            }
        }
//        btnSearch.setOnClickListener(){
//            name=editMovieSearch.text.toString()
//        }



    }
    suspend fun parseJSON(stb: java.lang.StringBuilder) {
// this contains the full JSON returned by the Web Service
        val json = JSONObject(stb.toString())
        var movieDetails = java.lang.StringBuilder()
        movieDetails.append("\nTitle: "+json.getString("Title"))
        movieDetails.append("\nYear: "+json.getString("Year"))
        movieDetails.append("\nRated: "+json.getString("Rated"))
        movieDetails.append("\nReleased: "+json.getString("Released"))
        movieDetails.append("\nRuntime: "+json.getString("Runtime"))
        movieDetails.append("\nGenre: "+json.getString("Genre"))
        movieDetails.append("\nDirector: "+json.getString("Director"))
        movieDetails.append("\nWriter: "+json.getString("Writer"))
        movieDetails.append("\nActors: "+json.getString("Actors"))
       txtRetrived.setText(movieDetails)
    }
}