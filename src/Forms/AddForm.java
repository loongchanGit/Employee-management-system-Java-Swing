package Forms;

import DButil.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AddForm extends JFrame{
    private static PreparedStatement pstmt1;
    private Connection conn;
    public AddForm(){
        setTitle("增加记录");//设置窗体名字
        setResizable(false);//禁止改变窗口大小
        setLayout(null);//窗体布局为null
        setBounds(800,100,400,800);
        Font font=new Font("黑体",Font.PLAIN,17);

        JLabel jLabel=new JLabel("id:");
        jLabel.setBounds(80,60,100,40);
        jLabel.setVisible(true);
        jLabel.setFont(font);
        getContentPane().add(jLabel);

        JTextField jTextField = new JTextField();
        jTextField.setBounds(180,60,120,40);
        jTextField.setVisible(true);
        getContentPane().add(jTextField);

        JLabel jLabel2=new JLabel("name:");
        jLabel2.setBounds(80,130,100,40);
        jLabel2.setVisible(true);
        jLabel2.setFont(font);
        getContentPane().add(jLabel2);

        JTextField jTextField2 = new JTextField();
        jTextField2.setBounds(180,130,120,40);
        jTextField2.setVisible(true);
        getContentPane().add(jTextField2);

        JLabel jLabel3=new JLabel("gender:");
        jLabel3.setBounds(80,200,100,40);
        jLabel3.setVisible(true);
        jLabel3.setFont(font);
        getContentPane().add(jLabel3);

        JTextField jTextField3 = new JTextField();
        jTextField3.setBounds(180,200,120,40);
        jTextField3.setVisible(true);
        getContentPane().add(jTextField3);

        JLabel jLabel4=new JLabel("position:");
        jLabel4.setBounds(80,270,100,40);
        jLabel4.setVisible(true);
         jLabel4.setFont(font);
        getContentPane().add(jLabel4);

        JTextField jTextField4 = new JTextField();
        jTextField4.setBounds(180,270,120,40);
        jTextField4.setVisible(true);
        getContentPane().add(jTextField4);

        JLabel jLabel5=new JLabel("salary:");
        jLabel5.setBounds(80,340,100,40);
        jLabel5.setVisible(true);
        jLabel5.setFont(font);
        getContentPane().add(jLabel5);

        JTextField jTextField5 = new JTextField();
        jTextField5.setBounds(180,340,120,40);
        jTextField5.setVisible(true);
        getContentPane().add(jTextField5);

        JLabel jLabel6=new JLabel("telephone:");
        jLabel6.setBounds(80,420,100,40);
        jLabel6.setVisible(true);
        jLabel6.setFont(font);
        getContentPane().add(jLabel6);

        JTextField jTextField6 = new JTextField();
        jTextField6.setBounds(180,420,120,40);
        jTextField6.setVisible(true);
        getContentPane().add(jTextField6);

        JButton jButton=new JButton("确定");
        jButton.setBounds(70,600,100,50);
        jButton.setVisible(true);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conn=DBConnection.getDBConnection();//注册驱动程序
                //所有文本框为空
                if(jTextField.getText().equals("") || jTextField2.getText().equals("") || jTextField3.getText().equals("") || jTextField4.getText().equals("") || jTextField5.getText().equals("") || jTextField6.getText().equals("")){//文本框为空的情况
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "文本框不能为空", "格式错误", JOptionPane.INFORMATION_MESSAGE);
                }else if(!isNumber(jTextField5.getText())){//文本框不为数字情况
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "salary格式错误", "格式错误", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    String id=jTextField.getText();//获取各个文本框的值
                    String name=jTextField2.getText();
                    String gender=jTextField3.getText();
                    String position=jTextField4.getText();
                    String telephone=jTextField6.getText();
                    double salary=Double.valueOf(jTextField5.getText());
                    try {
                        pstmt1 = conn.prepareStatement("insert into tb_staff values(?,?,?,?,?,?,?);");
                        pstmt1.setString(1,id);
                        pstmt1.setString(2,name);
                        pstmt1.setString(3,gender);
                        pstmt1.setString(4,position);
                        pstmt1.setDouble(5,salary);
                        pstmt1.setString(6,telephone);
                        pstmt1.setInt(7,0);
                        pstmt1.execute();
                    } catch (SQLException throwables) {
                        System.out.println("插入错误");
                        throwables.printStackTrace();
                    }
                    setVisible(false);
                    MainForm.refresh();
                }


            }



        });
        getContentPane().add(jButton);

        JButton jButton2=new JButton("取消");
        jButton2.setBounds(200,600,100,50);
        jButton2.setVisible(true);
        jButton2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        getContentPane().add(jButton2);

        setVisible(true);



    }

    public static boolean isNumber(String string) {
        if (string == null)
            return false;
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//正则表达式
        return pattern.matcher(string).matches();
    }



}
