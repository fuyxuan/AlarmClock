package com.example.alarmclock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.alarmclock.fragment.DatePickerFragment;
import com.example.alarmclock.fragment.TimePickerFragment;
import com.example.alarmclock.fragment.TimePickerFragment.OnTimePicked;
import com.example.alarmclock.fragment.DatePickerFragment.OnDatePicked;
import com.example.alarmclock.showDialog.AlarmReceiverDialog;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Button btnDate, btnTime,btnSubmitTime;
	private TextView tvSetDate,tvSetTime;
	private Context context;
	private Calendar c;
	private int systemVersion;
	private int year,month,day,hour,mintue;
	private boolean isCheckDate , isCheckTime , isAlarmBusy;
	private int userSetDate ,nowDate;
	private int 
		nowYear ,
		nowMonth ,
		nowDay ,
		nowHour ,
		nowMinute ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		setContentView(R.layout.activity_main);
		findView();
		systemVersion = Integer.parseInt(VERSION.SDK);
		Log.i("msg","systemVersion:"+systemVersion);
		
		
	}

	

	private void findView(){
		context = this;
		btnDate = (Button) findViewById(R.id.btnDatePickerDialog);
		//btnTime = (Button) findViewById(R.id.btnTimePickerDialog);
		btnSubmitTime = (Button) findViewById(R.id.btnSubmitTime);
		tvSetDate = (TextView) findViewById(R.id.tvSetDate);
		tvSetTime = (TextView) findViewById(R.id.tvSetTime);
		btnDate.setOnClickListener(this);
		//btnTime.setOnClickListener(this);
		btnSubmitTime.setOnClickListener(this);
		
		
	}
	
	@SuppressLint("NewApi") @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDatePickerDialog:
			
			c = Calendar.getInstance();
			nowYear = c.get(Calendar.YEAR);
			nowMonth = c.get(Calendar.MONTH);
			nowMonth+=1;
			nowDay = c.get(Calendar.DAY_OF_MONTH);
			nowHour = c.get(Calendar.HOUR_OF_DAY);
			nowMinute = c.get(Calendar.MINUTE);
			
			final DatePickerFragment showDatePicker = new DatePickerFragment(new OnDatePicked() {
				
				@Override
				public void onDatePicked(int year, int month, int day) {
					
					String monthStr=month+"",dayStr=day+"",nowMonthStr=nowMonth+"",nowDayStr=nowDay+"";
					if(monthStr.length() == 1){
						monthStr = "0"+monthStr;
					}
					if(dayStr.length() == 1){
						dayStr = "0"+dayStr;
					}
					if(nowMonthStr.length() ==1){
						nowMonthStr = "0"+nowMonthStr;
					}
					if(nowDayStr.length() == 1){
						nowDayStr = "0"+nowDayStr;
					}
					
					nowDate =Integer.parseInt(nowYear+""+nowMonthStr+""+nowDayStr+"");
					userSetDate = Integer.parseInt(year+""+monthStr+""+dayStr+"");
					
					if(nowDate<=userSetDate  ){
						isCheckDate =true;
						MainActivity.this.year = year;
						MainActivity.this.month = month;
						MainActivity.this.day = day;
						tvSetDate.setText(year + "-" + monthStr + "-" + dayStr);
					}else{
						isCheckDate =false;
						Toast.makeText(context.getApplicationContext(),"請選擇今天獲今天以後的日期" ,Toast.LENGTH_SHORT).show();
						tvSetDate.setText("還沒設定");
					}
				}
			});
			if(systemVersion<10){
				showDatePicker.show(getFragmentManager(), "datePicker");
			}else{
				Calendar mCalendar = Calendar.getInstance();
				final DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
							boolean mFired = false;
							public void onDateSet(final DatePicker view, final int userSetYear, int userSetMonth, final int userSetDay) {
								userSetMonth++;
								String
									monthStr = userSetMonth + "",
									dayStr = userSetDay + "",
									nowMonthStr = nowMonth + "",
									nowDayStr = nowDay + "";
								if (monthStr.length() == 1) {
									monthStr = "0" + monthStr;
								}
								if (dayStr.length() == 1) {
									dayStr = "0" + dayStr;
								}
								if (nowMonthStr.length() == 1) {
									nowMonthStr = "0" + nowMonthStr;
								}
								if (nowDayStr.length() == 1) {
									nowDayStr = "0" + nowDayStr;
								}
								MainActivity.this.year = userSetYear;
								MainActivity.this.month = userSetMonth;
								MainActivity.this.day = userSetDay;

								nowDate = Integer.parseInt(nowYear + "" + nowMonthStr + "" + nowDayStr + "");
								userSetDate = Integer.parseInt(userSetYear + "" + monthStr + "" + dayStr + "");
								isCheckDate = true;
								if (userSetDate < nowDate) {
									Toast.makeText(MainActivity.this, "日期選擇錯誤", Toast.LENGTH_SHORT).show();
								} else {
									if (mFired == true) {// 解決DatePickerDialog會呼叫兩次問題
										TimePickerDialog time = new TimePickerDialog(MainActivity.this, new OnTimeSetListener() {
													@Override
													public void onTimeSet(TimePicker view, int userSetHour, int userSetMinute) {
														// Time
														Log.i("msg", "hour:"+ userSetHour + "  min"+ userSetMinute);
														String 
															hourStr = userSetHour + "", 
															mintueStr = userSetMinute + "",
															nowHourStr = nowHour + "",
															nowMintueStr = nowMinute + "";
														if (hourStr.length() == 1) {
															hourStr = "0" + hourStr;
														}
														if (mintueStr.length() == 1) {
															mintueStr = "0" + mintueStr;
														}
														if (nowHourStr.length() == 1) {
															nowHourStr = "0" + nowHourStr;
														}
														if (nowHourStr.length() == 1) {
															nowHourStr = "0" + nowHourStr;
														}

														MainActivity.this.hour = userSetHour;
														MainActivity.this.mintue = userSetMinute;

														int nowTime = Integer.parseInt(nowHourStr + "" + nowMintueStr);
														int userSetTime = Integer.parseInt(hourStr + "" + mintueStr);
														if (((nowDate == userSetDate) && (userSetTime > nowTime))) {
															isCheckTime = true;
															tvSetDate.setText(MainActivity.this.year
																			+ "-"
																			+ MainActivity.this.month
																			+ "-"
																			+ MainActivity.this.day);
															//tvSetTime.setText(hourStr+ " : "+ mintueStr);
															if(userSetHour>12){
																tvSetTime.setText("下午"+(userSetHour-12) + ":" + mintueStr);
															}else{
																tvSetTime.setText("上午"+userSetHour + ":" + mintueStr);
															}
															
															isAlarmBusy = true;

														} else if ((userSetDate > nowDate)) {
															isCheckTime = true;
															tvSetDate.setText(MainActivity.this.year
																			+ "-"
																			+ MainActivity.this.month
																			+ "-"
																			+ MainActivity.this.day);
															if(userSetHour>12){
																tvSetTime.setText("下午"+(userSetHour-12) + ":" + mintueStr);
															}else{
																tvSetTime.setText("上午"+userSetHour + ":" + mintueStr);
															}
															isAlarmBusy = true;
														} else {
															Toast.makeText(
																	context.getApplicationContext(),
																	"時間錯誤",
																	Toast.LENGTH_SHORT)
																	.show();
														}

													}
								}, nowHour, nowMinute, true);
								time.show();
					            return;
					        } else {
					            mFired = true;
					        }
						}
				    }
				}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
				dateDialog.show();
				break;
			}
			break;

//		case R.id.btnTimePickerDialog:
//			Toast.makeText(MainActivity.this,"沒有東東",Toast.LENGTH_SHORT).show();
//			if(false){
//				Toast.makeText(context.getApplicationContext(),"NULL" ,Toast.LENGTH_SHORT).show();
//			}else{
//				TimePickerFragment showTimePicker = new TimePickerFragment(
//						new OnTimePicked() {
//							@Override
//							@SuppressWarnings("unused")
//							public void onTimePicked(int hourlisten,int minutelisten) {
//								Log.i("msg", "getUserTime:" + hourlisten + ":"+ minutelisten);
//
//								String hourStr = hourlisten + "", mintueStr = minutelisten
//										+ "", nowHourStr = nowHour + "", nowMintueStr = nowMinute
//										+ "";
//								if (hourStr.length() == 1) {
//									hourStr = "0" + hourStr;
//								}
//								if (mintueStr.length() == 1) {
//									mintueStr = "0" + mintueStr;
//								}
//								if (nowHourStr.length() == 1) {
//									nowHourStr = "0" + nowHourStr;
//								}
//								if (nowHourStr.length() == 1) {
//									nowHourStr = "0" + nowHourStr;
//								}
//
//								int nowTime = Integer.parseInt(nowHourStr + ""+ nowMintueStr);
//								int userSetTime = Integer.parseInt(hourStr + ""+ mintueStr);
//
//								// Log.i("msg","nowTime:"+nowHour+":"+nowMinute+" compare: "+(nowTime<userSetTime));
//								Log.i("msg", "userTime:" + nowTime + ":"+ userSetTime + " compare: "+ (nowTime < userSetTime));
//
//								if ((nowTime < userSetTime)) {
//									isCheckTime = true;
//									MainActivity.this.hour = hourlisten;
//									MainActivity.this.mintue = minutelisten;
//									tvSetTime.setText(hourStr + ":" + mintueStr);
//								} else {
//									isCheckTime = false;
//									Toast.makeText(context.getApplicationContext(),"請選擇目前之後的時間", Toast.LENGTH_SHORT).show();
//									tvSetTime.setText("還沒設定");
//								}
//							}
//						});
//				
//				
//				if (systemVersion > 10) {
//					showTimePicker.show(getFragmentManager(), "TimePicker");
//				} else {
////					TimePickerDialog time = new TimePickerDialog(
////							MainActivity.this, new OnTimeSetListener() {
////
////								@Override
////								public void onTimeSet(TimePicker view,int hourOfDay, int minute) {
////									Toast.makeText(MainActivity.this,hourOfDay + "hour " + minute+ "minute",Toast.LENGTH_SHORT).show();
////								}
////							}, nowHour, nowMinute, true);
////					time.show();
//					
//				}
//			}
//			break;
		case R.id.btnSubmitTime:
			if((isCheckDate) && (isCheckTime)){
				setAlarm();
			}else{
				Toast.makeText(context.getApplicationContext(),"沒有選擇時間" ,Toast.LENGTH_SHORT).show();
			}
			break;
		}

	}
	
	private void setAlarm() {
		
		//時間差試算
		String 	alarmYearStr = String.format("%04d", year),
				alarmMonthStr = String.format("%02d", month),
				alarmDateStr = String.format("%02d", day),
				alarmHourStr = String.format("%02d", hour),
				alarmMintueStr = String.format("%02d", mintue),
				alarmNowHourStr = String.format("%02d", c.get(Calendar.HOUR_OF_DAY)),
			    alarmNowMintueStr = String.format("%02d", c.get(Calendar.MINUTE)),
			    alarmNowYearStr = String.format("%04d", c.get(Calendar.YEAR)),
			    alarmNowMonthStr = String.format("%02d", (c.get(Calendar.MONTH)+1)),
			    alarmNowDateStr = String.format("%02d", c.get(Calendar.DATE));
		int alarmSetTimeInt = Integer.parseInt("1"+alarmHourStr+""+alarmMintueStr),
			alarmNowTimeInt = Integer.parseInt("1"+alarmNowHourStr+""+alarmNowMintueStr);
		
		String usersetdtStr =alarmYearStr+"/"+alarmMonthStr+"/"+alarmDateStr +" "+alarmHourStr + ":" + alarmMintueStr,
			   nowdtStr =alarmNowYearStr+"/"+alarmNowMonthStr+"/"+alarmNowDateStr +" "+alarmNowHourStr + ":" + alarmNowMintueStr;
		Log.i("msg","nowdtStr:"+nowdtStr+"    alarmDateStr:"+usersetdtStr);
		//定義時間格式

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		//取的兩個時間

		Date dt1 = null,dt2 = null;
			try {
				dt1 = sdf.parse(nowdtStr);
				dt2 =sdf.parse(usersetdtStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		Long ut1=dt1.getTime();
		Long ut2=dt2.getTime();
		Long timeP=ut2-ut1;//毫秒差
		Long difMin=timeP/(1000*60);//分差
		Long difHr=timeP/(1000*60*60);//時差
		Long difDay=timeP/(1000*60*60*24);//日差
		if(difMin>= (60*24) ){
			Toast.makeText(context.getApplicationContext(),"耐心等候～" +difDay+ "天" +((difHr)-(difDay*24))+"小時"+ (difMin%60) +" 分" ,Toast.LENGTH_SHORT).show();
		}else if(difMin>=60){
			Toast.makeText(context.getApplicationContext(),"耐心等候～" + difHr+"小時"+(difMin%60) +" 分" ,Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(context.getApplicationContext(),"耐心等候～" + (difMin%60) +" 分" ,Toast.LENGTH_SHORT).show();
		}
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 10);
		Intent intent = new Intent(this.context, AlarmReceiver.class);
		intent.putExtra("msg", "送訊息");
		PendingIntent pi = PendingIntent.getBroadcast(this.context, 1, intent, PendingIntent.FLAG_ONE_SHOT);
		AlarmManager am = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
		
	}
}
