package com.cd.alarm.v1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Alarm {
	
	private JFrame alarmFrame;
//	private JPanel alarmPanel;
	JLabel label1,label2,label3,label4;
    JTextField tf1,tf2,tf3,tf4;
    JButton bt1,bt2;
    AlarmTimer alTimer = null;
	
	public Alarm(){
		
		label1=new JLabel("闹钟时间（HH:mm）");
        label2=new JLabel("闹钟音乐（wav）");
        label3=new JLabel("响铃间隔（分钟）");
        label4=new JLabel("响铃次数");
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        tf1.setText("07:00");
        tf3.setText("5");
        tf4.setText("5");
        bt1 = new JButton("启动闹钟");
        bt2 = new JButton("稍后再响");
        bt2.setVisible(false);
        bt1.addActionListener(getAlarmActionListener());
        bt2.addActionListener(getAlarmLaterActionListener());
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		/*System.out.println("屏幕宽："+screensize.width+"；屏幕高："+screensize.height);*/
        alarmFrame = new JFrame(Constants.TITLE);
        alarmFrame.setVisible(true);//使窗体可视
//        alarmFrame.setSize(200, 150);//设置窗体大小
        
        alarmFrame.setBounds((int)(screensize.width*0.4),(int)(screensize.height*0.3),
        		Constants.ALARM_WIDTH,Constants.ALARM_HEIGHT);//设置窗体的位置和大小
	        //设置窗体的关闭方式
        alarmFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        GroupLayout layout = new GroupLayout(alarmFrame.getContentPane());        
      //创建GroupLayout的水平连续组，，越先加入的ParallelGroup，优先级级别越高。
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);//添加间隔
        hGroup.addGroup(layout.createParallelGroup().addComponent(label1)
                .addComponent(label2).addComponent(label3).addComponent(label4));
        hGroup.addGap(5);//添加间隔
        hGroup.addGroup(layout.createParallelGroup().addComponent(tf1)
                .addComponent(tf2).addComponent(tf3).addComponent(tf4)
                .addComponent(bt1).addComponent(bt2));
        hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);
      //创建GroupLayout的垂直连续组，，越先加入的ParallelGroup，优先级级别越高。
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(10);//添加间隔
        vGroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(tf1));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label2).addComponent(tf2));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label3).addComponent(tf3));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label4).addComponent(tf4));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(bt1));        
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(bt2));
        vGroup.addGap(10);
        //设置垂直组
        layout.setVerticalGroup(vGroup);
        alarmFrame.setLayout(layout);
	}
	
	private ActionListener getAlarmActionListener(){
		 return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("启动/停止闹钟--"+AlarmTimer.getCurrentTime());
				if(!Constants.ALARM_OK){
					if(init()){						
						Constants.ALARM_AGAIN = true;
						int hour = getHour(),min = getMin();
						System.out.println("闹钟时间:"+hour+":"+min);
						alTimer = new AlarmTimer(hour,min);
						alTimer.startAlarm();
						bt1.setText("停止闹钟");
						bt2.setVisible(true);
						Constants.ALARM_OK = true;
						System.out.println("闹钟启动成功--"+AlarmTimer.getCurrentTime());
					}else{
						JOptionPane.showConfirmDialog(null,"请正确填写闹钟参数！！","参数错误",JOptionPane.YES_NO_OPTION);
					}					
				}else{
					System.out.println("闹钟关闭成功--"+AlarmTimer.getCurrentTime());
					alTimer.setPlay(false);
					alTimer.getTimer().cancel();
					alTimer.getTimer().purge();
					Constants.ALARM_AGAIN = false;
					bt1.setText("启动闹钟");
					bt2.setVisible(false);					
					Constants.ALARM_OK = false;
				}				
			}
			
			private boolean init(){
				String text1 = tf1.getText();
				String text2 = tf2.getText();
				String text3 = tf3.getText();
				String text4 = tf4.getText();
				String pattern = "[0-2]{0,1}[0-9]{1}:[0-5]{0,1}[0-9]{1}";
				if(text1!=null){
					text1 = text1.replace("：",":");
				}else{
					return false;
				}				
			    if(!Pattern.matches(pattern,text1)){
			    	return false;
			    }else{
			    	if(Integer.parseInt(text1.split(":")[0])>=24){
			    		return false;
			    	}
			    }
				if(text2!=null && !"".equals(text2)){
					text2 = text2.replace("\\\\", "/");					
					Constants.FILE_PATH = text2;
				}else{
					return false;
				}
				if(text3!=null && !"".equals(text3)){
					String pattern3 = "[0-9]{1,}";
				    if(!Pattern.matches(pattern3,text3)){
				    	return false;
				    }else if(Integer.parseInt(text3)<=0){
				    	return false;
				    }
					Constants.EVERY_OTHER_TIME = Integer.parseInt(text3);
					if(Constants.EVERY_OTHER_TIME>2){
						Constants.ALARM_SECONDS = 150;
					}else if(Constants.EVERY_OTHER_TIME>1){
						Constants.ALARM_SECONDS = 90;
					}
				}else{
					return false;
				}
				if(text4!=null && !"".equals(text4)){
					String pattern4 = "[0-9]{1,}";
				    if(!Pattern.matches(pattern4,text4)){
				    	return false;
				    }else if(Integer.parseInt(text4)<=0){
				    	return false;
				    }
					Constants.ALARM_TIMES = Integer.parseInt(text4);
				}else{
					return false;
				}
				return true;
			}
			
			private int getHour(){
				String text = tf1.getText();
				text = text.replace("：",":");
				if(text.indexOf(":")>0 && text.indexOf(":")<text.length()-1){
					return Integer.parseInt(text.split(":")[0]);
				}
				return 8;
			}
			private int getMin(){
				String text = tf1.getText();
				text = text.replace("：",":");
				if(text.indexOf(":")>0 && text.indexOf(":")<text.length()-1){
					return Integer.parseInt(text.split(":")[1]);
				}
				return 0;
			}
        };
	}
	
	private ActionListener getAlarmLaterActionListener(){
		 return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {				
				System.out.println("稍后再响--"+AlarmTimer.getCurrentTime());
				alTimer.setPlay(false);
			}
       };
	}
}
