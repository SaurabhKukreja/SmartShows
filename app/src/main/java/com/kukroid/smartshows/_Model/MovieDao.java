package com.kukroid.smartshows._Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface MovieDao {

    @Insert
    void insertFavoriteMovie(Movies movies);

    @Delete
    void deleteFavoriteMovie(Movies movie);

    @Query("Select * from movies_list")
    LiveData<List<Movies>> getFavoritemovies();

    @Query("Select * from movies_list where id= :id")
    Movies getFavoriteMovieDetails(int id);

}
