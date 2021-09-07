package com.example.baothuc;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    Activity activity;
    ArrayList<AlarmModel> arrayList;

    public AlarmAdapter (Activity activity, ArrayList<AlarmModel>arrayList)
    {
        this.activity = activity;
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alarm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.ViewHolder holder, int position) {
        AlarmModel model = arrayList.get(position);
        holder.tvTime.setText(model.getTimeAlarm());
        holder.btnCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm(activity.getApplicationContext(), model, position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTime;
        Button btnCancelAlarm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_timeAlarm);
            btnCancelAlarm = itemView.findViewById(R.id.btnCancel);
        }
    }

    public void cancelAlarm(Context context, AlarmModel alarmModel, int position)
    {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent myIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                getApplicationContext(), requestCode, myIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.cancel(pendingIntent);

        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Xác nhận?")
                .setMessage("Bạn có muốn xóa báo thức "+alarmModel.getTimeAlarm()+" này?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int requestCode = alarmModel.getCodeAlarm();
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        Intent myIntent = new Intent(context, NotificationReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode, myIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.cancel(pendingIntent);

                        String queryDelete = "DELETE FROM Alarm WHERE RequestCode = '"+requestCode+"'";
                        DatabaseHelper databaseHelper = new DatabaseHelper(context, "baothucdb.sql", null, 1);
                        databaseHelper.queryData(queryDelete);

                        arrayList.remove(position);
                        notifyDataSetChanged();

                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }
}
