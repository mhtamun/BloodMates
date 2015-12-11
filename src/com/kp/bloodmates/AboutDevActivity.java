package com.kp.bloodmates;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AboutDevActivity extends Activity {
	
	String url = "https://www.linkedin.com/in/mhtamun";
	private WebView browser;
	private ProgressBar progress;

	@Override		
	protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_about_dev);
	      
	      if(isNetworkStatusAvialable (getApplicationContext())) {
	    	    
	    	  Toast.makeText(getApplicationContext(), "About Developer", Toast.LENGTH_SHORT).show();
	    	  
	    	} else {
	    	    Toast.makeText(getApplicationContext(), "Connection Lost", Toast.LENGTH_SHORT).show();

	    	}
	      
	      activeBrowser();
	      
	}
	
	private void activeBrowser(){
		

		browser = (WebView)findViewById(R.id.webView1);
		progress = (ProgressBar) findViewById(R.id.progressBarHorizontal);
		progress.setMax(100);
		browser.setBackgroundColor(Color.parseColor("#000000"));
		browser.addJavascriptInterface(new WebAppInterface(this), "Android");
	    browser.setWebChromeClient(new MyBrowser());
	    browser.getSettings().setLoadsImagesAutomatically(true);
	    browser.getSettings().setJavaScriptEnabled(true);
	    browser.getSettings().setBuiltInZoomControls(true);
	    browser.getSettings().setSupportZoom(true);
	    browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
	    browser.loadUrl(url);
	    AboutDevActivity.this.progress.setProgress(0);
	}
	

	private class MyBrowser extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {			
			AboutDevActivity.this.setValue(newProgress);
			if(progress.getProgress() == 100){
				
				progress.setVisibility(View.GONE);
	          }
			super.onProgressChanged(view, newProgress);
		}
		
		
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (Uri.parse(url).getHost().equals(url)) {
	            // This is my web site, so do not override; let my WebView load the page
	            return false;
	        }
	        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
	        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	        startActivity(intent);
	        return true;
	        //view.loadUrl(url);
	        //return true;
		}
		
		
	}
	
	public void setValue(int progress) {
		this.progress.setProgress(progress);		
	}
	
	public class WebAppInterface {
	    Context mContext;

	    /** Instantiate the interface and set the context */
	    WebAppInterface(Context c) {
	        mContext = c;
	    }

	    /** Show a toast from the web page */
	    @JavascriptInterface
	    public void showToast(String toast) {
	        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	    }
	}
	

	public static boolean isNetworkStatusAvialable (Context context) {
	    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (connectivityManager != null) 
	    {
	        NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
	        if(netInfos != null)
	        if(netInfos.isConnected()) 
	            return true;
	    }
	    return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
	        case R.id.action_about:
	        	Intent i = new Intent (AboutDevActivity.this, AboutDevActivity.class);
				startActivity(i);
	            break;
	        }
	 
	        return false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
	    if(event.getAction() == KeyEvent.ACTION_DOWN){
	        switch(keyCode){
	        
	        case KeyEvent.KEYCODE_BACK:
	        

	        	Intent i = new Intent (AboutDevActivity.this, MainActivity.class);
				startActivity(i);

	        }
	        
	
	    }
	    return super.onKeyDown(keyCode, event);
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
