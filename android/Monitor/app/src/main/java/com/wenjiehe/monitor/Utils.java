package com.wenjiehe.monitor;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangpengcheng on 2016/12/9.
 */

public class Utils {

    //创建一个文件，用于保存当前时间节点的照片，刷新后继续则从该时间节点下载，而并非重新开始下载。
    public static void writeTime2File() {
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

    public static String readTimeFromFIle() {
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
            return "2016-12-09T13:20:00";
        }
        return "2016-12-09T13:20:00";
    }

    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(date);
    }


    //获得指定文件的byte数组
    public static byte[] file2Byte(File file){
        byte[] buffer = null;
        try {
            //File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * @param bytes   二进制流
     * @param imgPath 图片保存路径
     * @param imgName 图片保存名称
     * @return 1：保存正常 0：保存失败
     */
    public  static int saveToImByStr(byte[] bytes, String imgPath, String imgName) {
        int stateInt = 1;
        try {
            File validateCodeFolder = new File(imgPath);
            if (!validateCodeFolder.exists()) {
                validateCodeFolder.mkdirs();
            }
            InputStream in = new ByteArrayInputStream(bytes);
            File file = new File(imgPath + imgName+".jpg");
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

    public static String removeJPG(String str){
        String[] s = str.split("\\.");
        return s[0];
    }
}
