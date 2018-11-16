package com.tenutohahwi.hahwiportfolio.idea;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.http.HttpService;
import com.tenutohahwi.hahwiportfolio.lotto.LottoListAdapter;
import com.tenutohahwi.hahwiportfolio.main.MainActivity;
import com.tenutohahwi.hahwiportfolio.qr.CaptureActivityAnyOrientation;
import com.tenutohahwi.hahwiportfolio.util.Application;
import com.tenutohahwi.hahwiportfolio.util.DateUtil;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gun0912.tedbottompicker.TedBottomPicker;


/**
 * @author shyknight_m@naver.com
 * @class SevenSat
 * @date 2016-05-27
 * @see
 */
public class IdeaActivity extends AppCompatActivity
{
    private Activity    activity;
    private Application app;
    private Context     context;
    HttpService httpService;

    @BindView(R.id.llBack)        LinearLayout llBack;
    @BindView(R.id.llRight)       LinearLayout llRight;
    @BindView(R.id.llImagePicker) LinearLayout llImagePicker;
    @BindView(R.id.ivSelectImage) ImageView ivSelectImage;

    @BindView(R.id.llAddChip) LinearLayout llAddChip;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idea_activity);

        ButterKnife.bind(this);

        activity = this;
        app = (Application) getApplication();
        context = getApplicationContext();
        httpService = new HttpService(activity);

    }


    @OnClick(R.id.llImagePicker)
    void imagePickerClick()
    {
        AndPermission.with(this)
                .runtime()
                .permission(
                        Permission.WRITE_EXTERNAL_STORAGE
                )
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(context).setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener()
                        {
                            @Override
                            public void onImageSelected( Uri uri )
                            {
                                String imagePath = uri.getPath();
                                Log.e("m-Log", "ImagePath : "+imagePath);
                                // here is selected uri
                                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                                // 이미지를 상황에 맞게 회전시킨다
                                try {
                                    ExifInterface exif            = new ExifInterface(imagePath);
                                    int           exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                                    int           exifDegree      = exifOrientationToDegrees(exifOrientation);
                                    bitmap = rotate(bitmap, exifDegree);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Glide.with(context).load(bitmap).into(ivSelectImage);
                            }
                        }).create();

                        tedBottomPicker.show(getSupportFragmentManager());
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

    public int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public Bitmap rotate(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);

            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted) {
                    bitmap.recycle();
                    bitmap = converted;
                }
            } catch (OutOfMemoryError ex) {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return bitmap;
    }

    @OnClick(R.id.llAddChip)
    void addChipClick(){
        //Chip chip = new Chip(this);
    }
}
