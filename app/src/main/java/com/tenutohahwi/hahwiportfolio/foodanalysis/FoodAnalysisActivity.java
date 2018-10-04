package com.tenutohahwi.hahwiportfolio.foodanalysis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.http.HttpService;
import com.tenutohahwi.hahwiportfolio.lotto.LottoListAdapter;
import com.tenutohahwi.hahwiportfolio.main.MainActivity;
import com.tenutohahwi.hahwiportfolio.qr.CaptureActivityAnyOrientation;
import com.tenutohahwi.hahwiportfolio.realmvo.DBHelper;
import com.tenutohahwi.hahwiportfolio.util.Application;
import com.tenutohahwi.hahwiportfolio.util.DateUtil;
import com.tenutohahwi.hahwiportfolio.weather.CitySpinnerAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;


/**
 * @author shyknight_m@naver.com
 * @class SevenSat
 * @date 2016-05-27
 * @see
 */
public class FoodAnalysisActivity extends AppCompatActivity
{
    private Activity    activity;
    private Application app;
    private Context     context;
    HttpService httpService;

    @BindView(R.id.llBack)  LinearLayout llBack;
    @BindView(R.id.llRight) LinearLayout llRight;

    private final int FOOD_BARCODE = 1;

    @BindView(R.id.llButton) LinearLayout           llButton;
    @BindView(R.id.loading)  AVLoadingIndicatorView loading;
    @BindView(R.id.lvFood)   ListView               lvFood;

    @BindView(R.id.tvBarcode)   TextView tvBarcode;
    @BindView(R.id.edtName)     EditText edtName;
    @BindView(R.id.edtCompany)  EditText edtCompany;
    @BindView(R.id.edtType)     EditText edtType;
    @BindView(R.id.edtCapacity) EditText edtCapacity;
    @BindView(R.id.spCapacity)  Spinner  spCapacity;
    List<String> capacityUnitList = new ArrayList<>();
    CapacityUnitSpinnerAdapter capacityUnitSpinnerAdapter;
    @BindView(R.id.edtCal) EditText edtCal;

    private DatabaseReference mPostReference;

    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<String> arrayData  = new ArrayList<String>();

    private FoodAnalysisBarcodeListAdapter foodAnalysisBarcodeListAdapter;
    String sort = "id";

    String id       = "";
    String name     = "알로에 수딩 젤";
    String company  = "엔프라니(주)";
    String type     = "수딩젤";
    String capacity = "250ml";
    int    cal      = 0;

    @BindView(R.id.llInsert)       LinearLayout llInsert;
    @BindView(R.id.llUpdate)       LinearLayout llUpdate;
    @BindView(R.id.llBarcodeInput) LinearLayout llBarcodeInput;

    @BindView(R.id.llBarcodeList) LinearLayout llBarcodeList; //바코드 목록 보러가기

    //calendar
    @BindView(R.id.llCalendarPrevMonth) LinearLayout            llCalendarPrevMonth;
    @BindView(R.id.llCalendarNextMonth) LinearLayout            llCalendarNextMonth;
    @BindView(R.id.tvCalendarMonth)     TextView                tvCalendarMonth;
    @BindView(R.id.gvCalendar)          GridView                gvCalendar;
    private                             CalendarGridViewAdapter calendarGridViewAdapter;

    private SimpleDateFormat sdf               = new SimpleDateFormat("MMM yyyy");
    private SimpleDateFormat sdfYM             = new SimpleDateFormat("yyyy-MM");
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

    String sSelectDate = "";
    // current displayed month
    private              Calendar currentDate = Calendar.getInstance();
    // how many days to show, defaults to six weeks, 42 days
    private static final int      DAYS_COUNT  = 42;
    ArrayList<Date> cells = new ArrayList<>();
    private CalendarGridViewAdapter.ViewHolder clickedVIewHolder; //이전에 선택된 달력 홀더
    private int clickedViewHolderPosition = 0; //이전에 선택된 달력 포지션

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodanalysis_activity);

        ButterKnife.bind(this);

        activity = this;
        app = (Application) getApplication();
        context = getApplicationContext();
        httpService = new HttpService(activity);

        loading.setVisibility(View.VISIBLE);
        lvFood.setVisibility(View.GONE);
        getFirebaseDatabase();
        initInput();

        // update title
        updateCalendar();
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar()
    {
        //이번달이면 isCurrnetMonth = true 전달이나 다음달이면 false
        // today
        DateFormat dateFormat     = new SimpleDateFormat("MM");
        Date       date           = new Date();
        int        calendarMonth  = currentDate.get(Calendar.MONTH) + 1;
        String     sCalendarMonth = calendarMonth + "";
        if (2 != sCalendarMonth.length())
        {
            sCalendarMonth = "0" + sCalendarMonth;
        }
        boolean isCurrentMonth = true;
        if (dateFormat.format(date).equals(sCalendarMonth))
        {
            isCurrentMonth = true;
        }
        else
        {
            isCurrentMonth = false;
        }
        //Log.e("m-Log","month : "+dateFormat.format(date) + " -- "+currentDate.get(Calendar.MONTH));
        cells = null;
        cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update title 10월 2017
        tvCalendarMonth.setText(sdf.format(currentDate.getTime()));

        // update grid
        //grid.setAdapter(new CalendarAdapter(getContext(), cells, events));
        if (null == calendarGridViewAdapter)
        {
            calendarGridViewAdapter = new CalendarGridViewAdapter(app, context, R.layout.calendar_item);
        }
        calendarGridViewAdapter.setData(cells);
        calendarGridViewAdapter.setIsCurrentMonth(isCurrentMonth);
        calendarGridViewAdapter.setYearMonth(sdfYM.format(currentDate.getTime()));
        calendarGridViewAdapter.setItemSelected(-1);
        gvCalendar.setAdapter(calendarGridViewAdapter);
        gvCalendar.setOnItemClickListener(gvCalendarItemClickListener);
    }

    private AdapterView.OnItemClickListener gvCalendarItemClickListener = new AdapterView.OnItemClickListener()
    {

        @Override
        public void onItemClick( AdapterView<?> parent, View view, int position, long id )
        {

            CalendarGridViewAdapter.ViewHolder viewHolder = (CalendarGridViewAdapter.ViewHolder) view.getTag();

            //Log.e("m-Log", "position : " + position + " cells : " + cells.get(position));
            calendarGridViewAdapter.setItemSelected(position);
            calendarGridViewAdapter.updateCalendarItemView(viewHolder, cells.get(position), position);
            if (null != clickedVIewHolder)
            {
                calendarGridViewAdapter.updateCalendarItemView(clickedVIewHolder, cells.get(clickedViewHolderPosition), clickedViewHolderPosition);
            }
            //calendarGridViewAdapter.notifyDataSetChanged();
            sSelectDate = mSimpleDateFormat.format(cells.get(position));
            //            tvLearningDay.setText(DateUtil.getMonth(sSelectDate) + "월 " + DateUtil.getDay(sSelectDate) + "일");
            //
            //            setLearningDayListView(true, sSelectDate);
            //            setCertifyListView(true, sSelectDate);
            //            setLearningTime(sSelectDate);
            //            setProgress();
            //            setCoachLayout(sSelectDate);

            //svAll.smoothScrollTo(10, 980);
            clickedViewHolderPosition = position;
            clickedVIewHolder = viewHolder;


            //            //스크롤뷰 smooth
            //            final Handler handler = new Handler();
            //            new Thread(new Runnable() {
            //                @Override
            //                public void run() {
            //                    try {
            //                        Thread.sleep(100);
            //                    } catch (InterruptedException e) {
            //                    }
            //                    handler.post(new Runnable() {
            //                        @Override
            //                        public void run() {
            //                            svAll.smoothScrollTo(0, llAchieve.getTop());
            //                        }
            //                    });
            //                }
            //            }).start();
        }
    };

    public void postFirebaseDatabase( boolean add )
    {
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues   = null;
        if (add)
        {
            FirebasePost post = new FirebasePost(id, name, company, type, capacity, cal);
            postValues = post.toMap();
        }
        childUpdates.put("/barcode/" + id, postValues);
        mPostReference.updateChildren(childUpdates);
    }

    private void setListAdapter()
    {
        if (null == foodAnalysisBarcodeListAdapter)
        {
            foodAnalysisBarcodeListAdapter = new FoodAnalysisBarcodeListAdapter(context, R.layout.foodanalysis_barcode_list_item);
        }
        foodAnalysisBarcodeListAdapter.setData(arrayData);
        lvFood.setAdapter(foodAnalysisBarcodeListAdapter);
        foodAnalysisBarcodeListAdapter.notifyDataSetChanged();
        loading.setVisibility(View.GONE);
        lvFood.setVisibility(View.VISIBLE);
    }

    public void getFirebaseDatabase()
    {
        ValueEventListener postListener = new ValueEventListener()
        {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot )
            {
                //Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                arrayData.clear();
                arrayIndex.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    String       key    = postSnapshot.getKey();
                    FirebasePost get    = postSnapshot.getValue(FirebasePost.class);
                    String[]     info   = { get.id, get.name, get.company, get.type, get.capacity, get.cal + "" };
                    String       Result = info[0] + "-" + info[1] + "-" + info[2] + "-" + info[3] + "-" + info[4] + "-" + info[5] + "kcal";
                    arrayData.add(Result);
                    arrayIndex.add(key);
                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + info[0] + info[1] + info[2] + info[3] + info[4]);
                }
                setListAdapter();
            }

            @Override
            public void onCancelled( DatabaseError databaseError )
            {
                Log.w("getFirebaseDatabase", "loadPost:onCancelled", databaseError.toException());
            }
        };
        Query sortbyAge = FirebaseDatabase.getInstance().getReference().child("barcode").orderByChild(sort);
        sortbyAge.addListenerForSingleValueEvent(postListener);
    }

    @OnClick(R.id.llCalendarPrevMonth)
    void onPrevMonth()
    {
        currentDate.add(Calendar.MONTH, -1);
        updateCalendar();
    }

    @OnClick(R.id.llCalendarNextMonth)
    void onNextMonth()
    {
        currentDate.add(Calendar.MONTH, 1);
        updateCalendar();
    }


    @OnItemClick(R.id.lvFood)
    void onItemClick( int position )
    {
        Log.e("On Click", "position = " + position);
        Log.e("On Click", "Data: " + arrayData.get(position));
        String[] tempData = arrayData.get(position).split("-");
        Log.e("On Click", "Split Result = " + tempData);
        tvBarcode.setText(tempData[0]);
        edtName.setText(tempData[1]);
        edtCompany.setText(tempData[2]);
        edtType.setText(tempData[3]);
        edtCapacity.setText(tempData[4]);
        if (6 == tempData.length)
        {
            edtCal.setText(tempData[5]);
        }
        setCapacitySpinnerAdapter();
        llBarcodeInput.setVisibility(View.VISIBLE);
    }

    void setCapacitySpinnerAdapter()
    {
        capacityUnitList.add("ml");
        capacityUnitList.add("g");
        capacityUnitSpinnerAdapter = new CapacityUnitSpinnerAdapter(getApplicationContext(), R.layout.default_spinner_item, capacityUnitList);
        spCapacity.setAdapter(capacityUnitSpinnerAdapter);
    }

    @OnItemSelected(R.id.spCapacity)
    public void spinnerItemSelected( Spinner spinner, int position )
    {
        // code here
        Log.e("m-Log", "position : " + position);
        Log.e("m-Log", "spCapacity select position : " + position + " data : "+ capacityUnitList.get(position));
    }

    @OnClick(R.id.llButton)
    void testButton()
    {
        id = tvBarcode.getText().toString();
        name = edtName.getText().toString();
        company = edtCompany.getText().toString();
        capacity = edtCapacity.getText().toString();
        type = edtType.getText().toString();
        cal = Integer.parseInt(edtCal.getText().toString());
        if (!IsExistID())
        {
            postFirebaseDatabase(true);
            getFirebaseDatabase();
        }
        else
        {
            Toast.makeText(this, "이미 존재하는 ID 입니다. 다른 ID로 설정해주세요.", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.llInsert)
    void insertButtonClick()
    {
        id = tvBarcode.getText().toString();
        name = edtName.getText().toString();
        company = edtCompany.getText().toString();
        capacity = edtCapacity.getText().toString();
        type = edtType.getText().toString();
        cal = Integer.parseInt(edtCal.getText().toString());
        if (!IsExistID())
        {
            postFirebaseDatabase(true);
            getFirebaseDatabase();
        }
        else
        {
            Toast.makeText(this, "이미 존재하는 ID 입니다. 다른 ID로 설정해주세요.", Toast.LENGTH_LONG).show();
        }

        initInput();
    }

    @OnClick(R.id.llBarcodeList)
    void llBarcodeListClick(){
        Intent intent = new Intent();
        intent.setClass(activity, BarcodeListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.llUpdate)
    void updateButtonClick()
    {
        id = tvBarcode.getText().toString();
        name = edtName.getText().toString();
        company = edtCompany.getText().toString();
        capacity = edtCapacity.getText().toString();
        type = edtType.getText().toString();
        cal = Integer.parseInt(edtCal.getText().toString());
        postFirebaseDatabase(true);
        getFirebaseDatabase();

        initInput();
    }

    public boolean IsExistID()
    {
        boolean IsExist = arrayIndex.contains(id);
        return IsExist;
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
        new IntentIntegrator(this).initiateScan();
    }


    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        //  com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
        //  = 0x0000c0de; // Only use bottom 16 bits
        if (requestCode == IntentIntegrator.REQUEST_CODE)
        {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result == null)
            {
                // 취소됨
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
            else
            {
                // 스캔된 QRCode --> result.getContents()
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Log.e("m-Log", "getBarcodeData : " + result.getFormatName() + " - " + result.getContents());
                id = result.getContents();
                if (!IsExistID())
                {
                    Log.e("m-Log", "상품이 없습니다.\n등록해주세요");
                    tvBarcode.setText(result.getContents());
                    edtName.setText("");
                    edtCompany.setText("");
                    edtType.setText("");
                    edtCapacity.setText("");
                    edtCal.setText("");
                    setCapacitySpinnerAdapter();
                    llBarcodeInput.setVisibility(View.VISIBLE);
                }
                else
                {
                    int position = 0;
                    for (int i = 0; i < arrayData.size(); i++)
                    {
                        if (arrayData.get(i).contains(result.getContents()))
                        {
                            position = i;
                        }
                        //Log.e("m-Log", "i : "+i + " position : "+position + " arrayData.get(i) : "+arrayData.get(i));
                    }
                    String[] tempData = arrayData.get(position).split("-");
                    tvBarcode.setText(tempData[0]);
                    edtName.setText(tempData[1]);
                    edtCompany.setText(tempData[2]);
                    edtType.setText(tempData[3]);
                    edtCapacity.setText(tempData[4]);
                    edtCal.setText(tempData[5]);
                }

                DBHelper dbHelper = new DBHelper();
                dbHelper.getInstance().insertEatFood(result.getContents());
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void initInput()
    {
        tvBarcode.setText("");
        edtName.setText("");
        edtCompany.setText("");
        edtType.setText("");
        edtCapacity.setText("");
        edtCal.setText("");

        llBarcodeInput.setVisibility(View.GONE);
    }
}
