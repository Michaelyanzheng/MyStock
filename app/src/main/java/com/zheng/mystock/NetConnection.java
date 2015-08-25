package com.zheng.mystock;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by michael on 2015/8/21.
 */
public class NetConnection {

    public NetConnection(final String url,
                         final HttpMethod method,
                         final SuccessCallback successCallback,
                         final FailCallback failCallback,
                         final String ...kvs){

        new AsyncTask<Void,Void,String>(){

            @Override
            protected String doInBackground(Void... params) {

                String getUrlStr;

                if (kvs != null){
                    StringBuilder paramsStr = new StringBuilder();
                    paramsStr.append("?");

                    for (int i = 0; i < kvs.length; i+=2){
                        paramsStr.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
                    }

                    getUrlStr = url.concat(paramsStr.toString());

                }else{

                    getUrlStr = url;
                }

                try {

                    URLConnection urlConnection;

                    switch (method){

                        case POST:

                            urlConnection = new URL(getUrlStr).openConnection();

                            break;

                        default:
                            urlConnection = new URL(getUrlStr).openConnection();
                            break;
                    }

                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream()));

                    String line = null;
                    StringBuffer resultStringBuffer = new StringBuffer();
                    int count = 0;

                    while ((line = bufferedReader.readLine()) != null){
                        resultStringBuffer.append(line);
                        count++;
                    }

                    return resultStringBuffer.toString();


                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {

                if (result != null){

                    if (successCallback != null){
                        successCallback.onSuccess(result);
                    }else{
                        if (failCallback != null){
                            failCallback.onFail();
                        }
                    }
                }else{
                    if (failCallback != null){
                        failCallback.onFail();
                    }
                }

                super.onPostExecute(result);
            }

        }.execute();



    }

    public static interface SuccessCallback{
        void onSuccess(String result);
    }

    public static interface FailCallback{
        void onFail();
    }
}
