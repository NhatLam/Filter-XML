package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.DAO.Datadao;
import com.example.myapplication.DTO.Datadto;
import com.example.myapplication.SQL.Database;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements OnClickListener {


    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    Button btnread, btnhistory;
    File list[];
    Environment e = new Environment();
    String sd_card = Environment.getExternalStorageDirectory().toString() + "/data";
    File file = new File(sd_card);
    Adapter adapter;

    public static final String DATABASE_NAME = "Room-database";
    private Database database;
    private Datadao dao;
    ArrayList<File> listrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = file.listFiles();

        btnread = findViewById(R.id.btnread);
        btnhistory = findViewById(R.id.btnhistory);
        //tuong tac data
        database = Database.getInstance(this);
        dao = database.dao();

        askPermissionAndWriteFile();


        btnread.setOnClickListener(this);
        btnhistory.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView rv = findViewById(R.id.recyscreen1);
        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        ArrayList<File> arrayList = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            arrayList.add(list[i]);
        }
        adapter = new Adapter(arrayList, getApplicationContext());
        rv.setAdapter(adapter);
        adapter.clear();


    }


    public static void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);

                }
            } else {
                copyFile(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }


/*    public void officaldata() {
        String src = ("/data/data/com.example.myapplication/databases");
        String dec = ("/sdcard/official-data");

        copyFileOrDirectory(src, dec);

    }*/

    private void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (canWrite) {
            // createfoder();
            //of  officaldata();

        }

    }

//    private void askPermissionAndReadFile() {
//        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
//                Manifest.permission.READ_EXTERNAL_STORAGE);
//        if (canRead) {
//            list = file.listFiles();
//        }
//    }


    // Với Android Level >= 23 bạn phải hỏi người dùng cho phép các quyền với thiết bị
    // (Chẳng hạn đọc/ghi dữ liệu vào thiết bị).
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Kiểm tra quyền
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {

                // Nếu không có quyền, cần nhắc người dùng cho phép.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    // Khi yêu cầu hỏi người dùng được trả về (Chấp nhận hoặc không chấp nhận).
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //

        // Chú ý: Nếu yêu cầu bị hủy, mảng kết quả trả về là rỗng.
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_READ_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // list = file.listFiles();
                    }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }

    public void createfoder() {
        File foderdata = new File("/sdcard/official-data");
        if (!foderdata.exists()) {
            foderdata.mkdirs();
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnread) {
            ArrayList<String> link = new ArrayList();
            createfoder();
            //lay danh sach da chon tren checkbox
            listrs = adapter.getList();
            for (int i = 0; i < listrs.size(); i++) {

                link.add(listrs.get(i).getPath());

            }
            // ProcessData process=new ProcessData(this,link);
            ProcessData process = new ProcessData(this, link);

            process.execute();

        }

        if (v.getId() == R.id.btnhistory) {
            Intent screenhis = new Intent(MainActivity.this, MainHistory.class);
            Bundle goithongtin = new Bundle();
            Bundle goithongtin2 = new Bundle();

            List<Datadto> listkq = dao.getAll();        //create list database

            List<String> listhistory = new ArrayList();  //create list filter data
            List<String> listurl = new ArrayList();
            List<String> content = new ArrayList();
            for (int i = 0; i < listkq.size(); i++) {
                listhistory.add(listkq.get(i).getId());
                listurl.add(listkq.get(i).getLink());
                content.add(readData(listurl.get(i)));
            }

            goithongtin.putSerializable("value", (Serializable) listhistory);
            screenhis.putExtra("goithongtin", goithongtin);

            goithongtin2.putSerializable("value2", (Serializable) content);
            screenhis.putExtra("goithongtin2", goithongtin2);
            startActivity(screenhis);

        }

    }

    public String readData(String url) {
        String data = "";


        try {
            Scanner scan = new Scanner(new File(url));
            while (scan.hasNext()) {
                data += scan.nextLine() + "\n";
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }


}

