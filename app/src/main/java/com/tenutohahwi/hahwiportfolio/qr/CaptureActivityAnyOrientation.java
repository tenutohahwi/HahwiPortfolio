package com.tenutohahwi.hahwiportfolio.qr;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.RequestParams;
import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.foodanalysis.FoodAnalysisActivity;
import com.tenutohahwi.hahwiportfolio.http.HttpService;
import com.tenutohahwi.hahwiportfolio.main.MainActivity;
import com.tenutohahwi.hahwiportfolio.util.Application;
import com.tenutohahwi.hahwiportfolio.util.PreferenceInfo;
import com.tenutohahwi.hahwiportfolio.util.ToastUtils;


public class CaptureActivityAnyOrientation extends Activity {
    HttpService httpService;
    Application app;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_any_orientation_activity);

        app = (Application) getApplication();
        httpService = new HttpService(getApplicationContext());
        activity = this;
        //new IntentIntegrator(CaptureActivityAnyOrientation.this).initiateScan();

        callBarcodeReader();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // QR코드/ 바코드를 스캔한 결과
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Log.e("m-Log", "result formatname : " + result.getFormatName());
        Log.e("m-Log", "result getcontents : " + result.getContents());

        if(null == result.getFormatName()){
            ToastUtils.Toast(getApplicationContext(), "QR/바코드 인식에 실패했습니다\n다시 시도해 주세요.");
        }else if("null".equals(result.getFormatName())){
            ToastUtils.Toast(getApplicationContext(), "QR/바코드 인식에 실패했습니다\n다시 시도해 주세요.");
        }else if("QR_CODE".equals(result.getFormatName())){
            ToastUtils.Toast(getApplicationContext(), "QR코드 인식 성공!");
        }else{
            ToastUtils.Toast(getApplicationContext(), "바코드 인식 성공!");
        }

        if(null != result.getContents() && result.getContents().contains("http")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getContents()));
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        }

        else{
            if(null != result.getContents()){
                //((FoodAnalysisActivity)activity).getBarcodeData(result.getFormatName(), result.getContents());
                Intent intent = getIntent();
                intent.putExtra("format", result.getFormatName());
                intent.putExtra("content", result.getContents());
                setResult(RESULT_OK, intent);
            }

        }


//        if (null != result.getContents()) {
//            RequestParams params = new RequestParams();
//            if("QR_CODE".equals(result.getFormatName())){ //qrcode의 url이 result.getContents()에 들어온다.
//                httpService.getLearnInfo(result.getContents(), app, new httpService.HttpCallback() {
//                    @Override
//                    public void httpDone(String ids) {
//                        if ("success fail".equals(ids) || "res fail".equals(ids)) {
//                            if ("res fail".equals(ids)) {
//                                ToastUtils.Toast(getApplicationContext(), "서버와의 연결이 원활하지 않습니다.\n잠시후 이용해주세요.");
//                            } else {
//                                ToastUtils.Toast(getApplicationContext(), "강의가 존재하지 않습니다.\n영상 코드를 확인해주세요.");
//                            }
//                        } else {
//                            if (ids.contains(",")) {
//                                String[] res = ids.split(","); //success,url,point
//                                Intent intent = new Intent();
//                                intent.setClass(getApplicationContext(), PlayerActivity.class);
//                                //intent.setData(Uri.parse("http://sejongdrm.gscdn.com/mobile/chamathtab/MDRA02-03_Low.mp4"));
//                                intent.setData(Uri.parse(res[1])); //url
//                                intent.putExtra("code", res[2]); //code
//                                intent.putExtra("ptype", res[3]); //ptype
//                                intent.putExtra("point", res[4]); //point
//                                intent.setAction(PlayerActivity.ACTION_VIEW);
//                                startActivity(intent);
//
//                                finish();
//                                overridePendingTransition(0,0);
//                            } else {
//                                ToastUtils.Toast(getApplicationContext(), "강의가 존재하지 않습니다.\n영상 코드를 확인해주세요.");
//                            }
//                        }
//
//                    }
//                });
//            }else{
//                params.put("action", "request_barcode");
//                params.put("member_id", app.pref.getValue(PreferenceInfo.MEMBER_ID, ""));
//                params.put("barcode", result.getContents());
//                httpService.sendBarCode(params, new httpService.HttpCallback() {
//                    @Override
//                    public void httpDone(String ids) {
//                        Log.e("m-Log", "sendBarcode httpDone : " + ids);
//                        callBarcodeReader();
//                    }
//                });
//            }
//        } else {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//        }
        // result.getFormatName() : 바코드 종류
        // result.getContents() : 바코드 값
        //Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT);
    }


    //세로모드로 실행시키기 위함
    private void callBarcodeReader() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(ZxingACtivity.class);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

}
