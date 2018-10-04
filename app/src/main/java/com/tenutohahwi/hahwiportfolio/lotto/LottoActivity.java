package com.tenutohahwi.hahwiportfolio.lotto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.http.APIClient;
import com.tenutohahwi.hahwiportfolio.http.APIInterface;
import com.tenutohahwi.hahwiportfolio.http.HttpService;
import com.tenutohahwi.hahwiportfolio.make.MakeAppData;
import com.tenutohahwi.hahwiportfolio.make.MakeAppListAdapter;
import com.tenutohahwi.hahwiportfolio.pojo.Weather;
import com.tenutohahwi.hahwiportfolio.qr.CaptureActivityAnyOrientation;
import com.tenutohahwi.hahwiportfolio.util.Application;
import com.tenutohahwi.hahwiportfolio.util.DateUtil;
import com.tenutohahwi.hahwiportfolio.util.GpsInfo;
import com.tenutohahwi.hahwiportfolio.weather.CitySpinnerAdapter;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
public class LottoActivity extends AppCompatActivity
{
    private Activity    activity;
    private Application app;
    private Context     context;
    HttpService httpService;

    @BindView(R.id.llBack)        LinearLayout llBack;
    @BindView(R.id.llRight)       LinearLayout llRight;
    @BindView(R.id.llLottoNumber) LinearLayout llLottoNumber;

    @BindView(R.id.lvLotto) ListView         lvLotto;
    private                 LottoListAdapter lottoListAdapter;
    Map<Integer, int[]> lottoMap = new HashMap<>();

    @BindView(R.id.tvNumber)     TextView tvNumber;
    @BindView(R.id.tvWinNumber1) TextView tvWinNumber1;
    @BindView(R.id.tvWinNumber2) TextView tvWinNumber2;
    @BindView(R.id.tvWinNumber3) TextView tvWinNumber3;
    @BindView(R.id.tvWinNumber4) TextView tvWinNumber4;
    @BindView(R.id.tvWinNumber5) TextView tvWinNumber5;
    @BindView(R.id.tvWinNumber6) TextView tvWinNumber6;
    @BindView(R.id.tvBonusNumber) TextView tvBonusNumber;
    @BindView(R.id.tvWinDate)    TextView tvWinDate;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_activity);

        ButterKnife.bind(this);

        activity = this;
        app = (Application) getApplication();
        context = getApplicationContext();
        httpService = new HttpService(activity);

        setLayout();
    }

    private void setLayout()
    {
        String drwNo = DateUtil.getLottoRound();
        Log.e("m-Log", "로또 회차 : "+drwNo);
        tvNumber.setText(drwNo);
        httpService.getLottoWinNumber(drwNo, new HttpService.HttpCallback()
        {
            @Override
            public void httpDone( String result )
            {
                try
                {
                    JSONObject jsonRoot = new JSONObject(result);
                    tvWinDate.setText(jsonRoot.getString("drwNoDate"));
                    tvWinNumber1.setText(jsonRoot.getString("drwtNo" + 1));
                    tvWinNumber2.setText(jsonRoot.getString("drwtNo" + 2));
                    tvWinNumber3.setText(jsonRoot.getString("drwtNo" + 3));
                    tvWinNumber4.setText(jsonRoot.getString("drwtNo" + 4));
                    tvWinNumber5.setText(jsonRoot.getString("drwtNo" + 5));
                    tvWinNumber6.setText(jsonRoot.getString("drwtNo" + 6));
                    tvBonusNumber.setText(jsonRoot.getString("bnusNo"));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Log.e("m-Log", "getLottoWinNumber res : " + result);
            }
        });

    }

    // 해당하는 요일 구하기
    public int doYo(){
        Calendar cal= Calendar.getInstance();
        String strWeek = null;

        int nWeek = cal.get ( Calendar.DAY_OF_WEEK );
        if ( nWeek == 1 ) strWeek="일요일";
        else if ( nWeek == 2 ) strWeek="월요일";
        else if ( nWeek == 3 ) strWeek="화요일";
        else if ( nWeek == 4 ) strWeek="수요일";
        else if ( nWeek == 5 ) strWeek="목요일";
        else if ( nWeek == 6 ) strWeek="금요일";
        else if ( nWeek == 7 ) strWeek="토요일";

        return nWeek;
    }

    @OnClick(R.id.llLottoNumber)
    void randomLottoNumberClick()
    {
        for (int i = 0; i < 5; i++)
        {
            getRandomLottoNumber(i);
        }
        setListAdapter();
    }

    private void getRandomLottoNumber( int count )
    {
        int lottoNumber[] = new int[6];
        for (int i = 0; i < 6; i++)
        {
            lottoNumber[i] = (int) (Math.random() * 45) + 1;
            for (int j = 0; j < i; j++)
            {
                if (lottoNumber[i] == lottoNumber[j])
                {
                    i--;
                    break;
                }
            }

        }

        quickSort(lottoNumber, 0, lottoNumber.length - 1);
        //        for (int i = 0; i < 6; i++)
        //        {
        //            Log.e("m-Log", "i : " + lottoNumber[i] + " count : " + count);
        //        }

        lottoMap.put(count, lottoNumber);
    }


    private void setListAdapter()
    {
        if (null == lottoListAdapter)
        {
            lottoListAdapter = new LottoListAdapter(context, R.layout.lotto_list_item);
        }
        lottoListAdapter.setData(lottoMap);
        lvLotto.setAdapter(lottoListAdapter);
        lottoListAdapter.notifyDataSetChanged();
    }

    //Quick sort
    static int partition( int arr[], int left, int right )
    {
        int pivot = arr[(left + right) / 2];
        while (left < right)
        {
            while ((arr[left] < pivot) && (left < right)) left++;
            while ((arr[right] > pivot) && (left < right)) right--;

            if (left < right)
            {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }
        return left;
    }

    //Quick sort
    public static void quickSort( int arr[], int left, int right )
    {
        if (left < right)
        {
            int pivotNewIndex = partition(arr, left, right);

            quickSort(arr, left, pivotNewIndex - 1);
            quickSort(arr, pivotNewIndex + 1, right);
        }
    }


    @OnClick(R.id.llBack)
    void backButtonClick()
    {
        finish();
        overridePendingTransition(0, 0);
    }

    public static int randomRange( int beforeNumber, int afterNumber )
    {
        return (int) (Math.random() * (afterNumber - beforeNumber + 1)) + beforeNumber;
    }

    @OnClick(R.id.llRight)
    void getlottoNumberFind()
    {
        Intent intent = new Intent();
        //바로 카메라 켜지게
        intent.setClass(context, CaptureActivityAnyOrientation.class);
        startActivity(intent);
    }
}
