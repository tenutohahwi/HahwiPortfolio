package com.tenutohahwi.hahwiportfolio.http;


import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tenutohahwi.hahwiportfolio.model.HahwiPortfolioUrl;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpConnection {
    private        OkHttpClient   client;
    private static HttpConnection instance = new HttpConnection();

    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection() { this.client = new OkHttpClient(); }

    //    //슬라이드 메뉴 리스트 데이터 받기
    //    public void requestSlideMenu( Callback callback ) {
    //        Request request = new Request.Builder()
    //                .url(HahwiPortfolioUrl.SLIDE_MENU_URL)
    //                .get()
    //                .build();
    //        client.newCall(request).enqueue(callback);
    //    }

    //    //서버에 이미지 파일 업로드
    //    public void requestFileUpload( List<ImageFileInfo> imageFileArray, Callback callback ) {
    //        RequestBody body = new MultipartBody.Builder()
    //                .setType(MultipartBody.FORM)
    //                .addFormDataPart("file1", imageFileArray.get(0).getFileName(), RequestBody.create(MultipartBody.FORM, new File(imageFileArray.get(0).getImagePath())))
    //                .build();
    //        Request request = new Request.Builder()
    //                .url(AiMathUrl.SLIDE_MENU_URL)
    //                .post(body)
    //                .build();
    //        client.newCall(request).enqueue(callback);
    //    }


    //로그인
    public void getWeatherDataCity( String id, String pwd, Callback callback ) {
        RequestBody body = new FormBody.Builder()
                .add("q", "seoul")
                .add("APPID", "55c62d0def9e9c75559ba60520f92deb")
                .build();

        Request request = new Request.Builder().url(HahwiPortfolioUrl.OPEN_WEATHER_MAP).post(body).build();
        client.newCall(request).enqueue(callback);
    }


    public void getWeatherDataThreeHour( String lat, String lon, final Callback callback ) {
        RequestBody body = new FormBody.Builder()
                .add("lat", lat)
                .add("lon", lon)
                .add("APPID", "55c62d0def9e9c75559ba60520f92deb")
                .build();

        Request request = new Request.Builder().url(HahwiPortfolioUrl.OPEN_WEATHER_MAP_THREE_HOUR).post(body).build();
        client.newCall(request).enqueue(callback);

        Log.e("m-Log", "getWeatherDataThreeHour param lat : " + lat + " lon : " + lon + " url : " + HahwiPortfolioUrl.OPEN_WEATHER_MAP_THREE_HOUR);
    }
}
