package com.wenjiehe.monitor;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    //public static final MediaType JSON = MediaType.parse("application/json;charset = utf-8");
    //private LinkedList<String> mListItems;
   // private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set a listener to be invoked when the list should be refreshed.
//
//        mListItems = new LinkedList<String>();
//        mListItems.addAll(Arrays.asList(mStrings));
//
//        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);

   //开启一个线程做联网操作
//        new Thread (){
//            @Override
//            public void run(){
//                postJson();
//            }
//        }.start();
    }

//    private void postJson(){
//        OkHttpClient okHttpClient = new OkHttpClient();
//        //构建一个请求对象
//        Request request = new Request.Builder().url("http://v.juhe.cn/weather/index?format=2&cityname=%E8%8B%8F%E5%B7%9E&key=b57251806c2f3936f2288a771fc66c5e").build();
//        //发送请求
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//            //打印服务端传回的数据
//            Log.d(TAG, response.body().string());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
