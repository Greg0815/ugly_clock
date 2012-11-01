package de.rainerzufall.uglyclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UglyClockActivity extends Activity implements OnClickListener
{

	Button alarmsButton;
	Button timerButton;
	Button stopWatchButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ugly_clock);
        alarmsButton = (Button)findViewById(R.id.UglyAlarmsButton);
        alarmsButton.setOnClickListener(this);
        timerButton = (Button)findViewById(R.id.UglyTimerButton);
        timerButton.setOnClickListener(this);
        stopWatchButton = (Button)findViewById(R.id.UglyStopWatchButton);
        stopWatchButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_ugly_clock, menu);
        return true;
    }

	public void onClick(View view)
	{
		if(view.getId()==R.id.UglyAlarmsButton)
		{
			startActivity(new Intent(this, AlarmsActivity.class));
		}
		else if(view.getId()==R.id.UglyTimerButton)
		{
		    startActivity(new Intent(this, TimerActivity.class));
		}
		else if(view.getId()==R.id.UglyStopWatchButton)
		{
		    startActivity(new Intent(this, StopWatchActivity.class));
		}
	}
}
