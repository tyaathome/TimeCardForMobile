package com.tyaathome.timecardformobile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.example.timecardformobile.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "TimeCardForMobile";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bt = (Button) findViewById(R.id.flag1);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StartAsyncTask task = new StartAsyncTask();
				task.execute();
			}
		});
	}
	
	private String loginAndGetCookie() {
		String result = "";
		String cookie = "";
		try {
			String pathUrl = "http://192.168.50.50/json?action=LOGIN_BY_USER_ACTION&USERNAME=tangya&PASSWORD=123456";
			HttpPost httpPost = new HttpPost(pathUrl);
//			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//	        nvps.add(new BasicNameValuePair("action", "LOGIN_BY_USER_ACTION"));
//			nvps.add(new BasicNameValuePair("name", "tangya"));
//			nvps.add(new BasicNameValuePair("password", "123456"));
//			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			HttpResponse response = new DefaultHttpClient().execute(
					httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				Log.e(TAG,result);
				Header header = response.getFirstHeader("set-cookie");
				cookie = header.getValue();
				return cookie;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cookie;
	}
	
	private void timeCard(String cookie) {
		try {
			String pathUrl = "http://192.168.50.50/json?action=ADD_TBL_KQ_RECORD_ACTION";
	        HttpPost httpPost = new HttpPost(pathUrl);
	        httpPost.setHeader("Cookie", cookie);
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("action", "ADD_TBL_KQ_RECORD_ACTION"));
	        nvps.add(new BasicNameValuePair("cookie", cookie));
	        nvps.add(new BasicNameValuePair("FLAG", "4"));
	        nvps.add(new BasicNameValuePair("name", "tangya"));
	        nvps.add(new BasicNameValuePair("password", "123456"));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
			HttpResponse response = new DefaultHttpClient().execute(
					httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				Log.e(TAG,result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class StartAsyncTask extends AsyncTask<Void, String, String>{

		@Override
		protected String doInBackground(Void... params) {
			String cookie = loginAndGetCookie();
			publishProgress(cookie);
			//timeCard(cookie);
			return "";
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			String value = values[0];
			//Toast.makeText(MainActivity.this, value, Toast.LENGTH_LONG).show();
		}

	}

}
