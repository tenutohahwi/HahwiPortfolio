package com.tenutohahwi.hahwiportfolio.career;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.make.MakeAppData;
import com.tenutohahwi.hahwiportfolio.make.MakeAppListAdapter;
import com.tenutohahwi.hahwiportfolio.model.HahwiPortfolioUrl;
import com.tenutohahwi.hahwiportfolio.util.Application;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;


/**
 * @author shyknight_m@naver.com
 * @class SevenSat
 * @date 2016-05-27
 * @see
 */
public class CareerActivity extends AppCompatActivity {
    private Activity activity;
    private Application app;
    private Context context;

    @BindView(R.id.llBack)
    LinearLayout llBack;

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    @BindView(R.id.tvPhone)
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.career_activity);

        ButterKnife.bind(this);

        //getSupportActionBar().hide();
        activity = this;
        app = (Application) getApplication();
        context = getApplicationContext();

    }

    @OnClick(R.id.tvPhone)
    void phoneCall() {
        AndPermission.with(this)
                .runtime()
                .permission(
                        Permission.CALL_PHONE
                )
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:01097974719"));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        if (AndPermission.hasAlwaysDeniedPermission(getApplicationContext(), data)) {

                        }
                        for (int i = 0; i < data.size(); i++) {
                            Log.e("m-Log", "Permission Denied : " + data.get(i));
                            Toast.makeText(getApplicationContext(), "권한 승인이 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .start();

    }

    @OnClick(R.id.tvEmail) void sendEmail(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{context.getString(R.string.info_email)});
        //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
        //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, "");
        email.putExtra(Intent.EXTRA_TEXT, "");

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    @OnClick(R.id.llBack)
    void backButtonClick() {
        finish();
        overridePendingTransition(0, 0);
    }

}
