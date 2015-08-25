package com.zheng.mystock;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by michael on 2015/8/22.
 */
public class GetPicture {

    public GetPicture(final String pictureUrl,
                      final SuccessCallback successCallback,
                      final FailCallback failCallback){

        new AsyncTask<Void,Void,Bitmap>(){

            @Override
            protected Bitmap doInBackground(Void... params) {

                URL pictureURL;

                try {

                    pictureURL = new URL(pictureUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) pictureURL.openConnection();
                    urlConnection.setDoInput(true);
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();

                    Bitmap resultBitmap = BitmapFactory.decodeStream(inputStream);

                    saveBitmap(pictureUrl,resultBitmap);

                    inputStream.close();

                    return resultBitmap;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {

                if (successCallback != null){
                    successCallback.onSuccess(bitmap);
                }else{
                    if (failCallback != null){
                        failCallback.onFail();
                    }
                }
                super.onPostExecute(bitmap);
            }
        }.execute();
    }

    private void saveBitmap(String pictureUrl,Bitmap resultBitmap){

        savePicture(pictureUrl,Config.MIN,resultBitmap);
        savePicture(pictureUrl,Config.DAILY,resultBitmap);
        savePicture(pictureUrl,Config.WEEKLY,resultBitmap);
        savePicture(pictureUrl,Config.MONTHLY,resultBitmap);
    }

    private void savePicture(String pictureUrl,String containsName,Bitmap resultBitmap){

        try {

            if(pictureUrl.contains(containsName)) {

                File minFile = new File(new File(Config.PICTURE_PATH), containsName+".png");
                BufferedOutputStream bufferedOutoutStream = new BufferedOutputStream(
                        new FileOutputStream(minFile));

                resultBitmap.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutoutStream);

                bufferedOutoutStream.flush();

                bufferedOutoutStream.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static interface SuccessCallback{
        void onSuccess(Bitmap result);
    }

    public static interface FailCallback{
        void onFail();
    }
}
