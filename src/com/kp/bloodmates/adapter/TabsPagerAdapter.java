package com.kp.bloodmates.adapter;

import com.kp.bloodmates.AddPeople;
import com.kp.bloodmates.PeopleInfo;
import com.kp.bloodmates.Home;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new Home();
		case 1:
			// Games fragment activity
			return new AddPeople();
		case 2:
			// Movies fragment activity
			return new PeopleInfo();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
