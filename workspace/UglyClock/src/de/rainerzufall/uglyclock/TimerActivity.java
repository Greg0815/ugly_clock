package de.rainerzufall.uglyclock;

import android.app.Activity;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends Activity implements OnClickListener, Runnable
{

    private int inputHours;
    private int inputMinutes;
    private int inputSeconds;
    private int secondsToWait;
    private Button startButton;
    private Handler handler = new Handler();
    
    private TextView inputHoursView;
    private TextView inputMinutesView;
    private TextView inputSecondsView;
    
    private TextView timerDisplay;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        assignIds();
        assignListeners();
        initializations();
    }

    private void assignIds()
    {
        startButton = (Button)findViewById(R.id.timerButton);
        timerDisplay = (TextView)findViewById(R.id.timerDisplayTextView);
        inputHoursView = (TextView)findViewById(R.id.timerInputHours);
        inputMinutesView = (TextView)findViewById(R.id.timerInputMinutes);
        inputSecondsView = (TextView)findViewById(R.id.timerInputSeconds);
    }

    private void assignListeners()
    {
        startButton.setOnClickListener(this);
    }

    private void initializations()
    {
        timerDisplay.setVisibility(View.INVISIBLE);
        inputHoursView.setText("0");
        inputMinutesView.setText("1");
        inputSecondsView.setText("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_timer, menu);
        return true;
    }

    public void onClick(View view)
    {
        handler.removeCallbacks(this);
        hideKeyboard(view);
        
        if(allFieldsfilled())
        {
            timerDisplay.setVisibility(View.VISIBLE);
            readInputFields();
            secondsToWait = inputSeconds + 60 * inputMinutes + 3600 * inputHours;
            refreshDisplayTimer();
            handler.postDelayed(this, 100);
        }
        else
        {
            timerDisplay.setVisibility(View.VISIBLE);
            timerDisplay.setText("At least one field is empty.");
        }
    }

    private void readInputFields()
    {
        String inputString = inputHoursView.getText().toString();
        inputHours = Integer.valueOf(inputString);
        inputString = inputMinutesView.getText().toString();
        inputMinutes = Integer.valueOf(inputString);
        inputString = inputSecondsView.getText().toString();
        inputSeconds = Integer.valueOf(inputString);
    }

    private boolean allFieldsfilled()
    {
        return inputHoursView.getText().length()>0 && inputMinutesView.getText().length()>0 && inputSecondsView.getText().length()>0;
    }

    private void hideKeyboard(View view)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    
    public void run()
    {
        countDown();
    }
    
    private void countDown()
    {
        if(secondsToWait>0){
            secondsToWait--;
            refreshDisplayTimer();
            handler.postDelayed(this, 1000);
        }
        else
        {
            timerDisplay.setText("Time's over!");
        }
    }
    
    private void refreshDisplayTimer()
    {
        int hours = secondsToWait > 3600 ? secondsToWait/3600 : 0; 
        int minutes = (secondsToWait-hours*3600) > 60 ? (secondsToWait-hours*3600)/60 : 0;
        int seconds = (secondsToWait-hours*3600-minutes*60) > 0 ? (secondsToWait-hours*3600-minutes*60) : 0;
        timerDisplay.setText(hours + ":" + minutes + ":" + seconds);
    }
}
