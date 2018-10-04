package com.tenutohahwi.hahwiportfolio.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tenutohahwi.hahwiportfolio.R;


/**
 * Created by 세븐에듀 on 2016-05-04.
 */
public class PopupDialog extends Dialog implements View.OnClickListener {
    private Application app;
    private Activity activity;
    private Context context;
    private LinearLayout llYes, llNo, llTitle, llMidButton;
    private ImageButton ibClose;
    private TextView tvHeader, tvTitle, tvSubTitle;
    private TextView tvYes, tvNo, tvMidButton;
    private String message = "", type = "";
    private Long courseLongID = 0L;


    public interface TouchEventListener {
        void touchYES();

        void touchNO();

        void touchClose();

        void touchMID();
    }

    private TouchEventListener touchEventListener;

    public TouchEventListener getTouchEventListener() {
        return touchEventListener;
    }

    public void setTouchEventListener(TouchEventListener touchEventListener) {
        this.touchEventListener = touchEventListener;
    }

    public PopupDialog(final Activity activity, Context context, String message, String type, long courseLongID) {
        super(activity);
        this.activity = activity;
        this.context = context;
        app = (Application) activity.getApplication();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(true);

        this.message = message;
        this.type = type;
        this.courseLongID = courseLongID;
        setLayout();
    }

    public void setLayout() {
        ibClose = (ImageButton) findViewById(R.id.ibClose);
        ibClose.setOnClickListener(this);

        llYes = (LinearLayout) findViewById(R.id.llYes);
        llYes.setOnClickListener(this);
        llMidButton = (LinearLayout) findViewById(R.id.llMidButton);
        llMidButton.setOnClickListener(this);
        llNo = (LinearLayout) findViewById(R.id.llNo);
        llNo.setOnClickListener(this);

        tvYes = (TextView) findViewById(R.id.tvYes);
        tvNo = (TextView) findViewById(R.id.tvNo);
        tvMidButton = (TextView) findViewById(R.id.tvMidButton);

        llTitle = (LinearLayout) findViewById(R.id.llTitle);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        tvHeader.setText(message);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);

        if ("certify".equals(type)) {
            llMidButton.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
            tvSubTitle.setText("이미지를 삭제하시겠습니까?");
        } else if ("player_more".equals(type)) {
            llTitle.setVisibility(View.GONE);
            tvYes.setText("Q&A 작성");
            tvMidButton.setText("인덱스");
            tvNo.setText("닫기");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibClose:
                if (null != touchEventListener) {
                    touchEventListener.touchClose();
                }
                break;

            case R.id.llYes:
                if (null != touchEventListener) {
                    touchEventListener.touchYES();
                }

                break;
            case R.id.llNo:
                if (null != touchEventListener) {
                    touchEventListener.touchNO();
                }

                break;
            case R.id.llMidButton:
                if (null != touchEventListener) {
                    touchEventListener.touchMID();
                }
                break;
        }
        dismiss();
    }
}
