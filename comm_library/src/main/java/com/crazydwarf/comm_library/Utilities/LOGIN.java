package com.crazydwarf.comm_library.Utilities;

import org.json.JSONException;
import org.json.JSONObject;


public class LOGIN
{
    public LOGIN(String phone, String password, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Constants.USERS_DATA_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try
                {
                    JSONObject obj = new JSONObject(result);
                    /*switch (obj.getInt(Constants.KEY_STATUS))
                    {
                        case Constants.KEY_STATUS.RESULT_STATUS_SUCCESS:
                            System.out.println(successCallback);
                            if(successCallback != null){
                                successCallback.onSuccess(obj.getString(Config.KEY_TOKEN));
                            }
                            break;
                        default:
                            if(failCallback!=null){
                                failCallback.onFail();
                            }
                            break;
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(failCallback!=null){
                        failCallback.onFail();
                    }
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if(failCallback!=null){
                    failCallback.onFail();
                }
            }
        },"PHONE_NUM",phone,"PASS_WORD",password);
    }

    public static interface SuccessCallback{
        void onSuccess(String token);
    }

    public static interface FailCallback{
        void onFail();
    }
}
