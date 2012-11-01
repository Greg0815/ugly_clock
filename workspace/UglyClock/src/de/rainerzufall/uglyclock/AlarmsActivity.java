package de.rainerzufall.uglyclock;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AlarmsActivity extends Activity implements OnClickListener, Runnable
{
    private Spinner inputYear;
    private Spinner inputMonth;
    private Spinner inputDay;
    private Spinner inputHour;
    private Spinner inputMinute;
    private Button saveButton;
    private ArrayAdapter<String> inputYearAdapter;
    private ArrayAdapter<CharSequence> inputMonthAdapter;
    private ArrayAdapter<CharSequence> inputDayAdapter;
    private ArrayAdapter<CharSequence> inputHourAdapter;
    private ArrayAdapter<CharSequence> inputMinuteAdapter;
    private ArrayList<String> yearList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        assignIds();
        assignListeners();
        createObjects();
        setAdapters();
        setSpinnerPositionsToToday();
    }

    private void assignIds()
    {
        inputYear = (Spinner)findViewById(R.id.AlarmsInputYear);
        inputMonth = (Spinner)findViewById(R.id.AlarmsInputMonth);
        inputDay = (Spinner)findViewById(R.id.AlarmsInputDay);
        inputHour = (Spinner)findViewById(R.id.AlarmsInputHour);
        inputMinute = (Spinner)findViewById(R.id.AlarmsInputMinute);
        saveButton = (Button)findViewById(R.id.AlarmsButton);
    }

    private void assignListeners(){
        saveButton.setOnClickListener(this);
    }
    
    private void createObjects(){
    	yearList = new ArrayList<String>();
    	createYearSpinnerOutput();
        inputYearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearList);
        inputMonthAdapter = ArrayAdapter.createFromResource(this, R.array.month_array, android.R.layout.simple_spinner_item);
        inputDayAdapter = ArrayAdapter.createFromResource(this, R.array.day_array, android.R.layout.simple_spinner_item);
        inputHourAdapter = ArrayAdapter.createFromResource(this, R.array.hour_array, android.R.layout.simple_spinner_item);
        inputMinuteAdapter = ArrayAdapter.createFromResource(this, R.array.minute_array, android.R.layout.simple_spinner_item);
    }

    private void createYearSpinnerOutput(){
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i=0; i<=42; i++)
            yearList.add(String.valueOf(thisYear+i));
    }
    
	private void setAdapters() {
		inputYear.setAdapter(inputYearAdapter);
        inputMonth.setAdapter(inputMonthAdapter);
        inputDay.setAdapter(inputDayAdapter);
        inputHour.setAdapter(inputHourAdapter);
        inputMinute.setAdapter(inputMinuteAdapter);
	}

    private void setSpinnerPositionsToToday() {
		inputMonth.setSelection(Calendar.getInstance().get(Calendar.MONTH));
		inputDay.setSelection(Calendar.getInstance().get((Calendar.DAY_OF_MONTH))-1);
		inputHour.setSelection(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		inputMinute.setSelection(Calendar.getInstance().get(Calendar.MINUTE));
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_alarms, menu);
        return true;
    }

    public void run()
    {
        // TODO Auto-generated method stub
        
    }

    public void onClick(View view)
    {
        // TODO check input correctness

        Calendar alarmDate = Calendar.getInstance();
        alarmDate.set(Calendar.YEAR, Integer.valueOf(inputYear.getSelectedItem().toString()));
        alarmDate.set(Calendar.MONTH, Integer.valueOf(inputMonth.getSelectedItem().toString())-1);
        alarmDate.set(Calendar.DAY_OF_MONTH, Integer.valueOf(inputDay.getSelectedItem().toString()));
        alarmDate.set(Calendar.HOUR_OF_DAY, Integer.valueOf(inputHour.getSelectedItem().toString()));
        alarmDate.set(Calendar.MINUTE, Integer.valueOf(inputMinute.getSelectedItem().toString()));
        alarmDate.set(Calendar.SECOND, 0);
        
        Intent intent = new Intent(this, UglyAlarmsBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 42, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmDate.getTimeInMillis(), pendingIntent);
        
        //TODO Alarm einbauen
        //TODO Statt Seconds auch Minutes und Stunden
        Toast.makeText(this, "Alarm set in " + (alarmDate.getTimeInMillis()-Calendar.getInstance().getTimeInMillis())/1000 + " seconds", Toast.LENGTH_SHORT).show();
   
    }

}
