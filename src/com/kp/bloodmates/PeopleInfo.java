package com.kp.bloodmates;

import com.kp.bloodmates.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PeopleInfo extends Fragment {
	
	String url = "http://docs.google.com/gview?embedded=true&url=http://www.givebloodgivelife.org/drd_cd/sponsor_cd/files/education_center/CommonQuestionsAboutDonating-vSept08.pdf";
	private WebView browser;
	private ProgressBar progress;
	
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_peopleinfo, container, false);
		
		if(isNetworkStatusAvialable (getActivity().getApplicationContext())) {
    	    
			//Toast.makeText(getActivity().getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
	    	 
			activeBrowser();
			
	    } else {
	    	
	    	rootView = inflater.inflate(R.layout.fragment_nointernet, container, false);
	    	
	    	Toast.makeText(getActivity().getApplicationContext(), "Please, connect to the internet and retry", Toast.LENGTH_LONG).show();
	    	
	    }
		
		return rootView; 
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void activeBrowser(){
		

		browser = (WebView) rootView.findViewById(R.id.webView1);
		progress = (ProgressBar) rootView.findViewById(R.id.progressBarHorizontal);
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
	    PeopleInfo.this.progress.setProgress(0);
	}


	private class MyBrowser extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {			
			PeopleInfo.this.setValue(newProgress);
			if(progress.getProgress() == 100){
				
				progress.setVisibility(View.GONE);
	          }
			super.onProgressChanged(view, newProgress);
		}
		
		
	}
	
	public void setValue(int progress) {
		this.progress.setProgress(progress);		
	}
	
	public class WebAppInterface {
	    Context mContext;

	    /** Instantiate the interface and set the context */
	    WebAppInterface(Context PeopleInfo) {
	        mContext = PeopleInfo;
	    }

	    public WebAppInterface(PeopleInfo PeopleInfo) {
			// TODO Auto-generated constructor stub
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
}
