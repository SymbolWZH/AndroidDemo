package com.origin.activitytest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

public class FirstActivity  extends BaseActivity  {
    private static final String TAG = "FirstActivity";


    private String[] data ={ "Apple","Banana","Orange","Watermelon",
            "Pear","Grape","Pineapple","Strawberry","Cherry","Mango","Apple",
            "Banana","Orange","Watermelon","Pear","Grape",
            "pineapple","Strawberry","Cherry","Mango"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) actionbar.hide();
        /*在布局中加入ListView*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FirstActivity.this , android.R.layout.simple_list_item_1,data);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(FirstActivity.this,item,Toast.LENGTH_SHORT).show();
            }
        });
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1:
                if (resultCode == RESULT_OK){
                    String returnData = data.getStringExtra("data_return");
                    Log.d("FirstActivity", returnData);
                }
                break;
            default:
        }
    }


    /*创建菜单显示*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*getMenuInflater:获取菜单 inflate接收参数*/
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_item:
                Toast.makeText(this,
                        "You checked  Add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,
                        "You checked  Remove",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}