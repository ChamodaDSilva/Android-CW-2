package com.example.android_cw_2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface MovieDao {
    @Query("Select * from movie")
    suspend fun getAll(): List<Movie>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg movie: Movie)
    @Insert
    suspend fun insertAll(vararg movie: Movie)
}