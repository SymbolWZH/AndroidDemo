package com.origin.activitytest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends BaseActivity {
    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Task id is"+ getTaskId());
        setContentView(R.layout.second_layout);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) actionbar.hide();

        Intent intent = getIntent();//创建对象
        String Data1 = intent.getStringExtra("param1");//接收传递的参数
        String Data2 = intent.getStringExtra("param2");
        Log.d("SecondActivity", Data1+"-----"+Data2);


        Button button2 = findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = new Intent(SecondActivity.this,ThirdActivity.class); //创建对象
                //startActivity(intent);
//                intent.putExtra("data_return","Hello FirstActivity");
//                setResult(RESULT_OK,intent);
//                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return","back_Hello FirstActivity");
        setResult(RESULT_OK,intent);
        finish();
    }

    public static void actionStart(Context context,String data1,String data2)
    {
        Intent intent = new Intent(context,SecondActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }
}
