package com.example.alarmclock.showDialog;

import com.example.alarmclock.R;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.text.AndroidCharacter;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class AlarmReceiverDialog extends Dialog {
	private Context context;
	private Button btnCheckAlarm;
	private Vibrator myVibrator;
	private MediaPlayer mMediaPlayer;
	private int ALARM_TIME=10000;

	public AlarmReceiverDialog(Context context) {
		super(context);
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_alarm_receiver);
		findView();
		setAction();
	}

	private void findView() {
		btnCheckAlarm = (Button) findViewById(R.id.btnCheckAlarm);
		mMediaPlayer = MediaPlayer.create(context, R.raw.alarm);
	}

	private void setAction() {
		setVibrate(ALARM_TIME); // 設定震動10秒
		mMediaPlayer.start();
		mMediaPlayer.setLooping(true);// 重複循環播放
		btnCheckAlarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVibrator.cancel();// 關閉振動
				dismiss();// 關閉dialog
				mMediaPlayer.stop();// 關閉音樂
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(ALARM_TIME);
					myVibrator.cancel();// 關閉振動
					dismiss();// 關閉dialog
					mMediaPlayer.stop();// 關閉音樂
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void setVibrate(int time) {
		myVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		myVibrator.vibrate(time);
		mMediaPlayer.start();
	}

}
