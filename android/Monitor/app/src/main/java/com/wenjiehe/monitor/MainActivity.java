package com.wenjiehe.monitor;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.canyinghao.canrefresh.CanRefreshLayout;
import com.canyinghao.canrefresh.classic.ClassicRefreshView;
import com.canyinghao.canrefresh.shapeloading.ShapeLoadingRefreshView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static ArrayList<MonitorObject> arrayListMO = new ArrayList<>();
    public static ArrayList<String> timestamp = new ArrayList<>();
    public static ArrayList<String> imageurl = new ArrayList<>();

    CanRefreshLayout refresh;
    ShapeLoadingRefreshView canRefreshHeader;
    ClassicRefreshView canRefreshFooter;

    private RecyclerView recyclerView;
    RecyclerViewAdapter rva;

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
        refresh = (CanRefreshLayout) findViewById(R.id.refresh);
        canRefreshHeader = (ShapeLoadingRefreshView) findViewById(R.id.can_refresh_header);
        canRefreshFooter = (ClassicRefreshView) findViewById(R.id.can_refresh_footer);
        canRefreshFooter.setPullStr("下拉刷新");
        canRefreshFooter.setReleaseStr("释放立即刷新");
        canRefreshFooter.setCompleteStr("刷新完成");
        canRefreshFooter.setRefreshingStr("刷新中");


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.can_scroll_view);
        rva = new RecyclerViewAdapter(arrayListMO, MainActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rva);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //开启一个线程做联网操作
        new Thread() {
            @Override
            public void run() {
                String lastTime = readTimeFromFIle();
                String currentTime = getCurrentTime();
                Log.d(TAG, lastTime);
                Log.d(TAG, currentTime);
                getNewImageResource(lastTime, currentTime);
            }
        }.start();
    }

    private void getNewImageResource(String lastTime, String currentTime) {
        OkHttpClient okHttpClient = new OkHttpClient();
        //构建一个请求对象
        Request request = new Request.Builder().url
                ("http://api.yeelink.net/v1.0/device/344726/sensor/383332.json?start=" + lastTime + "&end=" + currentTime + "&interval=1&page=1")
                .build();
        //Request request = new Request.Builder().url("http://api.yeelink.net/v1.0/device/344726/sensor/383332.json?start=2016-12-05T17:00:00&end=2016-12-05T20:00:00&interval=1&page=1").build();
        //发送请求
        try {
            Response response = okHttpClient.newCall(request).execute();
            //打印服务端传回的数据
            //Log.d(TAG, response.body().string());


            JSONArray jsonArray = JSON.parseArray(response.body().string());
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                timestamp.add(jo.getString("timestamp"));
                //System.out.println(jo.toJSONString());
            }

            for (int i = 0; i < timestamp.size(); i++) {
                OkHttpClient okHttpClient2 = new OkHttpClient();
                //构建一个请求对象
                Request request2 = new Request.Builder().url("http://api.yeelink.net/v1.0/device/344726/sensor/383332/photo/info/" + timestamp.get(i)).build();
                Response response2 = okHttpClient2.newCall(request2).execute();

                JSONObject jo = JSON.parseObject(response2.body().string());
                imageurl.add(jo.getString("url"));
                //Log.d(TAG,imageurl.get(i));
            }

            for (int i = 0; i < imageurl.size(); i++) {
                synchronizedGet(imageurl.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void synchronizedGet(String imageUrl) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().get()
                .url(imageUrl)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Message message = handler.obtainMessage();
            if (response.isSuccessful()) {
                message.what = IS_SUCCESS;
                message.obj = response.body().bytes();
                handler.sendMessage(message);
            } else {
                handler.sendEmptyMessage(IS_FAIL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final int IS_SUCCESS = 1;
    private static final int IS_FAIL = 0;
    private int current = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IS_SUCCESS:
                    byte[] bytes = (byte[]) msg.obj;
                    arrayListMO.add(new MonitorObject(bytes, timestamp.get(current)));
                    if (current >= timestamp.size())
                        current = timestamp.size() - 1;
                    saveToImByStr(bytes, Environment.getExternalStorageDirectory() + "/Monitor/", timestamp.get(current));
                    current++;
                    if (current >= timestamp.size() - 1) {//本次从服务器端获取数据完成了
                        writeTimeFromFIle();
                        loadMonitorObjectFromFile();
                    }
                    rva.notifyDataSetChanged();
                    //imageView.setImageBitmap(bitmap);
                    break;
                case IS_FAIL:
                    break;
                default:
                    break;
            }
        }
    };

    private void loadMonitorObjectFromFile() {
//        arrayListMO.
    }

    /**
     * @param bytes   二进制流
     * @param imgPath 图片保存路径
     * @param imgName 图片保存名称
     * @return 1：保存正常 0：保存失败
     */
    private static int saveToImByStr(byte[] bytes, String imgPath, String imgName) {
        int stateInt = 1;
        try {
            File validateCodeFolder = new File(imgPath);
            if (!validateCodeFolder.exists()) {
                validateCodeFolder.mkdirs();
            }
            InputStream in = new ByteArrayInputStream(bytes);
            File file = new File(imgPath + imgName);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int nRead = 0;
            while ((nRead = in.read(b)) != -1) {
                fos.write(b, 0, nRead);
            }
            fos.flush();
            fos.close();
            in.close();
        } catch (Exception e) {
            stateInt = 0;
            e.printStackTrace();
        } finally {
        }
        return stateInt;
    }

    //创建一个文件，用于保存当前时间节点的照片，刷新后继续则从该时间节点下载，而并非重新开始下载。
    public void writeTimeFromFIle() {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/Monitor/time");
            BufferedWriter bf = new BufferedWriter(new PrintWriter(file));
            bf.write(getCurrentTime());
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readTimeFromFIle() {
        StringBuffer str = new StringBuffer("");
        File file = new File(Environment.getExternalStorageDirectory() + "/Monitor/time");

        if (file.exists()) {
            InputStreamReader read = null;//考虑到编码格式
            try {
                read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                lineTxt = bufferedReader.readLine();
                read.close();
                return lineTxt;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            return "2016-12-05T20:00:00";
        }
        return "2016-12-05T20:00:00";
    }

    private static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(date);
    }

}
