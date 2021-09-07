package com.example.baothuc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Notification_Activity extends AppCompatActivity implements View.OnClickListener {
    private int notifyId = 1;
    Button btnSetAlarm, btnCancelAlarm;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        init();
        btnSetAlarm.setOnClickListener(this);
        btnCancelAlarm.setOnClickListener(this);
    }

    private void init()
    {
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        btnCancelAlarm = findViewById(R.id.btnCancelAlarm);
        timePicker = findViewById(R.id.timePicker);
    }
    @Override
    public void onClick(View v) {
        int alarmRequestCode = (int)(System.currentTimeMillis() % 1000000000);  //id for requestCode
        Intent intent = new Intent(Notification_Activity.this, NotificationReceiver.class);
        intent.putExtra("notifyId", notifyId);
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(Notification_Activity.this, 0, intent,
//                PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(Notification_Activity.this, alarmRequestCode, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        switch(v.getId())
        {
            case R.id.btnSetAlarm:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();
                //alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        alarmStartTime, AlarmManager.INTERVAL_DAY, alarmIntent);
                Toast.makeText(this, "Set Alarm", Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                String selectedTime = hour+" : "+minute;
                returnIntent.putExtra("ALARM_CODE",alarmRequestCode);
                returnIntent.putExtra("ALARM_TIME", selectedTime);
                setResult(RESULT_OK, returnIntent);
                finish();

                break;

            case R.id.btnCancelAlarm:
                alarmManager.cancel(alarmIntent);
                Toast.makeText(this, "Cancel Alarm", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
