package com.example.android_cw_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchActor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_actor)

        var btnSearchActor=findViewById<Button>(R.id.btnSearchActor)

        btnSearchActor.setOnClickListener{
            addMovieToDatabase()
        }



    }

    fun addMovieToDatabase(){

        var editTextActor=findViewById<EditText>(R.id.editTextActor)
        var tvActorMovies=findViewById<TextView>(R.id.txtActorMovies)
        tvActorMovies.text=""

        var actorMovies= mutableListOf<Movie>()
        var inputActor=editTextActor.text.toString()
        val db = Room.databaseBuilder(this, AppDatabase::class.java,
            "mydatabase").build()
        val movieDao = db.movieDao()
        runBlocking {
            launch {
                val movies: List<Movie> = movieDao.getAll()
                for (movie in movies){
                    if(movie.Actors?.contains(inputActor,ignoreCase = true) == true){
                        actorMovies.add(movie)
                        tvActorMovies.append(movie.Title+"\n"+movie.Actors+"\n\n")
                    }
                }
                if(tvActorMovies.text.toString()==""){
                    var alertBonus= Toast.makeText(applicationContext,"Actor not in the list.",Toast.LENGTH_SHORT)
                    alertBonus.show()
                }

            }
        }
    }
}