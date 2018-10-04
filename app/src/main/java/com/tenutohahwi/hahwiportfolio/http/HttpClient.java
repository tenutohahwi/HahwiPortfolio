package com.tenutohahwi.hahwiportfolio.http;

import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class HttpClient extends DefaultHttpClient {
	 private static AsyncHttpClient async = new AsyncHttpClient();
	 private static SyncHttpClient sync = new SyncHttpClient(); 
	 
	 public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		 getClient().get(url, params, responseHandler);
	 }
	 
	 public static void postSync(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		 getClient().setTimeout(30*1000);
		 getClient().setResponseTimeout(30*1000);
		 getClient().post(url, params, responseHandler);
	 }
	  
	 public static AsyncHttpClient getClient(){
	     // Return the synchronous HTTP client when the thread is not prepared
	     if (Looper.myLooper() == null)
	         return sync;
	     return async;
	 }
	  
	  
	  
	  
}
