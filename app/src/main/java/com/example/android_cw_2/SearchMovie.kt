package com.example.android_cw_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchMovie : AppCompatActivity() {
    lateinit var txtRetrived: TextView

    lateinit var title:String
    lateinit var year:String
    lateinit var rated:String
    lateinit var released:String
    lateinit var runtime:String
    lateinit var genre:String
    lateinit var director:String
    lateinit var writer:String
    lateinit var actors:String
    lateinit var plot:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        txtRetrived = findViewById(R.id.txtRetrived)
        var btnSearch = findViewById<Button>(R.id.btnRetreive)
        var btnSave = findViewById<Button>(R.id.btnSaveMovie)

        if(savedInstanceState!=null){
            txtRetrived.text=savedInstanceState.getString("txtRetrived")
            title= savedInstanceState.getString("title").toString()
            year= savedInstanceState.getString("year").toString()
            rated=savedInstanceState.getString("rated").toString()
            released=savedInstanceState.getString("released").toString()
            runtime=savedInstanceState.getString("runtime").toString()
            genre=savedInstanceState.getString("genre").toString()
            director= savedInstanceState.getString("director").toString()
            actors= savedInstanceState.getString("actors").toString()
            writer= savedInstanceState.getString("writer").toString()
            plot= savedInstanceState.getString("plot").toString()
        }

        btnSearch?.setOnClickListener {
            getMovie()
        }
        btnSave.setOnClickListener{
            addMovieToDatabase()
        }


    }
    fun addMovieToDatabase(){
        val db = Room.databaseBuilder(this, AppDatabase::class.java,
            "mydatabase").build()
        val movieDao = db.movieDao()
        runBlocking {
            launch {
                val movies: List<Movie> = movieDao.getAll()//to initialize the pk
                val movie= Movie(movies.size+1,title,
                    year,
                    rated,
                    released,
                    runtime,
                    genre,
                    director,
                    writer,
                    actors,
                    plot)

                movieDao.insertUsers(movie)//should be check here code
                var alertBonus= Toast.makeText(applicationContext,"Added to the DB!",
                    Toast.LENGTH_LONG)
                alertBonus.show()
            }
        }
    }

    fun getMovie() {
        var editMovieSearch = findViewById<EditText>(R.id.editTextSearchMovie)
        val movieName = editMovieSearch!!.text.toString().trim()
        if (movieName == "") {
            return
        }
        var url_string ="https://www.omdbapi.com/?t=$movieName&apikey=56835c20"
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
            txtRetrived?.setText(data)
        }
    }

    // extracts the relevant info from the JSON returned by the Web Service
    fun parseJSON(stb: StringBuilder): String {
        // extract the actual data
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
        movieDetails.append("\nPlot: "+json.getString("Plot"))

        //adding data to variables
        title=json.getString("Title")
        year=json.getString("Year")
        rated=json.getString("Rated")
        released=json.getString("Released")
        runtime=json.getString("Runtime")
        genre=json.getString("Genre")
        director=json.getString("Director")
        writer=json.getString("Writer")
        actors=json.getString("Actors")
        plot=json.getString("Plot")

        return movieDetails.toString()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        /**
         * to save values when pause the program
         */
        super.onSaveInstanceState(outState)
        outState.putString("txtRetrived",txtRetrived.text.toString())
        outState.putString("title",title)
        outState.putString("year",year)
        outState.putString("rated",rated)
        outState.putString("released",released)
        outState.putString("runtime",runtime)
        outState.putString("genre",genre)
        outState.putString("director",director)
        outState.putString("writer",writer)
        outState.putString("actors",actors)
        outState.putString("plot",plot)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        /**
         * to load values when resume the program
         */
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString("txtRetrived")
        savedInstanceState.getString("title")
        savedInstanceState.getString("year")
        savedInstanceState.getString("rated")
        savedInstanceState.getString("released")
        savedInstanceState.getString("runtime")
        savedInstanceState.getString("genre")
        savedInstanceState.getString("director")
        savedInstanceState.getString("writer")
        savedInstanceState.getString("actors")
        savedInstanceState.getString("plot")


    }
}