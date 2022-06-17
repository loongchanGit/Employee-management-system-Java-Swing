package Main;

import Forms.MainForm;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Nowtime extends JFrame implements Runnable {
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            try {
                Date date = new Date();
                SimpleDateFormat yearFormat= new SimpleDateFormat("yyyy年MM月dd日");
                SimpleDateFormat dayFormat= new SimpleDateFormat("hh:mm:ss");
                MainForm.labelyaer.setText(yearFormat.format(date));//把当前的系统时间赋予到我们自定义的JLabel中
                MainForm.labelday.setText(dayFormat.format(date));
                Thread.sleep(100);//Thread.Sleep()方法用于将当前线程休眠一定时间，单位为毫秒。这里为每100毫秒休眠一次线程。
            } catch (InterruptedException e) {
                e.printStackTrace();//抛出异常
            }
        }
    }
}

