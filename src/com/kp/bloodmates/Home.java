package com.kp.bloodmates;

import com.kp.bloodmates.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Home extends Fragment {
	
	String url = "http://www.djkamalislam.com/bloodmates/";
	private WebView browser;
	private ProgressBar progress;
	
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_home, container, false);
		
		if(isNetworkStatusAvialable (getActivity().getApplicationContext())) {
    	    
			//Toast.makeText(getActivity().getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
	    	 
			activeBrowser();
			
	    } else {
	    	
	    	rootView = inflater.inflate(R.layout.fragment_nointernet, container, false);
	    	
	    	Toast.makeText(getActivity().getApplicationContext(), "Please, connect to the internet and retry", Toast.LENGTH_LONG).show();
	    	
	    }
		
		return rootView; 
	}
	
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	private void activeBrowser(){
		

		browser = (WebView) rootView.findViewById(R.id.webView1);
		progress = (ProgressBar) rootView.findViewById(R.id.progressBarHorizontal);
		progress.setMax(100);
		browser.setBackgroundColor(Color.parseColor("#000000"));
	    browser.setWebChromeClient(new MyBrowser());
	    browser.getSettings().setLoadsImagesAutomatically(true);
	    browser.getSettings().setJavaScriptEnabled(true);
	    browser.addJavascriptInterface(new WebAppInterface(getActivity().getApplicationContext()), "Android");
	    browser.getSettings().setBuiltInZoomControls(true);
	    browser.getSettings().setSupportZoom(true);
	    browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
	    browser.loadUrl(url);
	    Home.this.progress.setProgress(0);
	}


	private class MyBrowser extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {			
			Home.this.setValue(newProgress);
			if(progress.getProgress() == 100){
				
				progress.setVisibility(View.GONE);
	          }
			super.onProgressChanged(view, newProgress);
		}
		
		
	}
	
	public class WebAppInterface {
	    Context mContext;

	    /** Instantiate the interface and set the context */
	    WebAppInterface(Context c) {
	        mContext = c;
	    }

		/** Show a toast from the web page */
	    
	    public void showToast(String toast) {
	        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	    }
	    
	    /** Show a alert from the web page */
	   
	    public void showAlert(final String dbid) {
	        
	    	android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());

			alertDialog.setTitle("Are you Sure?"); // Setting Dialog Title
			 
			alertDialog.setMessage("Click Update to Proceed"); // Setting Dialog Message
			 
			alertDialog.setIcon(R.drawable.alerticon); // Setting Icon to Dialog
			 
			// Setting "Yes" Button
			alertDialog.setPositiveButton("Update",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					
					browser.loadUrl("javascript:update_date("+dbid+");");
					
			    }
			});
			
			// Setting "NO" Button
			alertDialog.setNegativeButton("NO",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
			    }
			});

			alertDialog.show(); // Showing Alert Dialog	*/
	    	
	    }
	}
	
	public void setValue(int progress) {
		this.progress.setProgress(progress);		
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
