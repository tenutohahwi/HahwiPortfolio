package com.tenutohahwi.hahwiportfolio.weather;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.forecastpojo.Forecast;
import com.tenutohahwi.hahwiportfolio.http.APIClient;
import com.tenutohahwi.hahwiportfolio.http.APIInterface;
import com.tenutohahwi.hahwiportfolio.http.HttpConnection;
import com.tenutohahwi.hahwiportfolio.http.HttpService;
import com.tenutohahwi.hahwiportfolio.model.MultipleResource;
import com.tenutohahwi.hahwiportfolio.pojo.Coord;
import com.tenutohahwi.hahwiportfolio.pojo.Weather;
import com.tenutohahwi.hahwiportfolio.util.Application;
import com.tenutohahwi.hahwiportfolio.util.GpsInfo;
import com.tenutohahwi.hahwiportfolio.util.PreferenceInfo;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author shyknight_m@naver.com
 * @class SevenSat
 * @date 2016-05-27
 * @see
 */
public class WeatherActivity extends AppCompatActivity {
    private Activity    activity;
    private Application app;
    private Context     context;
    HttpService httpService;
    private HttpConnection httpConn = HttpConnection.getInstance();

    @BindView(R.id.llBack) LinearLayout llBack;

    APIInterface apiInterface;

    @BindView(R.id.ivWeather)      ImageView ivWeather;
    @BindView(R.id.tvDescription)  TextView  tvDescription;
    @BindView(R.id.tvTemp)         TextView  tvTemp;
    @BindView(R.id.tvHumidity)     TextView  tvHumidity;
    @BindView(R.id.tvCity)         TextView  tvCity;
    @BindView(R.id.tvDate1)        TextView  tvDate1;
    @BindView(R.id.ivWeather1)     ImageView ivWeather1;
    @BindView(R.id.tvDescription1) TextView  tvDescription1;
    @BindView(R.id.tvTemp1)        TextView  tvTemp1;
    @BindView(R.id.tvHumidity1)    TextView  tvHumidity1;
    @BindView(R.id.tvDate2)        TextView  tvDate2;
    @BindView(R.id.ivWeather2)     ImageView ivWeather2;
    @BindView(R.id.tvDescription2) TextView  tvDescription2;
    @BindView(R.id.tvTemp2)        TextView  tvTemp2;
    @BindView(R.id.tvHumidity2)    TextView  tvHumidity2;
    @BindView(R.id.tvDate3)        TextView  tvDate3;
    @BindView(R.id.ivWeather3)     ImageView ivWeather3;
    @BindView(R.id.tvDescription3) TextView  tvDescription3;
    @BindView(R.id.tvTemp3)        TextView  tvTemp3;
    @BindView(R.id.tvHumidity3)    TextView  tvHumidity3;
    @BindView(R.id.tvDate4)        TextView  tvDate4;
    @BindView(R.id.ivWeather4)     ImageView ivWeather4;
    @BindView(R.id.tvDescription4) TextView  tvDescription4;
    @BindView(R.id.tvTemp4)        TextView  tvTemp4;
    @BindView(R.id.tvHumidity4)    TextView  tvHumidity4;
    @BindView(R.id.tvDate5)        TextView  tvDate5;
    @BindView(R.id.ivWeather5)     ImageView ivWeather5;
    @BindView(R.id.tvDescription5) TextView  tvDescription5;
    @BindView(R.id.tvTemp5)        TextView  tvTemp5;
    @BindView(R.id.tvHumidity5)    TextView  tvHumidity5;
    @BindView(R.id.tvDate6)        TextView  tvDate6;
    @BindView(R.id.ivWeather6)     ImageView ivWeather6;
    @BindView(R.id.tvDescription6) TextView  tvDescription6;
    @BindView(R.id.tvTemp6)        TextView  tvTemp6;
    @BindView(R.id.tvHumidity6)    TextView  tvHumidity6;
    @BindView(R.id.tvDate7)        TextView  tvDate7;
    @BindView(R.id.ivWeather7)     ImageView ivWeather7;
    @BindView(R.id.tvDescription7) TextView  tvDescription7;
    @BindView(R.id.tvTemp7)        TextView  tvTemp7;
    @BindView(R.id.tvHumidity7)    TextView  tvHumidity7;

    @BindView(R.id.llLocation) LinearLayout llLocation;

    @BindView(R.id.spCity) Spinner            spCity;
    private                CitySpinnerAdapter citySpinnerAdapter;
    List<String>        cityList = new ArrayList<>();
    Map<String, String> cityMap  = new HashMap<>();
    private GpsInfo gps;

    @BindView(R.id.llThreeHourWeather) LinearLayout llThreeHourWeather;
    @BindView(R.id.chart)              LineChart    chart;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        ButterKnife.bind(this);

        activity = this;
        app = (Application) getApplication();
        context = getApplicationContext();
        apiInterface = APIClient.getWeather().create(APIInterface.class);
        //        HttpService httpService = new HttpService(context);
        //
        //        httpService.getWeatherDataCity(new HttpService.HttpCallback() {
        //            @Override
        //            public void httpDone(String ids) {
        //
        //            }
        //        });

        //testSeoulCity();
        httpService = new HttpService(context);

        llThreeHourWeather.setVisibility(View.GONE);
        setCitySpinnerAdapter();
    }

    void testSeoulCity() {
        /**
         GET List Resources
         **/
        Call<Weather> call = apiInterface.getWeatherData();
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse( Call<Weather> call, Response<Weather> response ) {
                //Log.e("m-Log", "testSeoulCity response code : " + response.code() + "");
                String displayResponse = "";

                Weather resource = response.body();
                Double  lon      = resource.getCoord().getLon();
                Double  lat      = resource.getCoord().getLat();

                displayResponse += "lon : " + lon + " lat : " + lat;

                Log.e("m-Log", "displayResponse : " + displayResponse);
                //tvTest.setText(displayResponse);

            }

            @Override
            public void onFailure( Call<Weather> call, Throwable t ) {
                call.cancel();
            }
        });
    }

    //현재 날씨정보
    void testlocation( String lat, String lon ) {
        Log.e("m-Log", "좌표로 날씨 가져오기 lat : " + lat + " lon : " + lon);
        /**
         GET List Resources
         **/
        Call<Weather> call = apiInterface.getWeatherData(lat, lon);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse( Call<Weather> call, Response<Weather> response ) {
                //Log.e("m-Log", "response code : " + response.code() + "");

                String displayResponse = "";

                Weather resource = response.body();
                Double  lon      = resource.getCoord().getLon();
                Double  lat      = resource.getCoord().getLat();

                displayResponse += "lon : " + lon + " lat : " + lat;

                //Log.e("m-Log", "displayResponse : " + displayResponse);
                //tvTest.setText(displayResponse);
                String weatherIconUrl = "http://openweathermap.org/img/w/" + resource.getWeather().get(0).getIcon() + ".png";
                Glide.with(activity).load(weatherIconUrl).into(ivWeather);

                tvDescription.setText(resource.getWeather().get(0).getMain() + "(" + resource.getWeather().get(0).getDescription() + ")");

                String temp = (resource.getMain().getTemp() - 273) + "";
                temp = temp.substring(0, 4);
                tvTemp.setText("온도 : " + temp + "℃");

                tvHumidity.setText("습도 : " + resource.getMain().getHumidity() + "%");

                String country = resource.getSys().getCountry();

                tvCity.setText(resource.getName() + " " + country);

                locationThreeHour(lat + "", lon + "");
            }

            @Override
            public void onFailure( Call<Weather> call, Throwable t ) {
                call.cancel();
            }
        });
    }

    //3시간 날씨정보
    void locationThreeHour( String lat, String lon ) {
//        httpConn.getWeatherDataThreeHour(lat, lon, new okhttp3.Callback() {
//            @Override
//            public void onFailure( okhttp3.Call call, IOException e ) {
//                Log.e("m-Log", "날씨, fail " + e.getMessage());
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse( okhttp3.Call call, okhttp3.Response response ) throws IOException {
//                final String result = response.body().string();
//                Log.e("m-Log", "날씨, result " + result);
//                new Handler(Looper.getMainLooper()).post(new Runnable() { //쓰레드 에러 나면 꼭써라
//                    @Override
//                    public void run() {
//
//                        //setThreeDataLayout(result);
//                    }
//                });
//            }
//        });
//
//        tvDescription1.setVisibility(View.GONE);
//        tvDescription2.setVisibility(View.GONE);
//        tvDescription3.setVisibility(View.GONE);
//        tvDescription4.setVisibility(View.GONE);
//        tvDescription5.setVisibility(View.GONE);
//        tvDescription6.setVisibility(View.GONE);
//        tvDescription7.setVisibility(View.GONE);
//        llThreeHourWeather.setVisibility(View.VISIBLE);

        httpService.getWeatherDataThreeHour(lat, lon, new HttpService.HttpCallback() {
            @Override
            public void httpDone( String result ) {
                if ("Fail".equals(result) || "Exception".equals(result)) {

                }
                else {
                    setThreeDataLayout(result);
                }
                tvDescription1.setVisibility(View.GONE);
                tvDescription2.setVisibility(View.GONE);
                tvDescription3.setVisibility(View.GONE);
                tvDescription4.setVisibility(View.GONE);
                tvDescription5.setVisibility(View.GONE);
                tvDescription6.setVisibility(View.GONE);
                tvDescription7.setVisibility(View.GONE);
                llThreeHourWeather.setVisibility(View.VISIBLE);

            }
        });
    }

    void setCitySpinnerAdapter() {
        cityList.add("서울시");
        cityList.add("인천광역시");
        cityList.add("광주광역시");
        cityList.add("대구광역시");
        cityList.add("울산광역시");
        cityList.add("대전광역시");
        cityList.add("부산광역시");
        cityList.add("경기도");
        cityList.add("강원도");
        cityList.add("충청남도");
        cityList.add("충청북도");
        cityList.add("경상북도");
        cityList.add("경상남도");
        cityList.add("전라북도");
        cityList.add("전라남도");
        cityList.add("제주도");

        cityMap.put("서울시", "37.540705,126.956764");
        cityMap.put("인천광역시", "37.469221,126.573234");
        cityMap.put("광주광역시", "35.126033,126.831302");
        cityMap.put("대구광역시", "35.798838,128.583052");
        cityMap.put("울산광역시", "35.519301,129.239078");
        cityMap.put("대전광역시", "36.321655,127.378953");
        cityMap.put("부산광역시", "35.198362,129.053922");
        cityMap.put("경기도", "37.567167,127.190292");
        cityMap.put("강원도", "37.555837,128.209315");
        cityMap.put("충청남도", "36.557229,126.779757");
        cityMap.put("충청북도", "36.628503,127.929344");
        cityMap.put("경상북도", "36.248647,128.664734");
        cityMap.put("경상남도", "35.259787,128.664734");
        cityMap.put("전라북도", "35.716705,127.144185");
        cityMap.put("전라남도", "34.819400,126.893113");
        cityMap.put("제주도", "33.364805,126.542671");
        citySpinnerAdapter = new CitySpinnerAdapter(getApplicationContext(), R.layout.default_spinner_item, cityList);
        spCity.setAdapter(citySpinnerAdapter);
    }


    //    @OnItemClick(R.id.spCity)
    //    void onItemClick(int position) {
    //        //citySpinnerAdapter.setSelectedPosition(position);
    //
    //    }

    @OnItemSelected(R.id.spCity)
    public void spinnerItemSelected( Spinner spinner, int position ) {
        // code here
        Log.e("m-Log", "position : " + position);
        Log.e("m-Log", "city : " + cityList.get(position));
        Log.e("m-Log", "cityMap : " + cityMap.get(cityList.get(position)));

        String[] locationArray = cityMap.get(cityList.get(position)).split(",");

        double latitude  = Double.parseDouble(locationArray[0]);
        double longitude = Double.parseDouble(locationArray[1]);
        testlocation(latitude + "", longitude + "");
    }

    @OnClick(R.id.llLocation)
    void getLocation() {
        AndPermission.with(this).runtime().permission(Permission.ACCESS_FINE_LOCATION).onGranted(new Action<List<String>>() {
            @Override
            public void onAction( List<String> data ) {
                gps = new GpsInfo(WeatherActivity.this);
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    double latitude  = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    //Toast.makeText(getApplicationContext(), "당신의 위치 - 위도: " + latitude + " 경도: " + longitude, Toast.LENGTH_LONG).show();
                    Log.e("m-Log", "당신의 위치 - 위도: " + latitude + " 경도: " + longitude);
                    if (0.0 == latitude && 0.0 == longitude) {
                        gps.getLatitude();
                        gps.getLongitude();
                    }

                    testlocation(latitude + "", longitude + "");
                }
                else {
                    // GPS 를 사용할수 없으므로
                    //gps.showSettingsAlert();
                    Log.e("m-Log", "no gps");
                }
            }
        }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction( List<String> data ) {
                if (AndPermission.hasAlwaysDeniedPermission(getApplicationContext(), data)) {

                }
                for (int i = 0; i < data.size(); i++) {
                    Log.e("m-Log", "Permission Denied : " + data.get(i));
                    Toast.makeText(getApplicationContext(), "권한 승인이 필요합니다.\n위치 정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    @OnClick(R.id.llBack)
    void backButtonClick() {
        finish();
        overridePendingTransition(0, 0);
    }

    void setThreeDataLayout(String result){
        List<Entry>       entries = new ArrayList<Entry>();
        ArrayList<String> labels  = new ArrayList<String>();
        try {
            JSONObject jsonRoot  = new JSONObject(result);
            JSONArray  jsonArray = new JSONArray(jsonRoot.getString("list"));
            int        flag      = -1;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String     temp       = "";
                String[]   dateTime   = jsonObject.getString("dt_txt").split("-");
                String     date       = dateTime[1] + "-" + dateTime[2];
                if (0 == i) {
                    tvDate1.setText(date);
                    JSONArray  jsonArrayWeather = new JSONArray(jsonObject.getString("weather"));
                    JSONObject jObject          = jsonArrayWeather.getJSONObject(0);  // JSONObject 추출
                    String     weatherIconUrl   = "http://openweathermap.org/img/w/" + jObject.getString("icon") + ".png";
                    Glide.with(activity).load(weatherIconUrl).into(ivWeather1);
                    tvDescription1.setText(jObject.getString("main") + "(" + jObject.getString("description") + ")");
                    JSONObject jsonObjectMain = new JSONObject(jsonObject.getString("main"));
                    temp = (jsonObjectMain.getDouble("temp") - 273) + "";
                    temp = temp.substring(0, 4);
                    tvTemp1.setText("온도 : " + temp + "℃");
                    tvHumidity1.setText("습도 : " + jsonObjectMain.getInt("humidity") + "%");

                }
                else if (1 == i) {
                    tvDate2.setText(date);
                    JSONArray  jsonArrayWeather = new JSONArray(jsonObject.getString("weather"));
                    JSONObject jObject          = jsonArrayWeather.getJSONObject(0);  // JSONObject 추출
                    String     weatherIconUrl   = "http://openweathermap.org/img/w/" + jObject.getString("icon") + ".png";
                    Glide.with(activity).load(weatherIconUrl).into(ivWeather2);
                    tvDescription2.setText(jObject.getString("main") + "(" + jObject.getString("description") + ")");
                    JSONObject jsonObjectMain = new JSONObject(jsonObject.getString("main"));
                    temp = (jsonObjectMain.getDouble("temp") - 273) + "";
                    temp = temp.substring(0, 4);
                    tvTemp2.setText("온도 : " + temp + "℃");
                    tvHumidity2.setText("습도 : " + jsonObjectMain.getInt("humidity") + "%");
                }
                else if (2 == i) {
                    tvDate3.setText(date);
                    JSONArray  jsonArrayWeather = new JSONArray(jsonObject.getString("weather"));
                    JSONObject jObject          = jsonArrayWeather.getJSONObject(0);  // JSONObject 추출
                    String     weatherIconUrl   = "http://openweathermap.org/img/w/" + jObject.getString("icon") + ".png";
                    Glide.with(activity).load(weatherIconUrl).into(ivWeather3);
                    tvDescription3.setText(jObject.getString("main") + "(" + jObject.getString("description") + ")");
                    JSONObject jsonObjectMain = new JSONObject(jsonObject.getString("main"));
                    temp = (jsonObjectMain.getDouble("temp") - 273) + "";
                    temp = temp.substring(0, 4);
                    tvTemp3.setText("온도 : " + temp + "℃");
                    tvHumidity3.setText("습도 : " + jsonObjectMain.getInt("humidity") + "%");
                }
                else if (3 == i) {
                    tvDate4.setText(date);
                    JSONArray  jsonArrayWeather = new JSONArray(jsonObject.getString("weather"));
                    JSONObject jObject          = jsonArrayWeather.getJSONObject(0);  // JSONObject 추출
                    String     weatherIconUrl   = "http://openweathermap.org/img/w/" + jObject.getString("icon") + ".png";
                    Glide.with(activity).load(weatherIconUrl).into(ivWeather4);
                    tvDescription4.setText(jObject.getString("main") + "(" + jObject.getString("description") + ")");
                    JSONObject jsonObjectMain = new JSONObject(jsonObject.getString("main"));
                    temp = (jsonObjectMain.getDouble("temp") - 273) + "";
                    temp = temp.substring(0, 4);
                    tvTemp4.setText("온도 : " + temp + "℃");
                    tvHumidity4.setText("습도 : " + jsonObjectMain.getInt("humidity") + "%");
                }
                else if (4 == i) {
                    tvDate5.setText(date);
                    JSONArray  jsonArrayWeather = new JSONArray(jsonObject.getString("weather"));
                    JSONObject jObject          = jsonArrayWeather.getJSONObject(0);  // JSONObject 추출
                    String     weatherIconUrl   = "http://openweathermap.org/img/w/" + jObject.getString("icon") + ".png";
                    Glide.with(activity).load(weatherIconUrl).into(ivWeather5);
                    tvDescription5.setText(jObject.getString("main") + "(" + jObject.getString("description") + ")");
                    JSONObject jsonObjectMain = new JSONObject(jsonObject.getString("main"));
                    temp = (jsonObjectMain.getDouble("temp") - 273) + "";
                    temp = temp.substring(0, 4);
                    tvTemp5.setText("온도 : " + temp + "℃");
                    tvHumidity5.setText("습도 : " + jsonObjectMain.getInt("humidity") + "%");
                }
                else if (5 == i) {
                    tvDate6.setText(date);
                    JSONArray  jsonArrayWeather = new JSONArray(jsonObject.getString("weather"));
                    JSONObject jObject          = jsonArrayWeather.getJSONObject(0);  // JSONObject 추출
                    String     weatherIconUrl   = "http://openweathermap.org/img/w/" + jObject.getString("icon") + ".png";
                    Glide.with(activity).load(weatherIconUrl).into(ivWeather6);
                    tvDescription6.setText(jObject.getString("main") + "(" + jObject.getString("description") + ")");
                    JSONObject jsonObjectMain = new JSONObject(jsonObject.getString("main"));
                    temp = (jsonObjectMain.getDouble("temp") - 273) + "";
                    temp = temp.substring(0, 4);
                    tvTemp6.setText("온도 : " + temp + "℃");
                    tvHumidity6.setText("습도 : " + jsonObjectMain.getInt("humidity") + "%");
                }
                else if (6 == i) {
                    tvDate7.setText(date);
                    JSONArray  jsonArrayWeather = new JSONArray(jsonObject.getString("weather"));
                    JSONObject jObject          = jsonArrayWeather.getJSONObject(0);  // JSONObject 추출
                    String     weatherIconUrl   = "http://openweathermap.org/img/w/" + jObject.getString("icon") + ".png";
                    Glide.with(activity).load(weatherIconUrl).into(ivWeather7);
                    tvDescription7.setText(jObject.getString("main") + "(" + jObject.getString("description") + ")");
                    JSONObject jsonObjectMain = new JSONObject(jsonObject.getString("main"));
                    temp = (jsonObjectMain.getDouble("temp") - 273) + "";
                    temp = temp.substring(0, 4);
                    tvTemp7.setText("온도 : " + temp + "℃");
                    tvHumidity7.setText("습도 : " + jsonObjectMain.getInt("humidity") + "%");
                }

                if (i > 6) {
                }
                else {
                    //Log.e("m-Log", "temp : "+temp);
                    if (temp.contains(".")) {
                        String[] temps = temp.split("\\.");
                        //Log.e("m-Log", "temp111 : "+temps[0]);
                        entries.add(new Entry(i, Float.parseFloat(temps[0])));
                    }
                    else {
                        //Log.e("m-Log", "temp222 : "+temp);
                        entries.add(new Entry(i, Float.parseFloat(temp)));
                    }
                    //entries.add(new Entry(Float.parseFloat(i+""), i));
                    labels.add(jsonObject.getString("dt_txt"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.e("m-Log", "entry size : " + entries.size());

        LineDataSet linedataset = new LineDataSet(entries, "온도");
        //linedataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        LineData data = new LineData(linedataset);
        /*dataset.setDrawCubic(true); //선 둥글게 만들기
        dataset.setDrawFilled(true); //그래프 밑부분 색칠*/

        chart.animateY(5000);
        chart.setData(data);
        chart.setBackgroundColor(Color.TRANSPARENT);
        chart.setDrawGridBackground(false);

        chart.invalidate(); // refresh
    }

}
