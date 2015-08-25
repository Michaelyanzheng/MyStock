package com.zheng.mystock;

import android.os.Environment;

/**
 * Created by michael on 2015/8/21.
 */
public class Config {

    public static final String TAG = "MainActivity";

    public static final int RESULT_INT = 0;
    public static final String RESULT = "result";

    public static final int DATA_INT = 0;
    public static final String DATA = "data";

    public static final int DAPANDATA_INT = 1;
    public static final String GOPICTURE = "gopicture";

    public static final int GOPICTURE_INT = 2;
    public static final String DAPANDATA = "dapandata";

    public static final String GID = "gid";

    public static final String URLSTOCK = "http://web.juhe.cn:8080/finance/stock/hs";

    public static final String KEY = "key";

    public static final String APPKEY = "dd2ea2be8f94515b079238abe59f56f1";

    public static final String QUERY = "query";

    public static final String NAME = "name";

    public static final String CODE = "code";

    public static final String TODAYSTART = "todayStartPri";

    public static final String YESTODEND = "yestodEndPri";

    public static final String TODAYMAX = "todayMax";

    public static final String TODAYMIN = "todayMin";

    public static final String TRANUMBER = "traNumber";

    public static final String TRAAMOUNT = "traAmount";

    public static final String RATE = "rate";

    public static final String NOWPRI = "nowPri";

    public static final String MINURL = "minurl";

    public static final String DAYURL = "dayurl";

    public static final String WEEKURL = "weekurl";

    public static final String MOUNTHRUL = "monthurl";

    public static final String CHARSET = "utf-8";

    public static final String PICTURE_PATH = Environment.getExternalStorageDirectory() + "/download_finance/";

    public static final String MIN = "min";
    public static final String DAILY = "daily";
    public static final String WEEKLY = "weekly";
    public static final String MONTHLY = "monthly";

    public static final String PICTURE_FORMAT = ".png";
}
