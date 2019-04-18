package com.kukroid.smartshows.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kukroid.smartshows._Model.MovieDao;
import com.kukroid.smartshows._Model.Movies;

@Database(entities = {Movies.class} ,version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase moviesDatabase;
    private static String DATABASE_NAME = "MOVIE_DATABASE";

    public static MoviesDatabase getMoviesDatabase(Context context){
        if (moviesDatabase == null){
            synchronized (new Object()){
                moviesDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDatabase.class,
                        DATABASE_NAME).build();
            }
        }
        return moviesDatabase;
    }

    public abstract MovieDao getMovies();

}
