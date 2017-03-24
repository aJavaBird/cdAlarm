package com.cd.alarm.v1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class AlarmTimer {
	private Timer timer = null;
	private AudioStream as = null;
	private boolean isPlay = true;
	
	public AlarmTimer(int hour,int min){
		if(hour>=0 && hour<24){
			Constants.ALARM_HOUR = hour;
		}
		if(min>=0 && min<60){
			Constants.ALARM_MIN = min;
		}		
		InputStream in = null;
		try {
			in = new FileInputStream (Constants.FILE_PATH);// �� �� һ �� �� �� �� �� �� �� Ϊ �� ��
			as = new AudioStream (in);// �� �� �� �� �� �� һ ��AudioStream �� �� 
			timer = new Timer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void startAlarm(){
		Date firstDate = null;
		Date now = new Date();
		Calendar c = Calendar.getInstance();			
		c.set(Calendar.HOUR_OF_DAY,Constants.ALARM_HOUR);
		c.set(Calendar.MINUTE,Constants.ALARM_MIN);
		c.set(Calendar.SECOND,0);
		if(now.before(c.getTime())){
			firstDate = c.getTime();
		}else{
			c.add(Calendar.DAY_OF_MONTH,1);
			firstDate = c.getTime();
		}		
		timer.schedule(new AlarmTimerTask(Constants.ALARM_TIMES,this),firstDate,Constants.EVERY_OTHER_TIME*1000*60);
	}
	
	public void playMusic(){
		isPlay = true;
		if(as != null){
			AudioPlayer.player.start (as); //��player�� ��AudioPlayer �� һ �� ̬ �� Ա �� �� �� �� �� �� 
			int playSecond = Constants.ALARM_SECONDS;
			for(long i=0;isPlay&&i<playSecond;i++){
				try {
					System.out.println("======���ֻ���"+(playSecond-i)+"���ر�=====");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(!isPlay){
				AudioPlayer.player.stop (as);
			}			
			System.out.println("======�����ѹر�=====");
		}
	}
	
	public boolean stopMusic(){
		isPlay = false;
		if(as != null){
			AudioPlayer.player.stop (as);
			System.out.println("======�����ѹر�=====");
		}
		return true;
	}
	
	public Timer getTimer() {
		return timer;
	}
	public AudioStream getAs() {
		return as;
	}

	public boolean isPlay() {
		return isPlay;
	}

	public void setPlay(boolean isPlay) {
		this.isPlay = isPlay;
	}	
	
}

class AlarmTimerTask extends TimerTask{
	
	private int times = 0;
	AlarmTimer alTimer = null;

	public AlarmTimerTask(int times,AlarmTimer alTimer){
		this.times = times;
		this.alTimer = alTimer;
	}	
	@Override
	public void run() {
		System.out.println("----�����¼�---");
		if(!Constants.ALARM_AGAIN){
			times = 0;
		}
		if(times>0){
			System.out.println("����--"+times);
			alTimer.playMusic();
			--times;
		}else{
			System.out.println("����Over");
			alTimer.stopMusic();
			this.cancel();
			alTimer.getTimer().cancel();
			alTimer.getTimer().purge();
		}	
	}
}
