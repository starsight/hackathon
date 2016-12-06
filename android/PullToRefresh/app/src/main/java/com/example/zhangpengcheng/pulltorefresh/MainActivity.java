package com.example.zhangpengcheng.pulltorefresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zhangpengcheng.pulltorefresh.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final MediaType JSON = MediaType.parse("application/json;charset = utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开启一个线程，做联网操作
        new Thread() {
            @Override
            public void run() {

                try {
                    postJson();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void postJson() throws JSONException {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("qq", "302942319")
                .add("key", "c8b76a084579e43a112f15c16f839f4b")
                .build();
        //创建一个请求对象
        Request request = new Request.Builder()
                .url("http://japi.juhe.cn/qqevaluate/qq")
                .post(requestBody)
                .build();
        //发送请求获取响应
        try {
            Response response = okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if (response.isSuccessful()) {
                //打印服务端返回结果
                Log.d(TAG, response.body().string());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
