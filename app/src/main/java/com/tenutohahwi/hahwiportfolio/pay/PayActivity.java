package com.tenutohahwi.hahwiportfolio.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.http.HttpService;
import com.tenutohahwi.hahwiportfolio.lotto.LottoListAdapter;
import com.tenutohahwi.hahwiportfolio.qr.CaptureActivityAnyOrientation;
import com.tenutohahwi.hahwiportfolio.util.Application;
import com.tenutohahwi.hahwiportfolio.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author shyknight_m@naver.com
 * @class SevenSat
 * @date 2016-05-27
 * @see
 */
public class PayActivity extends AppCompatActivity
{
    private Activity    activity;
    private Application app;
    private Context     context;
    HttpService httpService;

    @BindView(R.id.llBack)  LinearLayout llBack;
    @BindView(R.id.llRight) LinearLayout llRight;

    @BindView(R.id.edtPay)      EditText     edtPay;
    @BindView(R.id.edtDay)      EditText     edtDay;
    @BindView(R.id.llPayButton) LinearLayout llPayButton;

    @BindView(R.id.tvCurrent) TextView tvCurrent;
    @BindView(R.id.tvCalPay)  TextView tvCalPay;
    @BindView(R.id.tvDayPay)  TextView tvDayPay;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_activity);

        ButterKnife.bind(this);

        activity = this;
        app = (Application) getApplication();
        context = getApplicationContext();
        httpService = new HttpService(activity);

        edtDay.setText("30");
        edtPay.setText("300");
    }


    @OnClick(R.id.llBack)
    void backButtonClick()
    {
        finish();
        overridePendingTransition(0, 0);
    }

    @OnClick(R.id.llRight)
    void getlottoNumberFind()
    {
        Intent intent = new Intent();
        //바로 카메라 켜지게
        intent.setClass(context, CaptureActivityAnyOrientation.class);
        startActivity(intent);
    }

    @OnClick(R.id.llPayButton)
    void calPayCurrent()
    {
        //하루일당
        int monthpay = Integer.parseInt(edtPay.getText().toString()) * 10000;
        tvDayPay.setText((monthpay / 30) + "원");

        SimpleDateFormat dt           = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String           sCurrentDate = dt.format(new Date());

        //8시간 근무, 30일 근무 월급/30이 1일급여   /8이 시간당 급여
        tvCurrent.setText(sCurrentDate);


        SimpleDateFormat dateMonthDay = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String           yearMonthDay = dateMonthDay.format(new Date());

        String[] yearMonthDayArray = yearMonthDay.split("-");

        String payDay = yearMonthDayArray[0] + "-" + yearMonthDayArray[1] + "-" + edtDay.getText().toString();
        try
        {
            long diff = DateUtil.diffOfDate(yearMonthDay, payDay);
            //오늘이 월급일이랑 같으면
            if (0 == diff)
            {
                tvCalPay.setText(Integer.parseInt(edtPay.getText().toString()) * 10000 + "원");
            }
            else//오늘이 월급일이 아니면 오늘과 월급일의 차이를 30에서 -해서 구하라

            {
                int workDay = 30 - (int) diff;
                tvCalPay.setText((monthpay / 30) * workDay + "원");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
