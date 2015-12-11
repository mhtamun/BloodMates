package com.kp.bloodmates;

import com.kp.bloodmates.adapter.TabsPagerAdapter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Home", "Add People", "Info" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(isNetworkStatusAvialable (getApplicationContext())) {
    	    
			Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
	    	  
	    } else {
	    	Toast.makeText(getApplicationContext(), "Please, connect to the internet and retry", Toast.LENGTH_LONG).show();

	    }

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
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
	        	Intent i = new Intent (MainActivity.this, AboutDevActivity.class);
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
	        
	        	android.app.AlertDialog.Builder alertDialog3 = new android.app.AlertDialog.Builder(MainActivity.this);

				alertDialog3.setTitle("Are you Sure?"); // Setting Dialog Title
				 
				alertDialog3.setMessage("Click yes to exit!"); // Setting Dialog Message
				 
				alertDialog3.setIcon(R.drawable.alerticon); // Setting Icon to Dialog
				 
				// Setting "Yes" Button
				alertDialog3.setPositiveButton("YES",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
				        finish();
				        System.exit(0);
						
				    }
				});
				
				// Setting "NO" Button
				alertDialog3.setNegativeButton("NO",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
				    }
				});

				alertDialog3.show(); // Showing Alert Dialog	
	        	
	    

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
