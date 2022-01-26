package com.fermat.dic2app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fermat.dic2app.models.Eleve;

import java.util.List;

//Adding annotation to our Dao class
@androidx.room.Dao
public interface Dao {

    //insertion
    @Insert
    void insert(Eleve model);

    //m-a-j
    @Update
    void update(Eleve model);

    //suppression
    @Delete
    void delete(Eleve model);

    //suppression totale
    @Query("DELETE FROM eleve_table")
    void deleteAllEleves();

    //recuperation de la liste
    @Query("SELECT * FROM eleve_table ORDER BY prenom ASC")
    LiveData<List<Eleve>> getAllEleves();

}
