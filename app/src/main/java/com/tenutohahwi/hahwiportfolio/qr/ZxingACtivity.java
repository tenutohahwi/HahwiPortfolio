package com.tenutohahwi.hahwiportfolio.qr;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.journeyapps.barcodescanner.CaptureActivity;


/**
 * @author shyknight_m@naver.com
 * @class chamathNotice
 * @date 2018-01-04
 * @see
 */

public class ZxingACtivity extends CaptureActivity
{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//        TextView title_view = new TextView(this);
//        title_view.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
//        title_view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
//        title_view.setPadding(0, 100, 0, 0);
//        title_view.setGravity(Gravity.CENTER_HORIZONTAL);
//        title_view.setTextColor(Color.parseColor("#FF7200"));
//        title_view.setTextSize(30);
//        title_view.setText("바코드 입력화면");
//
//        this.addContentView(title_view, layoutParams);

        Button button = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        //button.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.DIM_AMOUNT_CHANGED, WindowManager.LayoutParams.DIM_AMOUNT_CHANGED));
        button.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        //button.setBackgroundResource(R.drawable.icon_qna_cancel2);
        button.setPadding(0, 10, 10, 0);
        button.setGravity(Gravity.RIGHT);
        button.setTextColor(Color.parseColor("#FFFFFF"));
        button.setTextSize(15);
        button.setText("닫기");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
        this.addContentView(button, layoutParams);
//
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//        //lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
//        lp.addRule(RelativeLayout.ALIGN_RIGHT);
//        ImageButton ibClose = new ImageButton(this);
//        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        layoutParams1.gravity = Gravity.RIGHT;
//        //ibClose.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
//        ibClose.setBackgroundColor(Color.parseColor("#00FFFFFF"));
//        ibClose.setPadding(0, 30, 30, 0);
//        ibClose.setLayoutParams(layoutParams1);
//        ibClose.set
//        //ibClose.setLayoutParams(lp);
//        ibClose.setImageResource(R.drawable.icon_qna_cancel2);
//        ibClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finishDialog.show();
//            }
//        });
//        this.addContentView(ibClose, layoutParams);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //finishDialog.show();
        finish();
        overridePendingTransition(0,0);
    }

}
