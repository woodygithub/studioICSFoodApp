package com.org.icsfoodapp.fragments;

import com.org.icsfoodapp.MainActivity;

import android.support.v4.app.Fragment;

public class MenuLockFragment extends Fragment{
	
	@Override
	public void onPause() {
		super.onPause();
		((MainActivity)getActivity()).releaseMenu();
	}

	@Override
	public void onResume() {
		super.onResume();
		((MainActivity)getActivity()).lockMenu();
	}


}
