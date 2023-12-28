package com.origin.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //private List<Fruit> fruitList = new ArrayList<>();
    private List<Msg> msgList = new ArrayList<>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMsgs();

        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        msgRecyclerView = findViewById(R.id.msgRecycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!"".equals(content))
                {
                    Msg msg = new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);//把消息插入到最后一行
                    msgRecyclerView.scrollToPosition(msgList.size() -1 );//把视图定位到最后一行 保证后面插入的消息能够看到
                    inputText.setText("");
                }
            }
        });
//        initFruits();
//
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        /*第一个参数接收列数 第二个按照指定布局排列方向*/
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        FruitAdapter fruitAdapter = new FruitAdapter(fruitList);
//        recyclerView.setAdapter(fruitAdapter);


    }

//    private void initFruits(){
//        for (int i = 0; i < 2; i++){
//            Fruit apple = new Fruit(getRandomLengthName("Apple"),R.drawable.ic_launcher_background);
//            fruitList.add(apple);
//            Fruit banana = new Fruit(getRandomLengthName("banana"),R.drawable.ic_launcher_background);
//            fruitList.add(banana);
//            Fruit orange = new Fruit(getRandomLengthName("orange"),R.drawable.ic_launcher_background);
//            fruitList.add(orange);
//            Fruit pear = new Fruit(getRandomLengthName("pear"),R.drawable.ic_launcher_background);
//            fruitList.add(pear);
//            Fruit watermelon = new Fruit(getRandomLengthName("watermelon"),R.drawable.ic_launcher_background);
//            fruitList.add(watermelon);
//            Fruit grape = new Fruit(getRandomLengthName("grape"),R.drawable.ic_launcher_background);
//            fruitList.add(grape);
//            Fruit pineapple = new Fruit(getRandomLengthName("pineapple"),R.drawable.ic_launcher_background);
//            fruitList.add(pineapple);
//            Fruit strawberry = new Fruit(getRandomLengthName("strawberry"),R.drawable.ic_launcher_background);
//            fruitList.add(strawberry);
//            Fruit cherry = new Fruit(getRandomLengthName("cherry"),R.drawable.ic_launcher_background);
//            fruitList.add(cherry);
//            Fruit mango = new Fruit(getRandomLengthName("mango"),R.drawable.ic_launcher_background);
//            fruitList.add(mango);
//       }
//
//    }
//    private String getRandomLengthName(String name) {
//        Random random = new Random();
//        int length = random.nextInt(20) + 1;
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            builder.append(name);
//        }
//        return builder.toString();
//    }

    private void initMsgs()
    {
        Msg msg1 = new Msg("Hello guy.",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello. Who is that?",Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice talking to you.",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
        Msg msg4 = new Msg("Me too.",Msg.TYPE_SENT);
        msgList.add(msg4);
    }
}