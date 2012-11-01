package de.rainerzufall.uglyclock;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.widget.Toast;


public class UglyTimerBroadcastReceiver
{
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "Time's over!", Toast.LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        RingtoneManager ringtoneManager = new RingtoneManager(context);
        ringtoneManager.getRingtone(RingtoneManager.TYPE_ALARM).play();
    }
}
