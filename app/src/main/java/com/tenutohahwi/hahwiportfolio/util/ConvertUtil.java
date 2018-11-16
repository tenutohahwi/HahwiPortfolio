package com.tenutohahwi.hahwiportfolio.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public class ConvertUtil {

    private static int density;

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static void getDisplayInfo(Activity activity , Context context , Application app) {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;

        // 꼭 넣어 주어야 한다. 이렇게 해야 displayMetrics가 세팅이 된다.

        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //Log.e("m-Log","displayMetrics.density : " + displayMetrics.density);
        //Log.e("m-Log","deviceWidth(px) : " + deviceWidth + ", deviceHeight(px) : " + deviceHeight);

        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);

        // constant 값: DENSITY_LOW 120, DENSITY_MEDIUM 160, DENSITY_HIGH 240
        int density = metrics.densityDpi;
        //Log.e("m-Log","density : " + density);

        int dipWidth = (int) (deviceWidth / displayMetrics.density);
        int dipHeight = (int) (deviceHeight / displayMetrics.density);

        Log.e("m-Log","density : "+density + " dipWidth(dp) : " + dipWidth + ", dipHeight(dp) : " + dipHeight);
        app.pref.put(PreferenceInfo.DEVICE_WIDTH, dipWidth);
        app.pref.put(PreferenceInfo.DEVICE_HEIGHT, dipHeight);
//        Log.e("m-Log","device uuid : " + app.pref.getValue(PreferenceInfo.MEMBER_UUID));
    }

    public static int getDisplayDip(Activity activity , Context context) {

        DisplayMetrics displayMetrics = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;

        // 꼭 넣어 주어야 한다. 이렇게 해야 displayMetrics가 세팅이 된다.
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);

        return metrics.densityDpi;

    }

    public static int getDensityToValue(Activity activity , Context context , int value){
        if(0 == density ) density = getDisplayDip(activity , context);

        if(120 == density) return (int)(value * 0.75f);
        if(160 == density) return (int)(value * 1.0f);
        //T330 추가
        if(213 == density) return (int)(value * 1.325f);

        if(240 == density) return (int)(value * 1.5f);
        if(320 == density) return (int)(value * 2.0f);

        //넥서스 5X
        if(420 == density) return (int)(value * 2.625f);

        if(480 == density) return (int)(value * 3.0f);

        //노트7 추가
        if(560 == density) return (int)(value * 3.5f);

        if(640 == density) return (int)(value * 4.0f);
        return density;
    }


    public static int getDensityToValueToPx(Activity activity , Context context , int value){
        if(0 == density ) density = getDisplayDip(activity , context);

        if(120 == density) return (int)(value / 0.75f);
        if(160 == density) return (int)(value / 1.0f);
        //T330 추가
        if(213 == density) return (int)(value / 1.325f);

        if(240 == density) return (int)(value / 1.5f);
        if(320 == density) return (int)(value / 2.0f);

        //넥서스 5X
        if(420 == density) return (int)(value / 2.625f);

        if(480 == density) return (int)(value / 3.0f);

        //노트7 추가
        if(560 == density) return (int)(value / 3.5f);

        if(640 == density) return (int)(value / 4.0f);
        return density;
    }

    public static String join(String separator, List<String> mList) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (String m: mList) {
            sb.append(m);
            count++;
            if (count < mList.size()) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    public static String join(String[] list) {
        if(null == list || 0 == list.length) return "";

        StringBuilder builder = new StringBuilder();

        for (String n : list) {
            builder.append(n).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();

    }


    public static String join(List<String> list){
        if(null == list || 0 == list.size()) return "";

        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < list.size() ; i++){
            builder.append(list.get(i)).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }


    public static String convertMD5(String str){

        String MD5 = "";

        try{

            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(str.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();

            for(int i = 0 ; i < byteData.length ; i++){

                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));

            }

            MD5 = sb.toString();



        }catch(NoSuchAlgorithmException e){

            e.printStackTrace();

            MD5 = null;

        }

        return MD5;

    }

}
