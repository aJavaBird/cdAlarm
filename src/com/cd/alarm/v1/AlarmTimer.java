package com.cd.alarm.v1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class AlarmTimer {
	private Timer timer = null;
	InputStream in = null;
	private AudioStream as = null;
	private boolean isPlay = true;
	
	public AlarmTimer(int hour,int min){
		if(hour>=0 && hour<24){
			Constants.ALARM_HOUR = hour;
		}
		if(min>=0 && min<60){
			Constants.ALARM_MIN = min;
		}				
		timer = new Timer();	
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
		try {			
			in = new FileInputStream (Constants.FILE_PATH);// 打 开 一 个 声 音 文 件 流 作 为 输 入
			as = new AudioStream (in);// 用 输 入 流 创 建 一 个AudioStream 对 象 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(as != null){			
			AudioPlayer.player.start (as); //“player” 是AudioPlayer 中 一 静 态 成 员 用 于 控 制 播 放 
			int playSecond = Constants.ALARM_SECONDS;
			for(long i=0;isPlay&&i<playSecond;i++){
				try {
					System.out.println("======音乐还有"+(playSecond-i)+"秒后关闭=====");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			AudioPlayer.player.stop (as);			
			System.out.println("======音乐已关闭=====");
		}
	}
	
	public boolean stopMusic(){
		isPlay = false;
		if(as != null){
			AudioPlayer.player.stop (as);
			System.out.println("======音乐已关闭=====");
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
	
	public static String getCurrentTime(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sf.format(new Date());
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
		System.out.println("响铃事件---"+AlarmTimer.getCurrentTime());
		if(!Constants.ALARM_AGAIN){
			times = 0;
		}
		if(times>0){
			System.out.println("倒数第"+times+"次响玲");
			alTimer.playMusic();
			--times;
		}else{
			System.out.println("响铃Over--"+AlarmTimer.getCurrentTime());
			alTimer.stopMusic();
			this.cancel();
			alTimer.getTimer().cancel();
			alTimer.getTimer().purge();
		}	
	}
}
