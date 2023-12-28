package com.origin.activitytest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());

        ActivityCollector.addActivity(this); //将当前活动创建到活动管理器
    }

    @Override
    protected void onDestroy() { //重写onDestroy方法
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
