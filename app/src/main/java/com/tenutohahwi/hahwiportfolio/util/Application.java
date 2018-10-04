package com.tenutohahwi.hahwiportfolio.util;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;


public class Application extends android.app.Application {
    //private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    public ClassSharedPreference pref;
    private static Application app;


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
        }
        super.onCreate();
        app = this;
        pref = new ClassSharedPreference(getApplicationContext());

//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManager.createChannel(this);
//        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    //아래와 같이 하면 비정상 종료시 앱을 재실행할 수 있다.
    // uncaught exception handler variable
    private Thread.UncaughtExceptionHandler defaultUEH;

    private final Thread.UncaughtExceptionHandler _unCaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // here I do logging of exception to a db
            PendingIntent myActivity = PendingIntent.getActivity(
                    getApplicationContext(), 192837, new Intent(getApplicationContext(), LauncherActivity.class), PendingIntent.FLAG_ONE_SHOT);

            AlarmManager alarmManager;
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 15000, myActivity);
            System.exit(2);

            // re-throw critical exception further to the os (important)
            defaultUEH.uncaughtException(thread, ex);
        }
    };

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //Glide.get(this).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        //Glide.get(this).trimMemory(level);
    }
//    public void setFirebaseTracker(Activity activity){
//        mFirebaseAnalytics.setCurrentScreen(activity, null, null);
//    }

}