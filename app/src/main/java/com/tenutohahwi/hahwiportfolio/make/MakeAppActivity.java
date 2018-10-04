package com.tenutohahwi.hahwiportfolio.make;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.main.MainSlide;
import com.tenutohahwi.hahwiportfolio.main.MainSlideAdapter;
import com.tenutohahwi.hahwiportfolio.model.HahwiPortfolioUrl;
import com.tenutohahwi.hahwiportfolio.util.Application;
import com.tenutohahwi.hahwiportfolio.util.PreferenceInfo;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemClick;


/**
 * @author shyknight_m@naver.com
 * @class SevenSat
 * @date 2016-05-27
 * @see
 */
public class MakeAppActivity extends AppCompatActivity {
    private Activity activity;
    private Application app;
    private Context context;

    @BindView(R.id.llBack)
    LinearLayout llBack;
    @BindView(R.id.lvMakeApp)
    ListView lvMakeApp;

    private MakeAppListAdapter makeAppListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeapp_activity);

        ButterKnife.bind(this);

        //getSupportActionBar().hide();
        activity = this;
        app = (Application) getApplication();
        context = getApplicationContext();

        setListAdapter();
    }

    private void setListAdapter() {
        if (null == makeAppListAdapter) {
            makeAppListAdapter = new MakeAppListAdapter(context, R.layout.makeapp_list_item);
        }
        makeAppListAdapter.setData(new MakeAppData(getApplicationContext()).setMakeAppData());
        lvMakeApp.setAdapter(makeAppListAdapter);
    }

    @OnClick(R.id.llBack)
    void backButtonClick() {
        finish();
        overridePendingTransition(0, 0);
    }

    @OnItemClick(R.id.lvMakeApp)
    void onItemClick(int position) {
        Uri uri = null;
        switch (position) {
            case 0: //세븐에듀회사설명

                break;
            case 1: //세븐에듀
                uri = Uri.parse(HahwiPortfolioUrl.MARKET_SEVENEDU);
                break;
            case 2: //N제
                uri = Uri.parse(HahwiPortfolioUrl.MARKET_MATHBANK);
                break;
            case 3: //외워지는 수학공식
                uri = Uri.parse(HahwiPortfolioUrl.MARKET_MATHMATICALFORMULA);
                break;
            case 4: //DNA

                break;

            case 5: //차톡
                uri = Uri.parse(HahwiPortfolioUrl.MARKET_CHAMATHNOTICE);
                break;
            case 6: //스마트차수학
                uri = Uri.parse(HahwiPortfolioUrl.MARKET_SCHAMATH);
                break;

            case 7: //엔코디
                break;
            case 8: //소태산마음학교
                uri = Uri.parse(HahwiPortfolioUrl.MARKET_SOTESAN);
                break;
            case 9: //수상한마음공부
                break;
            case 10: //교도수첩
                break;
            case 11: //기독교복지몰
                break;
            case 12: //WEMS
                break;
        }

        if(null != uri){
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, uri);
            marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(marketIntent);
        }
    }
}
