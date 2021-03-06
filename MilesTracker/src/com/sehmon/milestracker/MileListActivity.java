package com.sehmon.milestracker;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

public class MileListActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mile_list);
		
		FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.listFragmentContainer);
        
        if(fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
            .add(R.id.listFragmentContainer, fragment)
            .commit();
        } else {
        	fm.beginTransaction()
        	.replace(R.id.listFragmentContainer, fragment)
        	.commit();
        }
    }
	
	protected Fragment createFragment(){
		return new MileListFragment();
	}

}
