package com.wenjiehe.monitor;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import android.app.Activity;
import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final MediaType JSON = MediaType.parse("application/json;charset = utf-8");
    private PullToRefreshListView mPullToRefreshListView;
    private LinkedList<String> mListItems;
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set a listener to be invoked when the list should be refreshed.
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
        ListView actualListView = mPullToRefreshListView.getRefreshableView();
        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(mStrings));

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
        actualListView.setAdapter(mAdapter);

    //开启一个线程做联网操作
        new Thread (){
            @Override
            public void run(){

                postJson();
            }
        }.start();
    }

    private void postJson(){
        //申明给服务端传递一个json串
        //创建一个OKHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递json串)
       Request request = new DownloadManager.Request.Builder()
               .url("IP地址")
               .post(requestBody)
               .build();

        //发送请求获取响应
        try{
            Response response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()) {
                //打印服务端返回结果
                Log.i(TAG, response.body().string());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

            @Override
            protected String[] doInBackground(Void... params) {
                // Simulates a background job.
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                }
                return mStrings;
            }
            @Override
            protected void onPostExecute(String[] result) {
                mListItems.addFirst("Added after refresh...");
                mAdapter.notifyDataSetChanged();

                // Call onRefreshComplete when the list has been refreshed.
                mPullToRefreshListView.onRefreshComplete();
                super.onPostExecute(result);
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

        private String[] mStrings = { "John", "Michelle", "Amy", "Kim", "Mary",
                "David", "Sunny", "James", "Maria", "Michael", "Sarah", "Robert",
                "Lily", "William", "Jessica", "Paul", "Crystal", "Peter",
                "Jennifer", "George", "Rachel", "Thomas", "Lisa", "Daniel", "Elizabeth",
                "Kevin" };
    }
