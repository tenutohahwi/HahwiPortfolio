package com.tenutohahwi.hahwiportfolio.http;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.model.HahwiPortfolioUrl;
import com.tenutohahwi.hahwiportfolio.util.Application;
import com.tenutohahwi.hahwiportfolio.util.PreferenceInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;


public class HttpService {
    private Activity activity;
    private Context context;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public interface HttpCallback {
        void httpDone(String ids);
    }

    public interface UpdateCallback {
        void updateDone();
    }

    public HttpService(Context context) {
        this.context = context;
    }


    public void getWeatherDataCity(final HttpCallback callback) {
        RequestParams params = new RequestParams();
        params.put("q", "seoul");
        params.put("APPID", "55c62d0def9e9c75559ba60520f92deb");
        HttpClient.get(HahwiPortfolioUrl.OPEN_WEATHER_MAP, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("m-Log", "getWeatherDataCity fail : "+responseString);
                callback.httpDone("Fail");
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                try {
                    Log.e("m-Log", "getWeatherDataCity result : " + responseString);
                    callback.httpDone(responseString);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("m-Log", "getWeatherDataCity exception : " + e.getMessage());
                    callback.httpDone("Exception");
                }
            }

        });
    }


    public void getWeatherDataThreeHour(String lat, String lon, final HttpCallback callback) {
        RequestParams params = new RequestParams();
        params.put("lat",lat);
        params.put("lon", lon);

        Log.e("m-Log", "getWeatherDataThreeHour param lat : "+lat + " lon : "+lon + " url : "+HahwiPortfolioUrl.OPEN_WEATHER_MAP_FORECAST);
        HttpClient.get(HahwiPortfolioUrl.OPEN_WEATHER_MAP_FORECAST, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("m-Log", "getWeatherDataThreeHour fail : "+responseString);
                callback.httpDone("Fail");
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                try {
                    Log.e("m-Log", "getWeatherDataThreeHour result : " + responseString);
                    callback.httpDone(responseString);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("m-Log", "getWeatherDataThreeHour exception : " + e.getMessage());
                    callback.httpDone("Exception");
                }
            }

        });
    }

    public void getLottoWinNumber(String number, final HttpCallback callback) {
        RequestParams params = new RequestParams();
        params.put("method", "getLottoNumber");
        params.put("drwNo", number);

        HttpClient.get(HahwiPortfolioUrl.LOTTO_WIN_NUMBER, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("m-Log", "getLottoWinNumber fail : "+responseString);
                callback.httpDone("Fail");
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                try {
                    Log.e("m-Log", "getLottoWinNumber result : " + responseString);
                    callback.httpDone(responseString);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("m-Log", "getLottoWinNumber exception : " + e.getMessage());
                    callback.httpDone("Exception");
                }
            }

        });
    }

//    /**
//     * @param result
//     * @author flowerdance@sevenedu.net
//     * @date 2016.11.04
//     * @see 1 아이템 입력
//     */
//    public void InsertDBItem(String result, int folderId) {
//        try {
//            //Folder folder = Folder.findById(Folder.class, "folder_id = ?", folderId+"");
//            //Log.e("m-Log", "folderid : "+folderId + " folder : "+folder.getFolderTitle());
//            JSONObject jsonRoot = new JSONObject(result);
//            JSONArray jsonArrayItem = new JSONArray(jsonRoot.getString("item"));
//
//            for (int i = 0; i < jsonArrayItem.length(); i++) {
//                JSONObject jObject = jsonArrayItem.getJSONObject(i);  // JSONObject 추출
//
//                Item item = new Item(jObject);
//                //item.setFolderId(folderId);
//
//                //List<Item> list = Item.find(Item.class, "item_id = ? and folder_id = ?", item.getItemIdVal(), folderId + "");
//                List<Item> list = Item.find(Item.class, "item_id = ? ", item.getItemId());
//                if (null == list || 0 == list.size()) {
//                    item.saveAsync(null);
//                } else {
//                    if (1 < list.size()) {
//                        for (int j = 0; j < list.size(); j++) {
//                            if (0 == j) {
//                                list.get(0).update(item).save();
//                                // Log.e("m-Log", "Item DB update : " + item.getItemId() + " folderid : " + folderId + " j =  : "+j);
//                            } else {
//                                list.get(j).delete();
//                                // Log.e("m-Log", "Item DB delete : " + item.getItemId() + " folderid : " + folderId + " j =  : "+j);
//                            }
//                        }
//                    } else {
//                        list.get(0).update(item).save();
//                    }
//
//                }
//            }
//        } catch (Exception e) {
//            Log.e("m-Log", "Item DB insert Exception : " + e.getMessage() + " -- " + e.getStackTrace()[0].getLineNumber());
//            e.printStackTrace();
////            Folder folder = Folder.findById(Folder.class, "folder_id", folderId + "");
////            if (null != folder) {
////                folder.setHasFile(false);
////                folder.save();
////            }
//        }
//
//    }


}
