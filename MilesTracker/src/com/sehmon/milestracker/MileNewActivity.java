package com.sehmon.milestracker;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

public class MileNewActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_mile);
		
		FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.newMileFragmentContainer);
        
        if(fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
            .add(R.id.newMileFragmentContainer, fragment)
            .commit();
        } else {
        	fm.beginTransaction()
        	.replace(R.id.newMileFragmentContainer, fragment)
        	.commit();
        }
    }
	
	protected Fragment createFragment(){
		return new MileNewFragment();
	}

}
