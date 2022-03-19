package com.example.recomovie.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.recomovie.RecomovieApplication;


@Database(entities = {Review.class}, version = 8)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ReviewDao reviewDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(RecomovieApplication.getContext(),
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}

