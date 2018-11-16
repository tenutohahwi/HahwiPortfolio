package com.tenutohahwi.hahwiportfolio.vlc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.http.HttpService;
import com.tenutohahwi.hahwiportfolio.qr.CaptureActivityAnyOrientation;
import com.tenutohahwi.hahwiportfolio.util.Application;
import com.tenutohahwi.hahwiportfolio.util.ConvertUtil;
import com.tenutohahwi.hahwiportfolio.util.PreferenceInfo;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

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
public class VlcActivity extends AppCompatActivity implements IVLCVout.Callback {
    private Activity    activity;
    private Application app;
    private Context     context;
    HttpService httpService;

    @BindView(R.id.llBack)  LinearLayout llBack;
    @BindView(R.id.llRight) LinearLayout llRight;

    @BindView(R.id.surface) SurfaceView   surface;
    private                 SurfaceHolder holder;
    // vlc 변수들
    private                 LibVLC        libvlc;
    private                 MediaPlayer   mMediaPlayer = null;
    private                 int           mVideoWidth;
    private                 int           mVideoHeight;

    private String  mFilePath;
    private boolean isRtsp = false;

    @BindView(R.id.flPlayer) FrameLayout flPlayer;

    @BindView(R.id.llPlayerPlayControl) LinearLayout llPlayerPlayControl;
    @BindView(R.id.btnPlaynPause)       Button       btnPlaynPause;
    @BindView(R.id.btnRetour)           Button       btnRetour;
    @BindView(R.id.btnAdvance)          Button       btnAdvance;

    @BindView(R.id.llSeek)      LinearLayout llSeek;
    @BindView(R.id.vlcSeekbar)  SeekBar      vlcSeekbar;
    @BindView(R.id.vlcDuration) TextView     vlcDuration;
    private                     Handler      handlerSeekbar;
    private                     Runnable     runnableSeekbar;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vlc_activity);

        ButterKnife.bind(this);

        activity = this;
        app = (Application) getApplication();
        context = getApplicationContext();
        httpService = new HttpService(activity);

        llRight.setVisibility(View.GONE);

        String movieUrl = "http://sejongdrm.gscdn.com/MathBank/" + "79062" + "_High.mp4";
        initVLCPlayer(movieUrl);

    }

    @OnClick(R.id.llBack)
    void backButtonClick() {
        finish();
        overridePendingTransition(0, 0);
    }

    @OnClick(R.id.llRight)
    void getlottoNumberFind() {
        Intent intent = new Intent();
        //바로 카메라 켜지게
        intent.setClass(context, CaptureActivityAnyOrientation.class);
        startActivity(intent);
    }


    void initVLCPlayer( final String movieUrl ) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideSystemUI();
                //재생할 파일 경로
                mFilePath = "/sdcard/sprite.mp4";
                if (!"".equals(movieUrl)) {
                    mFilePath = movieUrl;
                }
                //mFilePath에 url 경로 지정
                //mFilePath= "rtsp://192.168.25.41:8554/live.ts";
                //rtsp의 경우 true
                //isRtsp = false;
                isRtsp = true;

                //서페이스 연결 작업
                //surface = (SurfaceView) findViewById(R.id.surface);
                holder = surface.getHolder();

                createPlayer(mFilePath, isRtsp);

                //                final int                width  = app.pref.getValue(PreferenceInfo.MOVIE_WIDTH, 0);
                //                final int                height = app.pref.getValue(PreferenceInfo.MOVIE_HEIGHT, 0);
                //                FrameLayout.LayoutParams params;
                //                //params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                //                params = new FrameLayout.LayoutParams(ConvertUtil.getDensityToValue(activity, context, width), ConvertUtil.getDensityToValue(activity, context, height));
                //                //params = new FrameLayout.LayoutParams(width, height);
                //                params.gravity = Gravity.TOP;
                //                // params.topMargin = 60;
                //                params.topMargin = ConvertUtil.getDensityToValue(activity, context, 60);
                //                flPlayer.setLayoutParams(params);
                //                FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                //                surface.setLayoutParams(params1);
                //
                //
                //                Log.e("m-Log", "flPlayer - w : " + flPlayer.getWidth() + " h : " + flPlayer.getHeight());
                //                Log.e("m-Log", "surface - w : " + surface.getWidth() + " h : " + surface.getHeight());

                setVLCControl();
            }
        });

    }

    void setVLCControl() {
        // SEEKBAR
        handlerSeekbar = new Handler();
        runnableSeekbar = new Runnable() {
            @Override
            public void run() {
                if (libvlc != null) {
                    long   curTime    = mMediaPlayer.getTime();
                    long   totalTime  = (long) (curTime / mMediaPlayer.getPosition());
                    int    minutes    = (int) (curTime / (60 * 1000));
                    int    seconds    = (int) ((curTime / 1000) % 60);
                    int    endMinutes = (int) (totalTime / (60 * 1000));
                    int    endSeconds = (int) ((totalTime / 1000) % 60);
                    String duration   = String.format("%02d:%02d / %02d:%02d", minutes, seconds, endMinutes, endSeconds);
                    vlcSeekbar.setProgress((int) (mMediaPlayer.getPosition() * 100));
                    vlcDuration.setText(duration);
                }
                handlerSeekbar.postDelayed(runnableSeekbar, 1000);
            }
        };

        runnableSeekbar.run();
        vlcSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged( SeekBar seekBar, int i, boolean b ) {
                Log.e("m-Log", "onProgressChanged pos is : " + i + " getposition : " + mMediaPlayer.getPosition() + " time : " + mMediaPlayer.getTime());
                //if (i != 0)
                //    libvlc.setPosition(((float) i / 100.0f));
            }

            @Override
            public void onStartTrackingTouch( SeekBar seekBar ) {
                Log.e("m-Log", "onStartTrackingTouch : " + seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch( SeekBar seekBar ) {
                Log.e("m-Log", "onStopTrackingTouch : " + seekBar.getProgress());

            }
        });
    }

    //시스템 UI 표시
    private void showSystemUI() {
        //        llTop.setSystemUiVisibility(
        //                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        //                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        //                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    //전체 화면
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        //        llTop.setSystemUiVisibility(
        //                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        //                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        //                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        //                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
        //                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
        //                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }


    /*
     *  동영상 플레이어 시작
     *  String mediaPath : 파일 경로
     */
    private void createPlayer( String mediaPath, boolean isURL ) {
        //플레이어가 있다면 종료(제거)
        flPlayer.setVisibility(View.VISIBLE);
        surface.setVisibility(View.VISIBLE);
        releasePlayer();
        Log.d("m-Log", "createPlayer");
        try {
            // 미디어 파일 경로 메시지 풍선으로 화면에 표시
            //            if (mediaPath.length() > 0) {
            //                Toast toast = Toast.makeText(this, mediaPath, Toast.LENGTH_LONG);
            //                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
            //                        0);
            //                toast.show();
            //            }


            //libvlc 생성
            // 옵션 추가 하기
            // 다른 옵션 추가시 여기에 add로 추가해주면 됨.
            ArrayList<String> options = new ArrayList<String>();
            //options.add("--subsdec-encoding <encoding>");
            options.add("--aout=opensles");
            options.add("--audio-time-stretch"); // time stretching
            options.add("-vvv"); // verbosity
            //옵셕 적용하여 libvlc 생성
            libvlc = new LibVLC(this, options);

            // 화면 자동을 꺼지는 것 방지
            holder.setKeepScreenOn(true);

            // mediaplay 클래스 생성  (libvlc 라이브러리)
            mMediaPlayer = new MediaPlayer(libvlc);
            // 이벤트 리스너 연결
            mMediaPlayer.setEventListener(mPlayerListener);

            // 영상을 surface 뷰와 연결 시킴
            final IVLCVout vout = mMediaPlayer.getVLCVout();
            vout.setVideoView(surface);

            //콜백 함수 등록
            vout.addCallback(this);
            //서페이스 홀더와 연결
            vout.attachViews();

            //동영상 파일 로딩
            Media m;
            if (isURL) {
                m = new Media(libvlc, Uri.parse(mediaPath));
            }
            else {
                m = new Media(libvlc, mediaPath);
            }
            m.setHWDecoderEnabled(false, false); //HW decoderEnable
            mMediaPlayer.setMedia(m);
            // 재생 시작
            mMediaPlayer.play();

        } catch (Exception e) {
            Toast.makeText(this, "Error creating player!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /*
     *  동영상 플레이어 종료
     */
    private void releasePlayer() {
        //라이브러리가 없다면
        //바로 종료
        if (libvlc == null)
            return;
        if (mMediaPlayer != null) {
            //플레이 중지

            mMediaPlayer.stop();

            final IVLCVout vout = mMediaPlayer.getVLCVout();
            //콜백함수 제거
            vout.removeCallback(this);

            //연결된 뷰 분리
            vout.detachViews();
        }

        holder = null;
        libvlc.release();
        libvlc = null;

        mVideoWidth = 0;
        mVideoHeight = 0;
    }

    //영상 사이즈 변경
    private void setSize( int width, int height ) {
        Log.e("m-Log", "setSize w : " + width + " h : " + height);
        Log.e("m-Log", "preference Size w : " + app.pref.getValue(PreferenceInfo.MOVIE_WIDTH, 0) + " h : " + app.pref.getValue(PreferenceInfo.MOVIE_HEIGHT, 0));
        mVideoWidth = width;
        mVideoHeight = height;
        if (mVideoWidth * mVideoHeight <= 1)
            return;

        if (holder == null || surface == null)
            return;

        // get screen size
        int w = getWindow().getDecorView().getWidth();
        int h = getWindow().getDecorView().getHeight();

        // getWindow().getDecorView() doesn't always take orientation into
        // account, we have to correct the values
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        if (w > h && isPortrait || w < h && !isPortrait) {
            int i = w;
            w = h;
            h = i;
        }

        float videoAR  = (float) mVideoWidth / (float) mVideoHeight;
        float screenAR = (float) w / (float) h;

        if (screenAR < videoAR)
            h = (int) (w / videoAR);
        else
            w = (int) (h * videoAR);

        // force surface buffer size
        holder.setFixedSize(mVideoWidth, mVideoHeight);
        //holder.setFixedSize(flPlayer.getWidth(), flPlayer.getHeight());

        // set display size
        ViewGroup.LayoutParams lp = surface.getLayoutParams();
        lp.width = w;
        lp.height = h;

//        lp.width = ConvertUtil.getDensityToValue(activity, context, app.pref.getValue(PreferenceInfo.MOVIE_WIDTH, 0));
//        lp.height = ConvertUtil.getDensityToValue(activity, context, app.pref.getValue(PreferenceInfo.MOVIE_HEIGHT, 0));
        surface.setLayoutParams(lp);
        surface.invalidate();
    }

    //    /* IVLCVout.Callback override 함수 시작 */
    @Override
    public void onNewLayout( IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen ) {
        if (width * height == 0)
            return;


        //        final int                p_width  = app.pref.getValue(PreferenceInfo.MOVIE_WIDTH, 0);
        //        final int                p_height = app.pref.getValue(PreferenceInfo.MOVIE_HEIGHT, 0);
        //        FrameLayout.LayoutParams params;
        //        //params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        //        params = new FrameLayout.LayoutParams(ConvertUtil.getDensityToValue(activity, context, p_width), ConvertUtil.getDensityToValue(activity, context, p_height));
        //        //params = new FrameLayout.LayoutParams(width, height);
        //        params.gravity = Gravity.TOP;
        //        params.topMargin = 60;
        //        flPlayer.setLayoutParams(params);
        //
        //        final int p_width  = app.pref.getValue(PreferenceInfo.MOVIE_WIDTH, 0);
        //        final int p_height = app.pref.getValue(PreferenceInfo.MOVIE_HEIGHT, 0);
        //        if (p_width * p_height == 0)
        //            return;
        //        Log.e("m-Log", "onNewLayout width : " + width + " height : " + height + " vW : " + visibleWidth + " vH : " + visibleHeight + " p_width : " + p_width + " p_height : " + p_height);
        //        setSize(p_width, p_height);


        // store video size
        mVideoWidth = width;
        mVideoHeight = height;
        setSize(mVideoWidth, mVideoHeight);
    }

    @Override
    public void onSurfacesCreated( IVLCVout vout ) {
        //서페이스 생성 시
    }

    @Override
    public void onSurfacesDestroyed( IVLCVout vout ) {
        //서페이스 종료 시
    }

    @Override
    public void onHardwareAccelerationError( IVLCVout vlcVout ) {
        //하드웨어 가속 에러시 발생 함.
        Log.e("m-Log", "Error with hardware acceleration");
        this.releasePlayer();
        //Toast.makeText(this, "Error with hardware acceleration", Toast.LENGTH_LONG).show();
    }
    //    /* IVLCVout.Callback override 함수 끝 */


    /* MediaPlayer리스너 */
    private MediaPlayer.EventListener mPlayerListener = new MediaPlayerListener(this);

    private static class MediaPlayerListener implements MediaPlayer.EventListener {
        private WeakReference<VlcActivity> mOwner;

        public MediaPlayerListener( VlcActivity owner ) {
            mOwner = new WeakReference<VlcActivity>(owner);
        }

        @Override
        public void onEvent( MediaPlayer.Event event ) {
            VlcActivity player = mOwner.get();

            switch (event.type) {
                case MediaPlayer.Event.EndReached:
                    //동영상 끝까지 재생되었다면..
                    player.releasePlayer();
                    break;
                case MediaPlayer.Event.Playing:
                case MediaPlayer.Event.Paused:
                case MediaPlayer.Event.Stopped:
                    break;

                //아래 두 이벤트는 계속 발생됨
                case MediaPlayer.Event.TimeChanged: //재생 시간 변화시
                    break;
                case MediaPlayer.Event.PositionChanged: //동영상 재생 구간 변화시
                    //Log.d(TAG, "PositionChanged");
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick(R.id.btnPlaynPause)
    void playnpauseButtonClick() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                btnPlaynPause.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_movie_08));
            }
            else {
                btnPlaynPause.setBackground(getResources().getDrawable(R.drawable.icon_movie_08));
            }
        }
        else {
            mMediaPlayer.play();
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                btnPlaynPause.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_movie_08_1));
            }
            else {
                btnPlaynPause.setBackground(getResources().getDrawable(R.drawable.icon_movie_08_1));
            }
        }
    }

    @OnClick(R.id.surface)
    void surfaceClick() {
        if (View.VISIBLE == llPlayerPlayControl.getVisibility()) {
            llPlayerPlayControl.setVisibility(View.GONE);
        }
        else {
            llPlayerPlayControl.setVisibility(View.VISIBLE);
        }

        if (View.VISIBLE == llSeek.getVisibility()) {
            llSeek.setVisibility(View.GONE);
        }
        else {
            llSeek.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btnAdvance)
    void playAdvanceClick() {
        mMediaPlayer.setTime(mMediaPlayer.getTime() + 10000L);
    }

    @OnClick(R.id.btnRetour)
    void playRetourClick() {
        mMediaPlayer.setTime(mMediaPlayer.getTime() - 10000L);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        releasePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
