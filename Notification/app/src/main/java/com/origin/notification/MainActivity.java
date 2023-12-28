package com.origin.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.send_notice);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.send_notice:
                Intent intent = new Intent(this,NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,0);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if(Build.VERSION.SDK_INT >= 26){

                    //当sdk版本大于26
                    String id = "channel_1";
                    String description = "143";
                    int importance = NotificationManager.IMPORTANCE_LOW;
                    NotificationChannel channel = new NotificationChannel(id, description, importance);
                    notificationManager.createNotificationChannel(channel);
                    @SuppressLint("WrongConstant") Notification notification = new Notification.Builder(MainActivity.this, id)
                            .setCategory(Notification.CATEGORY_MESSAGE)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                            .setContentTitle("This is a content title")
                            .setStyle(new Notification.BigTextStyle().bigText("说到颜色，前端的小伙伴们一定都不陌生，比如字体颜色、背景色等等，颜色是构建视觉效果的重要部分，所以也必然是可视化的关键部分，色彩对人的视觉感知以及情绪心理都存在不少的影响，所以了解颜色表示对可视化非常重要。那么图形系统中都有哪些颜色表示方式呢？说到颜色，前端的小伙伴们一定都不陌生，比如字体颜色、背景色等等，颜色是构建视觉效果的重要部分，所以也必然是可视化的关键部分，色彩对人的视觉感知以及情绪心理都存在不少的影响，所以了解颜色表示对可视化非常重要。那么图形系统中都有哪些颜色表示方式呢？ ..."))
                          //  .setContentText("说到颜色，前端的小伙伴们一定都不陌生，比如字体颜色、背景色等等，颜色是构建视觉效果的重要部分，所以也必然是可视化的关键部分，色彩对人的视觉感知以及情绪心理都存在不少的影响，所以了解颜色表示对可视化非常重要。那么图形系统中都有哪些颜色表示方式呢？ ...")
                            .setContentIntent(pendingIntent)//点击后显示实例
                            .setAutoCancel(true)//点击后 消除通知
                            .build();
                    notificationManager.notify(1, notification);
                }
                else
                {
                    //当sdk版本小于26
                    Notification notification = new NotificationCompat.Builder(MainActivity.this)
                            .setContentTitle("This is content title")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText("说到颜色，前端的小伙伴们一定都不陌生，比如字体颜色、背景色等等，颜色是构建视觉效果的重要部分，所以也必然是可视化的关键部分，色彩对人的视觉感知以及情绪心理都存在不少的影响，所以了解颜色表示对可视化非常重要。那么图形系统中都有哪些颜色表示方式呢？"))
                            .setContentText("说到颜色，前端的小伙伴们一定都不陌生，比如字体颜色、背景色等等，颜色是构建视觉效果的重要部分，所以也必然是可视化的关键部分，色彩对人的视觉感知以及情绪心理都存在不少的影响，所以了解颜色表示对可视化非常重要。那么图形系统中都有哪些颜色表示方式呢？ ...")
                            .setContentIntent(pendingIntent)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                            .build();
                    notificationManager.notify(1,notification);
                }

            default:
                break;
        }
    }
}