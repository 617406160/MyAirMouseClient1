package com.example.myairmouseclient1;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class SensorControlActivity extends Activity {
	private float current_x;// 鼠标现在的坐标
	private float current_y;
	private float new_x;// 更新后的坐标
	private float new_y;
	private float delta_x;// 坐标变化
	private float delta_y;
	private float currentangle_x;// 鼠标现在的坐标
	private float currentangle_y;
	private float newangle_x;// 更新后的坐标
	private float newangle_y;
	private float deltaangle_x;// 坐标变化
	private float deltaangle_y;
	private float r = 10;
	private MessageSender sender;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_control);
		
		Button button = (Button) findViewById(R.id.btn_press);
		button.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gyromouse();
				}
				
				return false;
			}
		});
		
		
		
	}


	private void gyromouse() {
		SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);// 注册系统传感器监听权限
		Sensor gryo = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);// 获取要监听的传感器类型
		manager.registerListener(new SensorEventListener() {// 实现监听

					@Override
					public void onSensorChanged(SensorEvent event) {
						newangle_x = event.values[0];
						newangle_y = event.values[1];
						deltaangle_x = newangle_x - currentangle_x;
						deltaangle_y = newangle_y - currentangle_y;
						delta_x = deltaangle_x * r ;
						delta_y = deltaangle_y * r;
						currentangle_x = newangle_x;
						currentangle_y = newangle_y;
						String message = "mouse:"+delta_x+","+delta_y;
						sender.send(message);
					

					}

					@Override
					public void onAccuracyChanged(Sensor sensor, int accuracy) {
						// TODO Auto-generated method stub

					}
				}, gryo, SensorManager.SENSOR_DELAY_UI);
	}

}
