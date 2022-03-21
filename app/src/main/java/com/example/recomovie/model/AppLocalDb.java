package com.example.recomovie.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.recomovie.RecomovieApplication;
import com.example.recomovie.model.movie.Movie;


@Database(entities = {Movie.class}, version = 8)
@TypeConverters({Converters.class})
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract MovieDao movieDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(RecomovieApplication.getContext(),
                    AppLocalDbRepository.class,
                    "recomovie.db")
                    .fallbackToDestructiveMigration()
                    .build();
}

