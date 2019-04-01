package com.example.myapplication.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.myapplication.DTO.Datadto;

import java.util.List;

@Dao
public interface  Datadao {
    @Query("SELECT * FROM data")
    List<Datadto>  getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Datadto data);


}
