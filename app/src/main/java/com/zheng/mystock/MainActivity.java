package com.zheng.mystock;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends Activity {

    private final static String TAG = "MainActivity";

    private String result;

    private Button mGetStockButton;
    private EditText mQueryEditText;

    private TextView mStockNameTextView;
    private TextView mStockCodeTextView;

    private TextView mTodayStartTextView;
    private TextView mYestodendTextView;

    private TextView mTodayMaxTextView;
    private TextView mTodayMinTextView;

    private TextView mTraNumberTextView;
    private TextView mTraAmountTextView;

    private TextView mRateTextView;
    private TextView mNowPriTextView;

    private ImageView mMinImageView;
    private ImageView mDayImageView;
    private ImageView mWeekImageView;
    private ImageView mMonthImageView;

    private ProgressBar mMinProgressBar;
    private ProgressBar mDayProgressBar;
    private ProgressBar mWeekProgressBar;
    private ProgressBar mMonthProgressBar;


    private void initView(){

        mGetStockButton = (Button) findViewById(R.id.getStock_Button);
        mQueryEditText = (EditText) findViewById(R.id.query_EditText);

        mStockNameTextView = (TextView) findViewById(R.id.stockName_TextView);
        mStockCodeTextView = (TextView) findViewById(R.id.stockCode_TextView);

        mTodayStartTextView = (TextView) findViewById(R.id.todaystartpri_TextView);
        mYestodendTextView = (TextView) findViewById(R.id.yestodendpri_TextView);

        mTodayMaxTextView = (TextView) findViewById(R.id.todaymax_TextView);
        mTodayMinTextView = (TextView) findViewById(R.id.todaymin_TextView);

        mTraNumberTextView = (TextView) findViewById(R.id.tranumber_TextView);
        mTraAmountTextView = (TextView) findViewById(R.id.traamount_TextView);

        mRateTextView = (TextView) findViewById(R.id.rate_TextView);
        mNowPriTextView = (TextView) findViewById(R.id.nowpri_TextView);

        mMinImageView = (ImageView) findViewById(R.id.min_ImageView);
        mDayImageView = (ImageView) findViewById(R.id.day_ImageView);
        mWeekImageView = (ImageView) findViewById(R.id.week_ImageView);
        mMonthImageView = (ImageView) findViewById(R.id.month_ImageView);

        mMinProgressBar = (ProgressBar) findViewById(R.id.min_progressBar);
        mDayProgressBar = (ProgressBar) findViewById(R.id.day_progressBar);
        mWeekProgressBar = (ProgressBar) findViewById(R.id.week_progressBar);
        mMonthProgressBar = (ProgressBar) findViewById(R.id.month_progressBar);

        mkdirPicturePath();
    }

    private void mkdirPicturePath(){

        File picturePath_File = new File(Config.PICTURE_PATH);
        if (!picturePath_File.exists()){
            picturePath_File.mkdir();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (savedInstanceState != null){

            mQueryEditText.setText(savedInstanceState.getString(Config.QUERY));
            mStockNameTextView.setText(savedInstanceState.getString(Config.NAME));
            mStockCodeTextView.setText(savedInstanceState.getString(Config.CODE));
            mTodayStartTextView.setText(savedInstanceState.getString(Config.TODAYSTART));
            mYestodendTextView.setText(savedInstanceState.getString(Config.YESTODEND));
            mTodayMaxTextView.setText(savedInstanceState.getString(Config.TODAYMAX));
            mTodayMinTextView.setText(savedInstanceState.getString(Config.TODAYMIN));
            mTraNumberTextView.setText(savedInstanceState.getString(Config.TRANUMBER));
            mTraAmountTextView.setText(savedInstanceState.getString(Config.TRAAMOUNT));

            mRateTextView.setText(savedInstanceState.getString(Config.RATE));
            mNowPriTextView.setText(savedInstanceState.getString(Config.NOWPRI));

            try {
                mMinImageView.setImageBitmap(BitmapFactory.decodeStream(
                        new FileInputStream(new File(Config.PICTURE_PATH,Config.MIN + Config.PICTURE_FORMAT))));

                mDayImageView.setImageBitmap(BitmapFactory.decodeStream(
                        new FileInputStream(new File(Config.PICTURE_PATH,Config.DAILY + Config.PICTURE_FORMAT))));

                mWeekImageView.setImageBitmap(BitmapFactory.decodeStream(
                        new FileInputStream(new File(Config.PICTURE_PATH,Config.WEEKLY + Config.PICTURE_FORMAT))));

                mMonthImageView.setImageBitmap(BitmapFactory.decodeStream(
                        new FileInputStream(new File(Config.PICTURE_PATH,Config.MONTHLY + Config.PICTURE_FORMAT))));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        mGetStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String queryStockCode = mQueryEditText.getText().toString().trim();

                if (TextUtils.isEmpty(queryStockCode)) {

                    Toast.makeText(MainActivity.this, getString(R.string.please_into_stock_code), Toast.LENGTH_SHORT).show();

                } else {

                    new GetStock(queryStockCode, new GetStock.SuccessCallback() {
                        @Override
                        public void onSuccess(String result) {

                            Log.d(TAG, "onSuccess");

                            Log.d(TAG, result);

                            refreshTextView(result);
                        }
                    }, new GetStock.FailCallback() {
                        @Override
                        public void onFail() {

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Config.QUERY, mQueryEditText.getText().toString());
        outState.putString(Config.NAME, mStockNameTextView.getText().toString());
        outState.putString(Config.CODE,mStockCodeTextView.getText().toString());
        outState.putString(Config.TODAYSTART,mTodayStartTextView.getText().toString());
        outState.putString(Config.YESTODEND,mYestodendTextView.getText().toString());
        outState.putString(Config.TODAYMAX,mTodayMaxTextView.getText().toString());
        outState.putString(Config.TODAYMIN,mTodayMinTextView.getText().toString());
        outState.putString(Config.TRANUMBER,mTraNumberTextView.getText().toString());
        outState.putString(Config.TRAAMOUNT,mTraAmountTextView.getText().toString());

        outState.putString(Config.RATE,mRateTextView.getText().toString());
        outState.putString(Config.NOWPRI, mNowPriTextView.getText().toString());
    }

    private void refreshTextView(String result){

        try {

            JSONObject resultJsonObject = new JSONObject(result);

            JSONArray resultJSONArray = resultJsonObject.getJSONArray(Config.RESULT);

            JSONObject resultValueJSONArray = resultJSONArray.getJSONObject(Config.RESULT_INT);
            JSONObject dataJsonObject = resultValueJSONArray.getJSONObject(Config.DATA);
            JSONObject dapandataJsonObject = resultValueJSONArray.getJSONObject(Config.DAPANDATA);
            JSONObject gopictureJsonObject = resultValueJSONArray.getJSONObject(Config.GOPICTURE);

            Log.d(TAG,resultValueJSONArray.toString());

            String name = dataJsonObject.getString(Config.NAME);
            String gid = dataJsonObject.getString(Config.GID);
            String todayStartPri = dataJsonObject.getString(Config.TODAYSTART);
            String yestodEndPri = dataJsonObject.getString(Config.YESTODEND);
            String todayMax = dataJsonObject.getString(Config.TODAYMAX);
            String todayMin = dataJsonObject.getString(Config.TODAYMIN);
            String traNumber = dataJsonObject.getString(Config.TRANUMBER);
            String traAmount = dataJsonObject.getString(Config.TRAAMOUNT);
            String rate = dapandataJsonObject.getString(Config.RATE);
            String nowPri = dataJsonObject.getString(Config.NOWPRI);

            String minurl = gopictureJsonObject.getString(Config.MINURL);
            String dayurl = gopictureJsonObject.getString(Config.DAYURL);
            String weekurl = gopictureJsonObject.getString(Config.WEEKURL);
            String monthurl = gopictureJsonObject.getString(Config.MOUNTHRUL);

            mStockNameTextView.setText(name);
            mStockCodeTextView.setText(gid);
            mTodayStartTextView.setText(todayStartPri);
            mYestodendTextView.setText(yestodEndPri);
            mTodayMaxTextView.setText(todayMax);
            mTodayMinTextView.setText(todayMin);
            mTraNumberTextView.setText(traNumber);
            mTraAmountTextView.setText(traAmount);
            mRateTextView.setText(rate);
            mNowPriTextView.setText(nowPri);

            refreshImageView(mMinImageView,minurl);
            refreshImageView(mDayImageView,dayurl);
            refreshImageView(mWeekImageView,weekurl);
            refreshImageView(mMonthImageView,monthurl);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "printStackTrace()");
        }
    }

    private void refreshImageView(final ImageView imageView,String pictureUrl){

        new GetPicture(pictureUrl, new GetPicture.SuccessCallback() {
            @Override
            public void onSuccess(Bitmap result) {

                imageView.setImageBitmap(result);

                switch (imageView.getId()){

                    case R.id.min_ImageView:
                        mMinProgressBar.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.day_ImageView:
                        mDayProgressBar.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.week_ImageView:
                        mWeekProgressBar.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.month_ImageView:
                        mMonthProgressBar.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        }, new GetPicture.FailCallback() {
            @Override
            public void onFail() {

            }
        });
    }
}
