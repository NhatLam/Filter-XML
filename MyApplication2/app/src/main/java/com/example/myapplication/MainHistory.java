package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainHistory extends AppCompatActivity {


    Adapterhistory adapterhis;

    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        RecyclerView recyhis = findViewById(R.id.recyhistory);
        recyhis.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyhis.setLayoutManager(layoutManager2);
        Intent nhanthongtin= getIntent();
        Bundle mogoithongtin = nhanthongtin.getBundleExtra("goithongtin");
        ArrayList<String> thumbs = (ArrayList<String>) mogoithongtin.getSerializable("value");
        //link


        Bundle mogoithongtin2 = nhanthongtin.getBundleExtra("goithongtin2");
        ArrayList<String> urls = (ArrayList<String>) mogoithongtin2.getSerializable("value2");
//        for(int i =0;i<urls.size();i++){
//
//        }
        adapterhis = new Adapterhistory(thumbs,getApplicationContext(),urls);
        recyhis.setAdapter(adapterhis);



    }


}
