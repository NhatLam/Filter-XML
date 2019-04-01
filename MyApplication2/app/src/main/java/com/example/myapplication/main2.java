package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


public class main2 extends AppCompatActivity {
    Adapter2 adapter2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //recycler
        RecyclerView recy1 = findViewById(R.id.recyketqua);
        recy1.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recy1.setLayoutManager(layoutManager);


        //end
        //nhan thong tin
        Intent screen2 = getIntent();
        Bundle mogoithongtin = screen2.getBundleExtra("goithongtin");
        //end
        //mo ket noi database

        ArrayList<String> thumbs = (ArrayList<String>) mogoithongtin.getSerializable("value");


        adapter2 = new Adapter2(thumbs,getApplicationContext());

        recy1.setAdapter(adapter2);


    }
}
