package com.tenutohahwi.hahwiportfolio.main;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.career.CareerActivity;
import com.tenutohahwi.hahwiportfolio.foodanalysis.FoodAnalysisActivity;
import com.tenutohahwi.hahwiportfolio.http.APIClient;
import com.tenutohahwi.hahwiportfolio.http.APIInterface;
import com.tenutohahwi.hahwiportfolio.http.HttpService;
import com.tenutohahwi.hahwiportfolio.lotto.LottoActivity;
import com.tenutohahwi.hahwiportfolio.make.MakeAppActivity;
import com.tenutohahwi.hahwiportfolio.model.MultipleResource;
import com.tenutohahwi.hahwiportfolio.model.User;
import com.tenutohahwi.hahwiportfolio.model.UserList;
import com.tenutohahwi.hahwiportfolio.pay.PayActivity;
import com.tenutohahwi.hahwiportfolio.weather.WeatherActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private Activity    activity;
    private Application app;
    private Context     context;
    HttpService httpService;

    @BindView(R.id.tvToolbar)    TextView         tvToolbar;
    @BindView(R.id.toolbar)      Toolbar          toolbar;
    @BindView(R.id.llSlideMenu)  LinearLayout     llSlideMenu;
    @BindView(R.id.llSlideClose) LinearLayout     llSlideClose;
    @BindView(R.id.lvSlideMenu)  ListView         lvSlideMenu;
    private                      MainSlideAdapter mainSlideAdapter;

    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;

    @BindView(R.id.llCareer)       LinearLayout llCareer;
    @BindView(R.id.llMakeApp)      LinearLayout llMakeApp;
    @BindView(R.id.llFoodAnalysis) LinearLayout llFoodAnalysis;
    @BindView(R.id.llWeather)      LinearLayout llWeather;
    @BindView(R.id.llLotto)        LinearLayout llLotto;

    @BindView(R.id.ivWeather) ImageView ivWeather;

    @BindView(R.id.tvDescription) TextView tvDescription;

    @BindView(R.id.tvTemp) TextView tvTemp;

    @BindView(R.id.tvHumidity) TextView tvHumidity;

    @BindView(R.id.tvCity) TextView tvCity;

    @BindView(R.id.tvTest) TextView tvTest;

    APIInterface apiInterface;


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
        activity = this;
        app = (Application) getApplication();
        context = getApplicationContext();
        httpService = new HttpService(activity);
        Realm.init(context);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        setSlideListView();

        httpService.getWeatherDataCity(new HttpService.HttpCallback()
        {
            @Override
            public void httpDone( String result )
            {
                if ("Fail".equals(result) || "Exception".equals(result))
                {

                }
                else
                {
                    try
                    {
                        JSONObject jsonRoot         = new JSONObject(result);
                        JSONArray  jsonArrayWeather = new JSONArray(jsonRoot.getString("weather"));
                        for (int i = 0; i < jsonArrayWeather.length(); i++)
                        {
                            JSONObject jObject = jsonArrayWeather.getJSONObject(i);  // JSONObject 추출

                            String weatherIconUrl = "http://openweathermap.org/img/w/" + jObject.getString("icon") + ".png";
                            Glide.with(activity).load(weatherIconUrl).into(ivWeather);

                            tvDescription.setText(jObject.getString("main") + "(" + jObject.getString("description") + ")");
                        }

                        JSONObject jsonObjectMain = new JSONObject(jsonRoot.getString("main"));
                        String     temp           = (jsonObjectMain.getDouble("temp") - 273) + "";
                        temp = temp.substring(0, 4);
                        tvTemp.setText("온도 : " + temp + "℃");

                        tvHumidity.setText("습도 : " + jsonObjectMain.getDouble("humidity") + "%");

                        JSONObject jsonObjectSys = new JSONObject(jsonRoot.getString("sys"));
                        String     country       = jsonObjectSys.getString("country");

                        tvCity.setText(jsonRoot.getString("name") + " " + country);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        });


        //testCode();
    }

    private void testCode()
    {
        /**
         GET List Resources
         **/
        Call<MultipleResource> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<MultipleResource>()
        {
            @Override
            public void onResponse( Call<MultipleResource> call, Response<MultipleResource> response )
            {


                Log.d("TAG", response.code() + "");

                String displayResponse = "";

                MultipleResource             resource   = response.body();
                Integer                      text       = resource.page;
                Integer                      total      = resource.total;
                Integer                      totalPages = resource.totalPages;
                List<MultipleResource.Datum> datumList  = resource.data;

                displayResponse += text + " Page\n" + total + " Total\n" + totalPages + " Total Pages\n";

                for (MultipleResource.Datum datum : datumList)
                {
                    displayResponse += datum.id + " " + datum.name + " " + datum.pantoneValue + " " + datum.year + "\n";
                }

                tvTest.setText(displayResponse);

            }

            @Override
            public void onFailure( Call<MultipleResource> call, Throwable t )
            {
                call.cancel();
            }
        });

        /**
         Create new user
         **/
        User       user  = new User("morpheus", "leader");
        Call<User> call1 = apiInterface.createUser(user);
        call1.enqueue(new Callback<User>()
        {
            @Override
            public void onResponse( Call<User> call, Response<User> response )
            {
                User user1 = response.body();

                Toast.makeText(getApplicationContext(), user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure( Call<User> call, Throwable t )
            {
                call.cancel();
            }
        });

        /**
         GET List Users
         **/
        Call<UserList> call2 = apiInterface.doGetUserList("2");
        call2.enqueue(new Callback<UserList>()
        {
            @Override
            public void onResponse( Call<UserList> call, Response<UserList> response )
            {

                UserList             userList   = response.body();
                Integer              text       = userList.page;
                Integer              total      = userList.total;
                Integer              totalPages = userList.totalPages;
                List<UserList.Datum> datumList  = userList.data;
                Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();

                for (UserList.Datum datum : datumList)
                {
                    Toast.makeText(getApplicationContext(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure( Call<UserList> call, Throwable t )
            {
                call.cancel();
            }
        });


        /**
         POST name and job Url encoded.
         **/
        Call<UserList> call3 = apiInterface.doCreateUserWithField("morpheus", "leader");
        call3.enqueue(new Callback<UserList>()
        {
            @Override
            public void onResponse( Call<UserList> call, Response<UserList> response )
            {
                UserList             userList   = response.body();
                Integer              text       = userList.page;
                Integer              total      = userList.total;
                Integer              totalPages = userList.totalPages;
                List<UserList.Datum> datumList  = userList.data;
                Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();

                for (UserList.Datum datum : datumList)
                {
                    Toast.makeText(getApplicationContext(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure( Call<UserList> call, Throwable t )
            {
                call.cancel();
            }
        });

    }

    private void setSlideListView()
    {
        if (null == mainSlideAdapter)
        {
            mainSlideAdapter = new MainSlideAdapter(context, R.layout.main_slide_item);
        }
        mainSlideAdapter.setData(new MainSlide(getApplicationContext()).setSlideData());
        lvSlideMenu.setAdapter(mainSlideAdapter);
    }

    @OnClick(R.id.llSlideMenu)
    void onSlideMenuClick()
    {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @OnItemClick(R.id.lvSlideMenu)
    void onItemClick( int position )
    {
        Intent intent = new Intent();
        switch (position)
        {
            case 0:
                intent.setClass(context, CareerActivity.class);
                break;
            case 1:
                intent.setClass(context, MakeAppActivity.class);

                break;

            case 2:
                intent.setClass(context, WeatherActivity.class);
                break;

            case 3:
                intent.setClass(context, LottoActivity.class);
                break;
            case 4:
                intent.setClass(context, FoodAnalysisActivity.class);
                break;
            case 5:
                intent.setClass(context, PayActivity.class);
                break;
        }
        startActivity(intent);
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    @OnClick(R.id.llCareer)
    void onCareerClick()
    {
        Intent intent = new Intent();
        intent.setClass(context, CareerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.llMakeApp)
    void onMakeAppClick()
    {
        Intent intent = new Intent();
        intent.setClass(context, MakeAppActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.llFoodAnalysis)
    void onFoodAnalysisClick()
    {
        Intent intent = new Intent();
        intent.setClass(context, FoodAnalysisActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.llWeather)
    void onWeatherClick()
    {
        Intent intent = new Intent();
        intent.setClass(context, WeatherActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.llLotto)
    void onLottoClick()
    {
        Intent intent = new Intent();
        intent.setClass(context, LottoActivity.class);
        startActivity(intent);
    }
}
