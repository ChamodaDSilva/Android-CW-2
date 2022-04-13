package com.example.android_cw_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnAddMovie=findViewById<Button>(R.id.btnAddMovieDb)
        var btnSearchMovie=findViewById<Button>(R.id.btnSeachMovies)
        var btnSearchActor=findViewById<Button>(R.id.btnSearchActors)

        btnAddMovie.setOnClickListener(){
            var addMovieWindow= Intent(this,AddMovie::class.java)
            startActivity(addMovieWindow)
        }
        btnSearchMovie.setOnClickListener(){
            var searchMovieWindow= Intent(this,SearchMovie::class.java)
            startActivity(searchMovieWindow)
        }
    }
}