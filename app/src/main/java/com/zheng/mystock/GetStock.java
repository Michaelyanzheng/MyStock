package com.zheng.mystock;

/**
 * Created by michael on 2015/8/21.
 */
public class GetStock {

    public GetStock(String stockCode,
                    final SuccessCallback successCallback,
                    final FailCallback failCallback){

        new NetConnection(Config.URLSTOCK, HttpMethod.GET,new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {

                if (successCallback != null){
                    successCallback.onSuccess(result);
                }else{
                    if (failCallback != null){
                        failCallback.onFail();
                    }
                }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

                if (failCallback != null){
                    failCallback.onFail();
                }
            }
        },new String[]{Config.GID,stockCode,Config.KEY,Config.APPKEY});

    }

    public static interface SuccessCallback{
        void onSuccess(String result);
    }

    public static interface FailCallback{
        void onFail();
    }
}
