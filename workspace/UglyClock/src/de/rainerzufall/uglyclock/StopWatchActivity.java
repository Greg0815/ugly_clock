package de.rainerzufall.uglyclock;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

//TODO Design aufmotzen

public class StopWatchActivity extends Activity implements OnClickListener, Runnable
{

    private int timePassedInSeconds;
    private int roundCounter;
    private Button startButton;
    private Button roundButton;
    private Button finishedButton;
    private Handler handler;
    private TextView stopwatchTime;
    
    private ListView roundList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;
    
    PowerManager powermanager;
    PowerManager.WakeLock wakelock;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        assignIds();
        assignListeners();
        createObjects();
        initializations();
    }

    private void assignIds()
    {
        startButton = (Button)findViewById(R.id.StopwatchStartButton);
        roundButton = (Button)findViewById(R.id.StopwatchRoundButton);
        finishedButton = (Button)findViewById(R.id.StopwatchFinishedButton);
        roundList = (ListView)findViewById(R.id.StopwatchRoundList);
        stopwatchTime = (TextView)findViewById(R.id.StopwatchTime);
    }

    private void assignListeners()
    {
        startButton.setOnClickListener(this);
        roundButton.setOnClickListener(this);
        finishedButton.setOnClickListener(this);
    }

    private void createObjects()
    {
        handler = new Handler();
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
        roundList.setAdapter(adapter);
        powermanager = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        wakelock = powermanager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "UglyStopWatchActivity");
    }

    private void initializations()
    {
        roundButton.setClickable(false);
        finishedButton.setClickable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_stop_watch, menu);
        return true;
    }

    public void onClick(View view)
    {
        if(view.getId()==R.id.StopwatchStartButton)
        {
            startStopWatch();
        }
        else if(view.getId()==R.id.StopwatchRoundButton)
        {
            addRound();
        }
        else if(view.getId()==R.id.StopwatchFinishedButton)
        {
            stopStopWatch();            
        }
    }

    private void startStopWatch()
    {
        roundButton.setClickable(true);
        finishedButton.setClickable(true);
        timePassedInSeconds = 0;
        roundCounter = 0;
        list.clear();
        adapter.notifyDataSetChanged();
        handler.removeCallbacks(this);
        handler.postDelayed(this, 100);
    }

    private void addRound()
    {
        roundCounter++;
        list.add(0, createTimeString());
        adapter.notifyDataSetChanged();
    }
    
    private void stopStopWatch()
    {
        initializations();
        addRound();
        handler.removeCallbacks(this);
    }

    private String createTimeString()
    {
        int seconds;
        int minutes;
        int hours;
        hours = timePassedInSeconds>3600 ? timePassedInSeconds/3600 : 0;
        minutes = (timePassedInSeconds-hours*3600)>60 ? (timePassedInSeconds-hours*3600)/60 : 0;
        seconds = (timePassedInSeconds-hours*3600-minutes*60)>0 ? (timePassedInSeconds-hours*3600-minutes*60) : 0;
        return "Round " + String.valueOf(roundCounter) + ": " + String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
    }

    public void run()
    {   
        if(powermanager.isScreenOn())
            countUpScreenOn();
        else
            countUpScreenOff();
    }
    
    private void countUpScreenOff()
    {
        wakelock.acquire();
        timePassedInSeconds++;
        handler.postDelayed(this, 1000);
        wakelock.release();
    }

    private void countUpScreenOn()
    {
        timePassedInSeconds++;
        refreshScreen();
        stopwatchTime.setVisibility(View.VISIBLE);
        handler.postDelayed(this, 1000);
    }
    
    private void refreshScreen()
    {
        int seconds;
        int minutes;
        int hours;
        hours = timePassedInSeconds>3600 ? timePassedInSeconds/3600 : 0;
        minutes = (timePassedInSeconds-hours*3600)>60 ? (timePassedInSeconds-hours*3600)/60 : 0;
        seconds = (timePassedInSeconds-hours*3600-minutes*60)>0 ? (timePassedInSeconds-hours*3600-minutes*60) : 0;
        stopwatchTime.setText(hours + ":" + minutes + ":" + seconds);
    }

}
