package com.tenutohahwi.hahwiportfolio.http;

import com.tenutohahwi.hahwiportfolio.forecastpojo.Forecast;
import com.tenutohahwi.hahwiportfolio.model.MultipleResource;
import com.tenutohahwi.hahwiportfolio.model.User;
import com.tenutohahwi.hahwiportfolio.model.UserList;
import com.tenutohahwi.hahwiportfolio.pojo.Coord;
import com.tenutohahwi.hahwiportfolio.pojo.Weather;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("api/unknown")
    Call<MultipleResource> doGetListResources();

    @POST("api/users")
    Call<User> createUser(@Body User user);

    @GET("api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);


    @GET("data/2.5/weather?q=Seoul&appid=55c62d0def9e9c75559ba60520f92deb")
    Call<Weather> getWeatherData();

    @GET("data/2.5/weather?appid=55c62d0def9e9c75559ba60520f92deb")
    Call<Weather> getWeatherData(@Query("lat") String lat, @Query("lon") String lon);

    @GET("data/2.5/forecast?appid=55c62d0def9e9c75559ba60520f92deb")
    Call<Forecast> getWeatherDataThreeHour(@Query("lat") String lat, @Query("lon") String lon);
}