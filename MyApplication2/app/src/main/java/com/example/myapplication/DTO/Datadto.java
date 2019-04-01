package com.example.myapplication.DTO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "data")

public class Datadto  {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")

    String id;

    @ColumnInfo(name = "url")
    String link;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Ignore

    public Datadto(@NonNull String id, String link) {
        this.id = id;
        this.link = link;
    }

    public Datadto() {

    }

}
