package com.example.baothuc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnCreate;

    ArrayList<AlarmModel> arrayList = new ArrayList<AlarmModel>();
    AlarmAdapter adapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //tao database BaoThuc
        databaseHelper = new DatabaseHelper(this, "baothucdb.sql", null, 1);
        //tao table Alarm
        databaseHelper.queryData("CREATE TABLE IF NOT EXISTS Alarm(Id INTEGER PRIMARY KEY AUTOINCREMENT, RequestCode INTEGER, Time VARCHAR(200) )");

        getData();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNotify = new Intent(getApplicationContext(), Notification_Activity.class);
                startActivityForResult(intentNotify, 123);
            }
        });
    }

    private void init()
    {
        btnCreate = findViewById(R.id.btnCreate);
        recyclerView = findViewById(R.id.recyle_view);
    }


    private void getData()
    {
        //select
        Cursor dataAlarm = databaseHelper.getData("SELECT * FROM Alarm");
        while(dataAlarm.moveToNext())
        {
            int codeAlarm = dataAlarm.getInt(1);
            String timeAlarm = dataAlarm.getString(2);
            AlarmModel alarmModel = new AlarmModel(codeAlarm, timeAlarm);
            arrayList.add(alarmModel);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlarmAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
    }

    //Activity hen gio tra ve gia tri
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==  123)
        {
            if(resultCode == RESULT_OK)
            {
                int resultRequestCode = data.getIntExtra("ALARM_CODE", 0);
                String resultTime = data.getStringExtra("ALARM_TIME");
                AlarmModel alarmModel = new AlarmModel(resultRequestCode, resultTime);
                createAlarm(alarmModel);
            }
        }
    }

    //Tao bao thuc
    private void createAlarm(AlarmModel alarmModel)
    {
        String queryInsert =  "INSERT INTO Alarm VALUES(null, "+ alarmModel.getCodeAlarm()+", '"+ alarmModel.getTimeAlarm()+"')";
        databaseHelper.queryData(queryInsert);
        //getData();
        arrayList.add(alarmModel);
        adapter.notifyDataSetChanged();
    }

}