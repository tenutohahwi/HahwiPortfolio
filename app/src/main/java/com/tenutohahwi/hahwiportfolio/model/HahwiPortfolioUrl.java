package com.tenutohahwi.hahwiportfolio.model;

/**
 * @author shyknight_m@naver.com
 * @class MathBank
 * @date 2016-10-24
 * @see
 */

public class HahwiPortfolioUrl {
    public static final String MARKET_URL = "market://details?id=";
    public static final String MARKET_SEVENEDU = MARKET_URL+"net.sevenedu.sevenedu";
    public static final String MARKET_MATHBANK = MARKET_URL+"net.sevenedu.mathbank";
    public static final String MARKET_MATHMATICALFORMULA = MARKET_URL+"net.sevenedu.mathmaticalformula";
    public static final String MARKET_CHAMATHNOTICE = MARKET_URL+"net.sevenedu.chamathnotice";
    public static final String MARKET_SCHAMATH = MARKET_URL+"net.sevenedu.schamath";

    public static final String MARKET_SOTESAN = MARKET_URL + "kr.co.ncodi.mindschool";

    public static final String OPEN_WEATHER_MAP ="http://api.openweathermap.org/data/2.5/weather";
    public static final String WEATHER_SEOUL = "http://api.openweathermap.org/data/2.5/weather?q=seoul&APPID=55c62d0def9e9c75559ba60520f92deb";
    public static final String OPEN_WEATHER_MAP_FORECAST = "http://api.openweathermap.org/data/2.5/forecast?appid=55c62d0def9e9c75559ba60520f92deb";
    public static final String OPEN_WEATHER_MAP_THREE_HOUR = "http://api.openweathermap.org/data/2.5/forecast";

    public static final String LOTTO_WIN_NUMBER = "http://www.nlotto.co.kr/common.do";

    //3시간 단위 예보
    //api.openweathermap.org/data/2.5/forecast?lat=37.4564883&lon=127.0595305&appid=55c62d0def9e9c75559ba60520f92deb

    //( ex> http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=나의 API Key )
    //http://api.openweathermap.org/data/2.5/weather?lat=[latitude]&lon=[longitude]&APPID=[OPEN_WEATHER_MAP_KEY]
    //weather api key 55c62d0def9e9c75559ba60520f92deb

}

