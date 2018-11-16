package com.tenutohahwi.hahwiportfolio.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.C.ContentType;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.RepresentationKey;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.source.hls.playlist.RenditionKey;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.StreamKey;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.http.HttpService;
import com.tenutohahwi.hahwiportfolio.util.Application;
import com.tenutohahwi.hahwiportfolio.util.PreferenceInfo;
import com.tenutohahwi.hahwiportfolio.util.ToastUtils;


import java.lang.reflect.Constructor;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class PlayerActivity extends AppCompatActivity implements PlayerControlView.VisibilityListener {
    private Activity    activity;
    private Application app;
    private Context     context;
    private Intent      intent;
    private String      TAG = "ExplainPopupActivity";

    @BindView(R.id.llBack)  LinearLayout llBack;
    @BindView(R.id.llRight) LinearLayout llRight;

    //player
    private SimpleExoPlayer player;
    private PlayerView      videoPlayerView;

    private ViewGroup mpSpeedCtrl;
    private Button    btn_play_speed_up;
    private Button    btn_play_speed_down;
    private TextView  tvPlaySpeed;
    float speed = 1f;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);

        ButterKnife.bind(this);

        activity = this;
        app = (Application) getApplication();
        context = getApplicationContext();
        intent = getIntent();


        setLayout();
    }


    public void setLayout() {
        videoPlayerView = (PlayerView) findViewById(R.id.videoPlayerView);
        LinearLayout llTop = (LinearLayout) findViewById(R.id.llTop);

        mpSpeedCtrl = (ViewGroup) findViewById(R.id.mpSpeedCtrl);
        tvPlaySpeed = (TextView) findViewById(R.id.tx_play_speed);
        tvPlaySpeed.setText("1.0x");

        btn_play_speed_up = (Button) findViewById(R.id.btn_play_speed_up);
        btn_play_speed_up.setOnClickListener(buttonClickListener);
        btn_play_speed_down = (Button) findViewById(R.id.btn_play_speed_down);
        btn_play_speed_down.setOnClickListener(buttonClickListener);

        try {
            // 1. Create a default TrackSelector
            BandwidthMeter         bandwidthMeter             = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector          trackSelector              = new DefaultTrackSelector(videoTrackSelectionFactory);

            // 2. Create a default LoadControl
            LoadControl loadControl = new DefaultLoadControl();

            // 3. Create the player
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);

            videoPlayerView = (PlayerView) findViewById(R.id.videoPlayerView);
            videoPlayerView.setControllerVisibilityListener(this);
            videoPlayerView.setPlayer(player);

            String movieUrl = "http://sejongdrm.gscdn.com/MathBank/" + "79062" + "_High.mp4";
            //Uri movieUri = Uri.parse("http://sejongdrm.gscdn.com/MathBank/" + paper.getConceptItemId() + "_High.mp4");
            Uri                movieUri          = Uri.parse(movieUrl);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "yourApplicationName"));
            ExtractorsFactory  extractorsFactory = new DefaultExtractorsFactory();
            // MediaSource        videoSource       = new ExtractorMediaSource(movieUri, dataSourceFactory, extractorsFactory, null, null);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).setExtractorsFactory(extractorsFactory).createMediaSource(movieUri);
            player.prepare(videoSource);
        } catch (Exception e) {
            e.printStackTrace();
            videoPlayerView.setVisibility(View.GONE);
            llTop.setVisibility(View.GONE);
        }


        PlaybackParameters param = new PlaybackParameters(1f);
        player.setPlaybackParameters(param);
    }


    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick( View v ) {
            switch (v.getId()) {
                case R.id.btn_play_speed_up:
                    playSpeedChange("up");
                    break;
                case R.id.btn_play_speed_down:
                    playSpeedChange("down");
                    break;
            }
        }
    };


    private void playSpeedChange( String value ) {
        //Log.e("m-Log", "현재 스피드는1? : " + player.getPlaybackParameters().speed);
        if ("up".equals(value)) {
            if (1f == speed) {
                speed = 1.5f;
            }
            else {
                speed = 2f;
            }
        }
        else {
            if (2f == speed) {
                speed = 1.5f;
            }
            else {
                speed = 1f;
            }

        }
        //Log.e("m-Log", "현재 스피드는2? : " + player.getPlaybackParameters().speed);
        PlaybackParameters param;
        if (null != player.getPlaybackParameters()) {
            param = null;
        }
        param = new PlaybackParameters(speed);

        tvPlaySpeed.setText(speed + "x");

        player.setPlaybackParameters(param);
        //Log.e("m-Log", "현재 스피드는3? : " + player.getPlaybackParameters().speed);
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent intent ) {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        try {
            player.stop();
            player.release();
        } catch (Exception e) {
        }
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onVisibilityChange( int visibility ) {
        //Log.e("m-Log", "onVisibilityChange : "+visibility);
        if (0 == visibility) {
            mpSpeedCtrl.setVisibility(View.VISIBLE);
        }
        else {
            mpSpeedCtrl.setVisibility(View.GONE);
        }
        //debugRootView.setVisibility(visibility);
    }


    @OnClick(R.id.llBack)
    void backButtonClick() {
        finish();
        overridePendingTransition(0, 0);
    }
}
