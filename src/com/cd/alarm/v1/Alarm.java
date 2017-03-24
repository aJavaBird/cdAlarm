package com.cd.alarm.v1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		
		label1=new JLabel("����ʱ�䣨HH:mm��");
        label2=new JLabel("�������֣�wav��");
        label3=new JLabel("�����������ӣ�");
        label4=new JLabel("�������");
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        bt1 = new JButton("��������");
        bt2 = new JButton("�Ժ�����");
        bt2.setVisible(false);
        bt1.addActionListener(getAlarmActionListener());
        bt2.addActionListener(getAlarmLaterActionListener());
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		/*System.out.println("��Ļ��"+screensize.width+"����Ļ�ߣ�"+screensize.height);*/
        alarmFrame = new JFrame(Constants.TITLE);
        alarmFrame.setVisible(true);//ʹ�������
//        alarmFrame.setSize(200, 150);//���ô����С
        
        alarmFrame.setBounds((int)(screensize.width*0.4),(int)(screensize.height*0.3),
        		Constants.ALARM_WIDTH,Constants.ALARM_HEIGHT);//���ô����λ�úʹ�С
	        //���ô���Ĺرշ�ʽ
        alarmFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        GroupLayout layout = new GroupLayout(alarmFrame.getContentPane());        
      //����GroupLayout��ˮƽ�����飬��Խ�ȼ����ParallelGroup�����ȼ�����Խ�ߡ�
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);//��Ӽ��
        hGroup.addGroup(layout.createParallelGroup().addComponent(label1)
                .addComponent(label2).addComponent(label3).addComponent(label4));
        hGroup.addGap(5);//��Ӽ��
        hGroup.addGroup(layout.createParallelGroup().addComponent(tf1)
                .addComponent(tf2).addComponent(tf3).addComponent(tf4)
                .addComponent(bt1).addComponent(bt2));
        hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);
      //����GroupLayout�Ĵ�ֱ�����飬��Խ�ȼ����ParallelGroup�����ȼ�����Խ�ߡ�
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(10);//��Ӽ��
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
        //���ô�ֱ��
        layout.setVerticalGroup(vGroup);
        alarmFrame.setLayout(layout);
	}
	
	private ActionListener getAlarmActionListener(){
		 return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("btn1����¼�--"+Constants.ALARM_OK);
				if(!Constants.ALARM_OK){
					if(init()){
						Constants.ALARM_AGAIN = true;
						int hour = getHour(),min = getMin();
						System.out.println("����ʱ��:"+hour+":"+min);
						alTimer = new AlarmTimer(hour,min);
						alTimer.startAlarm();
						bt1.setText("ֹͣ����");
						bt2.setVisible(true);
						Constants.ALARM_OK = true;
					}else{
						System.out.println("��ʼ������ʧ�ܣ���ȷ������дʱ�䡢�����ļ��������������������Ƿ���ȷ��");
					}
					
				}else{
					alTimer.setPlay(false);
					alTimer.getTimer().cancel();
					alTimer.getTimer().purge();
					Constants.ALARM_AGAIN = false;
					bt1.setText("��������");
					bt2.setVisible(false);					
					Constants.ALARM_OK = false;
				}				
			}
			
			private boolean init(){
				String text1 = tf1.getText();
				String text2 = tf2.getText();
				String text3 = tf3.getText();
				String text4 = tf4.getText();
				if(text2!=null && !"".equals(text2)){
					text2 = text2.replace("\\\\", "/");
					Constants.FILE_PATH = text2;
				}
				if(text3!=null && !"".equals(text3)){
					Constants.EVERY_OTHER_TIME = Integer.parseInt(text3);
				}
				if(text4!=null && !"".equals(text4)){
					Constants.ALARM_TIMES = Integer.parseInt(text4);
				}
				return true;
			}
			
			private int getHour(){
				String text = tf1.getText();
				text = text.replace("��",":");
				if(text.indexOf(":")>0 && text.indexOf(":")<text.length()-1){
					return Integer.parseInt(text.split(":")[0]);
				}
				return 8;
			}
			private int getMin(){
				String text = tf1.getText();
				text = text.replace("��",":");
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
				System.out.println("btn1����¼�--"+Constants.ALARM_OK);
				alTimer.setPlay(false);
			}
       };
	}
}
