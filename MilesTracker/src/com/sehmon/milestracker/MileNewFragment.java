package com.sehmon.milestracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

public class MileNewFragment extends Fragment {
	
	//The codes for returning a new mile to the MileLab
	public static final String MILE_DATE = "date";
	public static final String MILE_TYPE = "type";
	public static final String MILE_LENGTH = "length";
	public static final String MILE_TIME = "time";
	public static final String MILE_DESCRIPTION = "description";
	public static final String MILE_ID = "id";
	
	private static final int RESULT_OK = 1;
	private static final int RESULT_FAILED = 2;
	
	private UUID mId;
    private double mLength;
    private double mTime;
    private String mType;
    private Date mDate;
    private String mDescription;
	
//TODO
	
	RadioGroup typeRadioGroup;
	Button timeButton;
	Button dateButton;
	
	EditText descriptionEditText;
	EditText lengthEditText;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getActivity().setTitle("Add New Mile");
		setHasOptionsMenu(true);
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    inflater.inflate(R.menu.new_mile, menu);
	    super.onCreateOptionsMenu(menu,inflater);
	    
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_cancelMile:
	        	getActivity().setResult(RESULT_FAILED);
	    		getActivity().finish();
	        	
	        case R.id.action_addMile:
	        	saveMile();
	        case android.R.id.home:
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	   
	}
	
	
	
	private void saveMile() {
		
		Intent i = new Intent();
		
       
        //Gets the type from the selected radio button
        int selectedId = typeRadioGroup.getCheckedRadioButtonId();
        
        if(selectedId == R.id.schoolRadio){
        	mType = "school";
        } else if (selectedId == R.id.homeRadio){
        	mType = "home";
        } else {
        	mType = "sport";
        }
        
        //Gets the description of the mile
        mDescription = descriptionEditText.getText().toString();
        Log.i("test", mDescription);
        try{
        mLength = Double.parseDouble(lengthEditText.getText().toString());
        } catch(Exception e){
        	mLength = 0;
        }
        
        //Gives the mile a new random UUID for identification
        mId = UUID.randomUUID();
		
		Log.e("date", mDate.toString());
		
		i.putExtra(MILE_TYPE, mType);
		i.putExtra(MILE_DATE, mDate);
		i.putExtra(MILE_LENGTH, mLength);
		i.putExtra(MILE_TIME, mTime);
		i.putExtra(MILE_DESCRIPTION, mDescription);
		i.putExtra(MILE_ID, mId);
		
		Toast.makeText(getActivity(), mDescription, Toast.LENGTH_SHORT).show();
		
		
		getActivity().setResult(RESULT_OK, i);
		getActivity().finish();
		
		
		
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
            Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_new_mile, parent, false);

        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        
        typeRadioGroup = (RadioGroup)v.findViewById(R.id.typeRadioGroup);
        
        dateButton = (Button)v.findViewById(R.id.dateButton);
        
        mDate = new Date();
        
        final SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yy", Locale.US);
        mDate = new Date();
        dateButton.setText(sdf.format(mDate));
        dateButton.setOnClickListener(new View.OnClickListener() {
			
        	@Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(), new OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */        	
                        mDate = getDateFromDatePicker(datepicker);
                        dateButton.setText(sdf.format(mDate));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        
        timeButton = (Button)v.findViewById(R.id.timeButton);
        
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        
        mTime = (60*hour + minute);
        
        timeButton.setText(hour + ":" + minute);
        timeButton.setOnClickListener(new View.OnClickListener() {
			
        	@Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mTime = (60*selectedHour + selectedMinute);
                    	
                    	timeButton.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Time:");
                mTimePicker.show();
            }
        });
        
        descriptionEditText = (EditText)v.findViewById(R.id.descriptionEditText);
        
        lengthEditText = (EditText)v.findViewById(R.id.lengthEditText);
        
        typeRadioGroup = (RadioGroup)v.findViewById(R.id.typeRadioGroup);
        typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				
				if(arg1 == R.id.sportRadio){
					
					
				}
					
				
			}
		});
        
        
        
        
        return v;
	}
	
	public static Date getDateFromDatePicker(DatePicker datePicker){
		
		int day = datePicker.getDayOfMonth();
	    int month = datePicker.getMonth();
	    int year =  datePicker.getYear();

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(year, month, day);

	    return calendar.getTime();
		
	}
	
}
