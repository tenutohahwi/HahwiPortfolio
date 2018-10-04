package com.tenutohahwi.hahwiportfolio.make;

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

public class MakeAppData {
    private Context context;
    private ArrayList<MakeAppData> makeAppData;
    private int type;
    private String companyName;
    private String appName;
    private String appIcon;
    private String appType;
    private String appExplain;
    private Application app;

    public MakeAppData(Context context) {
        this.context = context;
        this.app = (Application) this.context.getApplicationContext();
    }

    public MakeAppData(int type, String companyName, String appName, String appIcon, String appType, String appExplain) {
        this.type = type;
        this.companyName = companyName;
        this.appName = appName;
        this.appIcon = appIcon;
        this.appType = appType;
        this.appExplain = appExplain;
    }

    public ArrayList<MakeAppData> setMakeAppData() {
        makeAppData = new ArrayList<MakeAppData>();

        //세븐에듀
        makeAppData.add(
                new MakeAppData(0, context.getResources().getString(R.string.company_sevenedu), "", "sevenedu","16.04 ~ 재직중", "Android 개발 - 사원")
        );
        makeAppData.add(
                new MakeAppData(1, context.getResources().getString(R.string.company_sevenedu), context.getResources().getString(R.string.sevenedu_sevenedu), "sevenedu", context.getResources().getString(R.string.app_type_native), context.getResources().getString(R.string.sevenedu_sevenedu_detail))
        );
        makeAppData.add(
                new MakeAppData(1, context.getResources().getString(R.string.company_sevenedu), context.getResources().getString(R.string.sevenedu_mathbank), "mathbank", context.getResources().getString(R.string.app_type_native), context.getResources().getString(R.string.sevenedu_mathbank_detail))
        );
        makeAppData.add(
                new MakeAppData(1, context.getResources().getString(R.string.company_sevenedu), context.getResources().getString(R.string.sevenedu_mathmaticalformula), "mathmaticalformula", context.getResources().getString(R.string.app_type_native), context.getResources().getString(R.string.sevenedu_mathmaticalformula_detail))
        );
        makeAppData.add(
                new MakeAppData(1, context.getResources().getString(R.string.company_sevenedu), context.getResources().getString(R.string.sevenedu_mathdna), "mathdna", context.getResources().getString(R.string.app_type_native), "설명")
        );
        makeAppData.add(
                new MakeAppData(1, context.getResources().getString(R.string.company_sevenedu), context.getResources().getString(R.string.sevenedu_chamathnotice), "chamathnotice", context.getResources().getString(R.string.app_type_hybrid), context.getResources().getString(R.string.sevenedu_chamathnotice_detail))
        );

        makeAppData.add(
                new MakeAppData(1, context.getResources().getString(R.string.company_sevenedu), context.getResources().getString(R.string.sevenedu_schamath), "schamath", context.getResources().getString(R.string.app_type_hybrid), "설명")
        );

        //엔코디
        makeAppData.add(
                new MakeAppData(0, context.getResources().getString(R.string.company_ncodi), "", "ncodi","15.08 ~ 16.04", "Android 개발 - 대리")
        );
        makeAppData.add(
                new MakeAppData(1, context.getResources().getString(R.string.company_ncodi), context.getResources().getString(R.string.ncodi_sotesan), "sotesan", context.getResources().getString(R.string.app_type_hybrid), "설명")
        );
        makeAppData.add(
                new MakeAppData(1, context.getResources().getString(R.string.company_ncodi), context.getResources().getString(R.string.ncodi_mindstudy), "mindstudy", context.getResources().getString(R.string.app_type_native), "설명")
        );
        makeAppData.add(
                new MakeAppData(1, context.getResources().getString(R.string.company_ncodi), context.getResources().getString(R.string.ncodi_woncircle), "woncircle", context.getResources().getString(R.string.app_type_hybrid), "설명")
        );
        makeAppData.add(
                new MakeAppData(1, context.getResources().getString(R.string.company_ncodi), context.getResources().getString(R.string.ncodi_christianmall), "christianmall", context.getResources().getString(R.string.app_type_hybrid), "설명")
        );
        makeAppData.add(
                new MakeAppData(1, context.getResources().getString(R.string.company_ncodi), context.getResources().getString(R.string.ncodi_wems), "wems", context.getResources().getString(R.string.app_type_hybrid), "설명")
        );
        //카배온
        makeAppData.add(
                new MakeAppData(0, context.getResources().getString(R.string.company_kabeon), "", "kabeon","13.03 ~ 15.07", "연구원")
        );

        return makeAppData;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppExplain() {
        return appExplain;
    }

    public void setAppExplain(String appExplain) {
        this.appExplain = appExplain;
    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }
}
