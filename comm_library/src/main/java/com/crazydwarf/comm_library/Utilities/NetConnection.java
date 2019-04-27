package com.crazydwarf.comm_library.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetConnection
{
    private static String TAG = "netConnection: ";

    public NetConnection(final String url, final HttpMethod method, final SuccessCallback successCallback, final FailCallback failCallback, final String...kvs ){

        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {

                StringBuffer paramsStr = new StringBuffer();

                for (int i = 0; i <kvs.length ; i+=2) {
                    paramsStr.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
                }

                try{
                    HttpURLConnection uc;

                    switch (method){
                        case POST:
                            uc = (HttpURLConnection)new URL(url).openConnection();
                            System.out.println(uc);
                            // 显示开启请求体
                            uc.setDoInput(true);
                            uc.setDoOutput(true);

                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Constants.DEFAULT_CHARSET));
                            bw.write(paramsStr.toString());
                            bw.flush();
                            bw.close();
                            break;
                        default:
                            uc = (HttpURLConnection) new URL(url+"?"+ paramsStr.toString()).openConnection();
                            break;
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(),Constants.DEFAULT_CHARSET));

                    if(uc.getResponseCode() == 200){
                        String line = null;
                        StringBuffer result = new StringBuffer();
                        while((line=br.readLine())!=null){
                            result.append(line);
                        }
                        return result.toString();
                    } else if( uc.getResponseCode() == 404){
                        Log.i(TAG,"404访问服务器失败");
                    } else if( uc.getResponseCode() == 500 ){
                        Log.i(TAG,"500服务器拒绝访问");
                    }

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {

                if (result!= null) {
                    successCallback.onSuccess(result);
                } else {
                    if(failCallback!=null){
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
