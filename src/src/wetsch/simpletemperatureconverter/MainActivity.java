package wetsch.simpletemperatureconverter;

import java.text.DecimalFormat;

import wetsch.simpletemperatureconverter.R.id;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/* Version 2.1
 * This class is the main activity called by the Android manifest
 * This class extends Activity and implements OnClickListener.
 * The OnClickListener is used for the calculate button, and 
 * holds all of the code for the temperature conversions.
 * The arrayAdapter is used to populate the Spinners.
 * 
 *Changed how to application detects version updates to display about dialog.  
 */

public class MainActivity extends Activity implements OnClickListener, OnKeyListener, OnDoubleTapListener, OnItemSelectedListener, OnGestureListener {
	private int spinnerCFrom = -1;
	private int spinnerCTo = -1;
	private SharedPreferences getPref;//Preferences object to access application preferences.
	private DecimalFormat decimalF;//Format Answer.
	private double tempAnswer = 0.0;//Hold answer before passed to TextView.
	private Spinner convertFrom, convertTo;//Spinner holds choices to convert from/to.
	private TextView answer;//display answer to screen
	private EditText inputTemp;//Collect user input
	private Button calculate;//Button to run task
	private String convertionTypes[] = {"Fahrenheit","Celsius","Kelvin"};//Hold values for spinners.
	private ArrayAdapter<String> spinnerArrayAdapter;//Adapter for spinners.
	private int roundingOption;//Holds the value to set the decimal places for the decimal format object.
	private boolean autoHideKeyboard = false;//Holds the shared preferences value for the auto hide keyboard switch. 
	private GestureDetectorCompat mDetector;//Detect movement on touch. 
	private enum keyboardState{show, hide}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tempconvert);
		buildObjects();
		convertTo.setSelection(1);
		Installation.checkVersion(this);

		
	}

	//Inflate the option menu.
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;	
	}

	//Method for controlling menu items selection.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_Preferences:
		Intent pref =new  Intent(this, Preferences.class);
		startActivity(pref);
		break;
		case R.id.action_About:
		Intent aboutAppMessage = new Intent(this, AboutAppMessage.class);
		startActivity(aboutAppMessage);
		break;
		case R.id.clear_all:
			inputTemp.setText("");
			answer.setText(R.string.tv_convert_to_default_text);
			
		};
		return true;
	}

	//Building objects 
	private void buildObjects(){
		getPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		decimalF = new DecimalFormat("#.00");
		spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,convertionTypes);
		convertFrom = (Spinner) findViewById(R.id.convertfrom);
		convertTo = (Spinner) findViewById(R.id.convertto);
		inputTemp = (EditText) findViewById(R.id.tv_input);
		inputTemp.setOnKeyListener(this);
		mDetector = new GestureDetectorCompat(this,this);
		mDetector.setOnDoubleTapListener(this);
		answer = (TextView) findViewById(R.id.tv_answer);
		calculate = (Button) findViewById(R.id.calculate);
		convertFrom.setAdapter(spinnerArrayAdapter);
		convertTo.setAdapter(spinnerArrayAdapter);
		calculate.setOnClickListener(this);
		convertFrom.setOnItemSelectedListener(this);
		convertTo.setOnItemSelectedListener(this);
		//Add scrolling functionality to answer text view.
		answer.setMovementMethod(new ScrollingMovementMethod());
	}

	/*
	 *This method is called by the calculate button.
	 *if autoHideKeyboard is set to true, the keyboard 
	 *will hide when the calculateButton is pressed.
	 */
	private void showHideKeyBoard(keyboardState action){
	        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	        switch(action){
	        case hide:
	        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	        break;
	        case show:
	        	inputMethodManager.showSoftInput(inputTemp, 0);
	        }
	}
	
	private boolean checkForUserInput(){
		//Check to make sure that a value is in inputTemp text box.
		if(inputTemp.getText().toString().equals("")){
			Toast.makeText(this, "please enter a value for the starting temperature.", Toast.LENGTH_LONG).show();;
			return false;
		}
		//Check to make sure conversion from and to are not the same.
		else if(convertFrom.getSelectedItemPosition() == convertTo.getSelectedItemPosition()){
			Toast.makeText(this,"Can not convert from "+convertFrom.getSelectedItem().toString()
					+ " to " + convertTo.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	
	private void doCalculations(){
		//Convert from Faherenheit to Celsius/kelvin.
		if(convertFrom.getSelectedItemPosition() == 0){
			switch(convertTo.getSelectedItemPosition()){
			//Celsius
			case 1:
				tempAnswer = TemperatureConversionFormulas.fahrenheitToCelsius(Double.parseDouble(inputTemp.getText().toString()));
				answer.setText(decimalF.format(tempAnswer) + "\u00b0");
				break;
			//Kelvin
			case 2:
				tempAnswer = TemperatureConversionFormulas.fahrenheitToKelvin(Double.parseDouble(inputTemp.getText().toString()));
				answer.setText(decimalF.format(tempAnswer) + "\u00b0");
				break;
			};
		}

		//Convert from Celsius to Faherenheit/Kelvin.
		else if(convertFrom.getSelectedItemPosition() == 1){
			switch(convertTo.getSelectedItemPosition()){
			//Faherenheit
			case 0:
				tempAnswer = TemperatureConversionFormulas.celsiusToFahrenheit(Double.parseDouble(inputTemp.getText().toString()));
				answer.setText(decimalF.format(tempAnswer) + "\u00b0");
				break;
			//Kelvin
			case 2:
				tempAnswer = TemperatureConversionFormulas.celsiusToKelvin(Double.parseDouble(inputTemp.getText().toString()));
				answer.setText(decimalF.format(tempAnswer) + "\u00b0");
				break;
			};

		}
		//Convert from Kelvin To Faherenheit/Celsius
		else if(convertFrom.getSelectedItemPosition() == 2){
			switch(convertTo.getSelectedItemPosition()){
			//Faherenheit
			case 0:
				tempAnswer = TemperatureConversionFormulas.kelvinToFahrenheit(Double.parseDouble(inputTemp.getText().toString()));
				answer.setText(decimalF.format(tempAnswer) + "\u00b0");
				break;
			//Celsius
			case 1:
				tempAnswer = TemperatureConversionFormulas.kelvinToCelsius(Double.parseDouble(inputTemp.getText().toString()));
				answer.setText(decimalF.format(tempAnswer) + "\u00b0");
				break;
			};//End of switch case.
		 }
	}

	//On click method for calculate Button.
	@Override
	public void onClick(View v) {
			if(checkForUserInput() && v.getId() == id.calculate){
				if(autoHideKeyboard)
					showHideKeyBoard(keyboardState.hide);
				doCalculations();
			}
	}

	private void setupRounding(){
		StringBuilder pattern = new StringBuilder();
		pattern.setLength(0);
		pattern.append("0.");
		for(int i = 0; i < roundingOption; i++ ){
			pattern.append("0");
		}
		decimalF.applyPattern(pattern.toString());
	}
	//Applying the Preferences to the activity.
	public void setupPreferences(){
		roundingOption = Integer.parseInt(getPref.getString("rounding_option", "1"));
		setupRounding();
		autoHideKeyboard = getPref.getBoolean("auto_hide_keyboard", false);
	}

	//Overriding the onResume method to update changes in the preferences
	@Override
	protected void onResume() {
		super.onResume();
		setupPreferences();
		if(!inputTemp.getText().toString().equals(""))
			doCalculations();
			
	}

	//key listener for soft/hard keyboard.
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_ENTER  && event.getAction() == KeyEvent.ACTION_DOWN){
			if(checkForUserInput())
				doCalculations();
		}
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {return false;}
	//Not in use at this time.
	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {return false;}
	
	//Not in use at this time.
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {return false;}

	//Not in use at this time.
	@Override
	public boolean onDown(MotionEvent e) {return false;}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if(e1.getY() > calculate.getBottom() && velocityY < -1000){
			showHideKeyBoard(keyboardState.show);
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {}

	//Not in use at this time.
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {return false;}
	//Not in use that this time.
	@Override
	public void onShowPress(MotionEvent e) {}

	//Not in use at this time.
	@Override
	public boolean onSingleTapUp(MotionEvent e) {return false;}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if(!inputTemp.getText().toString().equals("") && (arg0.getSelectedItemPosition() != spinnerCFrom || arg0.getSelectedItemPosition() != spinnerCTo)){
			if(checkForUserInput())
				
			doCalculations();
		}
		spinnerCFrom = convertFrom.getSelectedItemPosition();
		spinnerCTo = convertTo.getSelectedItemPosition();
	}
	
	//No in use at this time.
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {}
}