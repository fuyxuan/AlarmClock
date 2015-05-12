package com.example.alarmclock;

import com.example.alarmclock.showDialog.AlarmReceiverDialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {  
    

	@Override  
    public void onReceive(final Context context, Intent intent) {  
        AlarmReceiverDialog alarmReceiverDialog = new AlarmReceiverDialog(context);
        alarmReceiverDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);  
        alarmReceiverDialog.show();  
    }  
}  