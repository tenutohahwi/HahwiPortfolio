package com.tenutohahwi.hahwiportfolio.main;

import android.content.Context;

import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.util.Application;

import java.util.ArrayList;

/**
 * @author shyknight_m@naver.com
 * @class MathbankApp
 * @date 2017-01-05
 * @see
 */

public class MainSlide {
    private Context context;
    private ArrayList<MainSlide> slideData;
    private int type;
    private String slideText;
    private Application app;

    public MainSlide(Context context) {
        this.context = context;
        this.app = (Application) this.context.getApplicationContext();
    }

    public MainSlide(int type, String slideText) {
        this.type = type;
        this.slideText = slideText;
    }

    public ArrayList<MainSlide> setSlideData() {
        slideData = new ArrayList<MainSlide>();
        slideData.add(new MainSlide(0, context.getResources().getString(R.string.slide_menu_1)));
        slideData.add(new MainSlide(0, context.getResources().getString(R.string.slide_menu_2)));
        slideData.add(new MainSlide(0, context.getResources().getString(R.string.slide_menu_3)));
        slideData.add(new MainSlide(0, context.getResources().getString(R.string.slide_menu_4)));
        slideData.add(new MainSlide(0, context.getResources().getString(R.string.slide_menu_5)));
        slideData.add(new MainSlide(0, context.getResources().getString(R.string.slide_menu_6)));
        //lideData.add(new MainSlide(2, context.getResources().getString(R.string.slide_menu_setting)));
        return slideData;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSlideText() {
        return slideText;
    }

    public void setSlideText(String slideText) {
        this.slideText = slideText;
    }
}
