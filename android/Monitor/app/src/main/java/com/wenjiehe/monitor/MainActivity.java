package com.wenjiehe.monitor;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.canyinghao.canrefresh.CanRefreshLayout;
import com.canyinghao.canrefresh.classic.ClassicRefreshView;
import com.canyinghao.canrefresh.shapeloading.ShapeLoadingRefreshView;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wenjiehe.monitor.Utils.file2Byte;
import static com.wenjiehe.monitor.Utils.getCurrentTime;
import static com.wenjiehe.monitor.Utils.readTimeFromFIle;
import static com.wenjiehe.monitor.Utils.removeJPG;
import static com.wenjiehe.monitor.Utils.saveToImByStr;
import static com.wenjiehe.monitor.Utils.writeTime2File;


public class MainActivity extends AppCompatActivity implements CanRefreshLayout.OnRefreshListener,CanRefreshLayout.OnLoadMoreListener{

    public static final String TAG = "MainActivity";
    public static ArrayList<MonitorObject> arrayListMO = new ArrayList<>();
    public static ArrayList<String> timestamp = new ArrayList<>();
    public static ArrayList<String> imageurl = new ArrayList<>();
    public static ArrayList<File> mList = new ArrayList<>();

    CanRefreshLayout refresh;
    ClassicRefreshView canRefreshHeader;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.meinfo_toolbar);
        toolbar.setTitle("监控图片信息");
        setSupportActionBar(toolbar);

        //透明状态栏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
//
//        mListItems = new LinkedList<String>();
//        mListItems.addAll(Arrays.asList(mStrings));
//
//        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
        refresh = (CanRefreshLayout) findViewById(R.id.refresh);
        canRefreshHeader = (ClassicRefreshView) findViewById(R.id.can_refresh_header);
        canRefreshFooter = (ClassicRefreshView) findViewById(R.id.can_refresh_footer);
        canRefreshHeader.setPullStr("下拉刷新");
        canRefreshHeader.setReleaseStr("释放立即刷新");
        canRefreshHeader.setCompleteStr("刷新完成");
        canRefreshHeader.setRefreshingStr("刷新中");

        canRefreshFooter.setPullStr("");
        canRefreshFooter.setReleaseStr("");
        canRefreshFooter.setCompleteStr("");
        canRefreshFooter.setRefreshingStr("");

        refresh.setOnRefreshListener(this);
        refresh.setOnLoadMoreListener(this);

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
            String str = response.body().string();
            Log.d(TAG, str);
            if (str.equals("[]")) {
                //arrayListMO.clear();

                Log.d(TAG, "timestamp is empty");
                writeTime2File();
                loadMonitorObjectFromFile();
                for (File f : mList)
                    arrayListMO.add(new MonitorObject(file2Byte(f), removeJPG(f.getName())));
//                rva.notifyDataSetChanged();
                Message message = handler.obtainMessage();
                message.what = IS_NONE;
                handler.sendMessage(message);
                return;
            }

            JSONArray jsonArray = JSON.parseArray(str);
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


    private static final int IS_FAIL = 0;
    private static final int IS_SUCCESS = 1;
    private static final int IS_NONE = 2;

    private int current = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IS_SUCCESS:
                    byte[] bytes = (byte[]) msg.obj;
                    //rrayListMO.add(new MonitorObject(bytes, timestamp.get(current)));
                    //if (current >= timestamp.size())
                     //   current = timestamp.size() - 1;
                    saveToImByStr(bytes, Environment.getExternalStorageDirectory() + "/Monitor/", timestamp.get(current));
                    current++;
                    Log.d(TAG, String.valueOf(current));
                    Log.d(TAG + "timestamp", String.valueOf(timestamp.size()));
                    if (current >= timestamp.size()) {//本次从服务器端获取数据完成了
//                        writeTime2File();
//                        loadMonitorObjectFromFile();
//                        for (File f : mList)
//                            Log.d(TAG, f.getName());
                        refresh.refreshComplete();
                        writeTime2File();
                        loadMonitorObjectFromFile();
                        for (File f : mList)
                            arrayListMO.add(new MonitorObject(file2Byte(f), removeJPG(f.getName())));
                        Message message = handler.obtainMessage();
                        message.what = IS_NONE;
                        handler.sendMessage(message);
                        return;
                    }

                    rva.notifyDataSetChanged();
                    //imageView.setImageBitmap(bitmap);
                    break;
                case IS_FAIL:
                    break;
                case IS_NONE:
                    Log.d(TAG+"mL",String.valueOf(mList.size()));
                    Log.d(TAG+"ar",String.valueOf(arrayListMO.size()));

                    rva.notifyDataSetChanged();
                    refresh.refreshComplete();
                    break;
                default:
                    break;
            }
        }
    };

    private void loadMonitorObjectFromFile() {
        String url = Environment.getExternalStorageDirectory() + "/Monitor/";
        File albumdir = new File(url);

        mList.clear();

        File[] imgfile = albumdir.listFiles(filefiter);
        int len = imgfile.length;
        for (int i = 0; i < len; i++) {
            mList.add(imgfile[i]);
        }
        Log.d(TAG+" mList.size",String.valueOf(mList.size()));
        Collections.sort(mList, new FileComparator());
    }

    private FileFilter filefiter = new FileFilter() {
        @Override
        public boolean accept(File f) {
            String tmp = f.getName().toLowerCase();
            if (tmp.endsWith(".png") || tmp.endsWith(".jpg")
                    || tmp.endsWith(".jpeg")) {
                return true;
            }
            return false;
        }
    };

    @Override
    public void onRefresh() {
        arrayListMO.clear();
        timestamp= new ArrayList<>();
        imageurl= new ArrayList<>();
        current = 0;

        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
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
        }, 2500);

    }

    @Override
    public void onLoadMore() {
        refresh.loadMoreComplete();
    }

    private class FileComparator implements Comparator<File> {
        @Override
        public int compare(File lhs, File rhs) {

            String lhsTime = removeJPG(lhs.getName());
            String rhsTime = removeJPG(rhs.getName());

            Date lhsDate = null;
            Date rhsDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                lhsDate = sdf.parse(lhsTime);
                rhsDate = sdf.parse(rhsTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return rhsDate.compareTo(lhsDate);
        }
    }

    private long exitTime = 0;

    /**
     * 捕捉返回事件按钮
     * <p>
     * 因为此 Activity 继承 TabActivity 用 onKeyDown 无响应，所以改用 dispatchKeyEvent
     * 一般的 Activity 用 onKeyDown 就可以了
     */

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            arrayListMO .clear();
            finish();
        }
    }
}
