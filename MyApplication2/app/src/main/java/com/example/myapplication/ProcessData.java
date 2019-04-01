package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;


import com.example.myapplication.DAO.Datadao;
import com.example.myapplication.DTO.Datadto;
import com.example.myapplication.SQL.Database;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProcessData extends AsyncTask<Void, Integer, Void> {

    String url = "";
    private Database database;
    private Datadao dao;
    Context context;
    MainActivity create;
    ArrayList<String> link;
    int chianho;
    int temp = 0;
    int tinhphantram = 0;
    Intent screen2;
    ProgressDialog progressDialog;

    public ProcessData(Context context, ArrayList<String> link) {
        this.context = context;
        this.link = link;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Process is running");
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    @Override
    protected Void doInBackground(Void... voids) {
        database = Database.getInstance(context);
        dao = database.dao();

        screen2 = new Intent(context, main2.class);
        Bundle goithongtin = new Bundle();
        ArrayList<String> databundle = new ArrayList();
        chianho = 90 / link.size();
        for (int i = 0; i < link.size(); i++) {
            url = link.get(i);
            tinhphantram += chianho;
            Datadto sqllite = new Datadto();

            try {
                if (filterdata(url) != "") {
                    sqllite.setId(filterdata(url));
                    sqllite.setLink(url);
                    dao.insert(sqllite);
                    create.copyFileOrDirectory(url, "/sdcard/official-data"); //copy cai file co noi dung trong the hop le

                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (filterdata(url) == "") {
                    databundle.add("Khong tim thay");
                } else {
                    databundle.add(filterdata(url));
                }
            } catch (XmlPullParserException e1) {
                e1.printStackTrace();
                databundle.add("File XML loi");
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            for (int j = 1; j <= chianho; j++) {
                temp = temp +1;
                SystemClock.sleep(20);

                publishProgress(temp);

            }
        }
        create.copyFileOrDirectory("/data/data/com.example.myapplication/databases", "/sdcard/official-data");
        goithongtin.putSerializable("value", databundle);
        screen2.putExtra("goithongtin", goithongtin);
        for (int i = 90; i < 101; i++) {
            SystemClock.sleep(2);

            publishProgress(i);
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
        for (int i = 1; i <= link.size(); i++) {

            if (values[0] == chianho * i) {
                progressDialog.setMessage("Done file " + i);

            }
            if (values[0] == 100) {
                Toast.makeText(context, "Done copy database to foder", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();

          context.startActivity(screen2);

    }

    public String filterdata(String url) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        InputStream file = new FileInputStream(new File(url));
        parser.setInput(file, null);
        int event = parser.getEventType();
        String res = "";
        String text = "";
        while (event != XmlPullParser.END_DOCUMENT) {
            String nametag = parser.getName();
            switch (event) {
                case XmlPullParser.START_TAG:
                    break;
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if (nametag.equals("instanceID")) {
                        res = text;
                    }
                    break;
            }
            event = parser.next();
        }
        return res;
    }


}
