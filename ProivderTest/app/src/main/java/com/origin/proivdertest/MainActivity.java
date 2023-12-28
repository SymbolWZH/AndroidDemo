package com.origin.proivdertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private String newId;
    private static final String AUTHORITY = "content://com.origin.databasetest.proivder/";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button AddData = findViewById(R.id.add_data);
        Button QueryData = findViewById(R.id.query_data);
        Button UpdateData = findViewById(R.id.update_data);
        Button DeleteData = findViewById(R.id.delete_data);

        AddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 处理添加数据的逻辑
                Uri uri = Uri.parse(AUTHORITY + "book");
                ContentValues values = new ContentValues();
                values.put("name", "A Clash of kings");
                values.put("author", "George Martin");
                values.put("pages", "400");
                values.put("price", "22.85");
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
            }
        });

        QueryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 处理查询数据的逻辑
                Uri uri = Uri.parse(AUTHORITY + "book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex("author"));
                        @SuppressLint("Range") int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        @SuppressLint("Range") double price = cursor.getInt(cursor.getColumnIndex("price"));

                        Log.d(TAG, "book name is " + name);
                        Log.d(TAG, "author name is " + author);
                        Log.d(TAG, "pages name is " + pages);
                        Log.d(TAG, "price name is " + price);

                    }
                }
                cursor.close();
            }
        });

        UpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 处理更新数据的逻辑
                Uri uri = Uri.parse(AUTHORITY + "book/" + newId);
                ContentValues values = new ContentValues();
                values.put("name", "A Storm of Swords");
                values.put("pages", "1216");
                values.put("price", "19.99");
                getContentResolver().update(uri, values, null, null);
            }
        });

        DeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 处理删除数据的逻辑
                Uri uri = Uri.parse(AUTHORITY + "book/" + newId);
                getContentResolver().delete(uri, null, null);
            }
        });

    }


}