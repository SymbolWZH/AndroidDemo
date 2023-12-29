package com.origin.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    Button send_request;
    TextView response_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send_request = findViewById(R.id.send_request);
        response_text = findViewById(R.id.response_text);

        send_request.setOnClickListener(this);
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作,将结果显示到界面上
                response_text.setText(response);
                Log.d("response_text", response);
            }
        });
    }
    //GSON解析JSON数据格式
    private void parseJsonWithGSON(String jsonData) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<App>>(){}.getType();
        List<App> appList = gson.fromJson(jsonData, listType);

        for (App app : appList){
            Log.d(TAG, "id is " + app.getId() );
            Log.d(TAG, "name is " + app.getName() );
            Log.d(TAG, "version is " + app.getVersion() );
        }
    }
    //解析JSON数据格式
    private void parseJsonWithJSONObject(String jsonData){
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");

                Log.d(TAG, "id is " + id );
                Log.d(TAG, "name is " + name );
                Log.d(TAG, "version is " + version );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //使用SAX实现
    private void parseXMLWithSAX(String xmlData){
        try {
            SAXParserFactory factory =  SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler contentHandler = new ContentHandler();
            xmlReader.setContentHandler(contentHandler);
            //开始解析
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //使用okHttp实现
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send_request) sendRequestWithOkHttp();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://192.168.10.15:7777/data.json").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJsonWithGSON(responseData);
                    //parseJsonWithJSONObject(responseData);
                    //parseXMLWithSAX(responseData);
                    //showResponse(responseData);
                    //parseXMLWithPull(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

//    private void parseXMLWithPull(String xmlData) {
//        try {
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            XmlPullParser xmlPullParser = factory.newPullParser();
//            xmlPullParser.setInput(new StringReader(xmlData));
//            int eventType = xmlPullParser.getEventType();
//            String id = "";
//            String name = "";
//            String version = "";
//            while (eventType != xmlPullParser.END_DOCUMENT) {
//                String nodeName = xmlPullParser.getName();
//                switch (eventType) {
//                    //开始解析节点
//                    case XmlPullParser.START_TAG: {
//                        if ("id".equals(nodeName)) {
//                            id = xmlPullParser.nextText();
//                        } else if ("name".equals(nodeName)) {
//                            name = xmlPullParser.nextText();
//                        } else if ("version".equals(nodeName)) {
//                            version = xmlPullParser.nextText();
//                        }
//                        break;
//                    }
//
//                    case XmlPullParser.END_TAG: {
//                        if ("app".equals(nodeName)) {
//                            Log.d("MainActivity", "id is " + id);
//                            Log.d("MainActivity", "name is " + name);
//                            Log.d("MainActivity", "version is " + version);
//
//                        }
//                        break;
//                    }
//                    default:
//                        break;
//                }
//                eventType = xmlPullParser.next();
//            }
//        } catch (XmlPullParserException | IOException e) {
//            e.printStackTrace();
//        }
//    }
    //使用 HttpURLConnection
//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.send_request) sendRequestWithHttpURLConnection();
//    }
//
//    private void sendRequestWithHttpURLConnection(){
//        //开启线程来发起网络请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection connection = null;
//                BufferedReader reader = null;
//                try {
//                    URL url = new URL("https://www.baidu.com");
//                    connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(8000);
//                    connection.setReadTimeout(8000);
//                    InputStream in = connection.getInputStream();
//                    //对获取到的输入流进行读取
//                    reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuilder response = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null){
//                        response.append(line);
//                    }
//                    Log.d("11111111", "run: -----------------------");
//                    showResponse(response.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    if (reader != null){
//                        try {
//                            reader.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (connection != null){
//                        connection.disconnect();
//                    }
//                }
//            }
//        }).start();
//    }
//

}