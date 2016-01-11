package com.wander.Cache;

import android.content.Context;
import android.util.Base64OutputStream;

import com.wander.MyUtils.SDCardHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wander on 2015/5/22.
 * time 23:16
 * Email 18955260352@163.com
 */
public class SaveJson {
    public static void Save(Context context, String json, String fileName) {
        File file = context.getFileStreamPath(fileName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, false));
            bos.write(json.getBytes());
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String GetJson(Context context, String fileName) {
        File file = context.getFileStreamPath(fileName);
        String data="";
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            ByteArrayOutputStream baos= new ByteArrayOutputStream();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] b = new byte[1024 * 8];
            int len = 0;
            while (-1 != (len = bis.read(b))) {
                baos.write(b,0,len);
            }
            data=new String(baos.toByteArray(),0,baos.size(),"utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
