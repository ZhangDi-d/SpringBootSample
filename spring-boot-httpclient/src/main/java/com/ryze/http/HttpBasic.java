package com.ryze.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xueLai on 2019/6/11.
 */
public class HttpBasic {
    public static void main(String[] args){
        //executeRequest();
        doGet();
    }
    public static void executeRequest()  {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("https://blog.csdn.net/ShelleyLittlehero/article/details/91449246");
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                int l;
                byte[] tmp = new byte[2048];
                while ((l = instream.read(tmp)) != -1) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doGet(){
        HttpGet httpget = new HttpGet("https://blog.csdn.net/ShelleyLittlehero/article/details/91449246");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            long len = entity.getContentLength();
            if (len != -1 && len < 2048) {
                try {
                    System.out.println(EntityUtils.toString(entity));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
// Stream content out
            }
        }
    }
}
