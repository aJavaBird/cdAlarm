package com.cd.alarm.v1;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Constants {
	/**名称*/
	public static String TITLE = "alarm v1.0";//清河阳光闹钟 v1.0
	/**闹钟是否启动*/
	public static boolean ALARM_OK = false;
	/**播放音乐文件*/
	public static String FILE_PATH = "F:/music/trippygaia1.mid";
	/**响铃时间，格式：HH:mm:ss,默认8点*/
	public static String ALARM_TIME = "08:00:00";
	/**响铃时，为ALARM_TIME的分解*/
	public static int ALARM_HOUR = 8;
	/**响铃分，为ALARM_TIME的分解*/
	public static int ALARM_MIN = 0;
	/**每隔多久响一次,单位分钟*/
	public static int EVERY_OTHER_TIME = 1;
	/**每次响铃秒数*/
	public static int ALARM_SECONDS = 58;
	/**响铃次数*/
	public static int ALARM_TIMES = 2;	
	/**闹钟最小宽*/
	public static int ALARM_WIDTH = 350;
	/**闹钟最小高*/
	public static int ALARM_HEIGHT = 250;
	/**闹钟是否会再次响*/
	public static boolean ALARM_AGAIN = true;
}
