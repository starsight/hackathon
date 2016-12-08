package com.wenjiehe.monitor;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by zhangpengcheng on 2016/12/8.
 */

public class MonitorObject {
    //public Bitmap bm;
    byte[] bytesImage;
    public String tittle;

    public MonitorObject(byte[] bytesImage) {
        this.bytesImage = bytesImage;
    }
    //public String time;
}
