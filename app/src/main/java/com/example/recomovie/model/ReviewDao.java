package com.example.recomovie.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReviewDao {

    @Query("select * from Review")
    List<Review> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Review... students);

    @Delete
    void delete(Review student);

}
