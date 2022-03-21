package com.example.recomovie.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.recomovie.model.movie.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("select * from Movie")
    List<Movie> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Movie... movies);

    @Delete
    void delete(Movie movie);

}
