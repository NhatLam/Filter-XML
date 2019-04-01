package com.example.myapplication.SQL;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;




import com.example.myapplication.DAO.Datadao;
import com.example.myapplication.DTO.Datadto;

import static com.example.myapplication.SQL.Database.DATABASE_VERSION;


@android.arch.persistence.room.Database(entities = Datadto.class,version = DATABASE_VERSION)
public abstract  class Database extends RoomDatabase {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Room-database";

    public abstract Datadao dao();
    public static Database Instance;

    public static Database getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context, Database.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }
            return Instance;
    }

}
