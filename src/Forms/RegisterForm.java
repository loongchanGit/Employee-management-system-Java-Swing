package Forms;

import DButil.DBManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

//注册窗口
    public class RegisterForm extends JFrame {

    private static final long serialVersionUID = 2491294229716316338L;
    private JPanel contentPane;
    private JTextField usernameTextField;//用户文本框
    private JPasswordField passwordField1;//第一次密码
    private JPasswordField passwordField2;//第二次密码
    private JTextField employeeField;
    private JTextField validateTextField;//验证码文本框
    private String randomText;//生成随机验证码字符串
    private JLabel tipLabel = new JLabel();// 显示提示信息

    public RegisterForm() {
        setTitle("用户注册");
        setResizable(false);//禁止改变窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//调用任意已注册 WindowListener 的对象后自动隐藏并释放该窗体
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        JPanel titlePanel = new JPanel();
        contentPane.add(titlePanel);
        JLabel titleLabel = new JLabel("欢迎注册");
        titleLabel.setFont(new Font("黑体", Font.PLAIN, 40));
        titlePanel.add(titleLabel);
        JPanel usernamePanel = new JPanel();
        contentPane.add(usernamePanel);
        JLabel usernameLabel = new JLabel("用户名：");
        usernameLabel.setFont(new Font("黑体", Font.PLAIN, 28));
        usernamePanel.add(usernameLabel);
        usernameTextField = new JTextField();
        usernameTextField.setToolTipText("请输入15个以字母数字组成的字符串");
        usernameTextField.setFont(new Font("黑体", Font.PLAIN, 24));
        usernamePanel.add(usernameTextField);
        usernameTextField.setColumns(10);
        JPanel passwordPanel1 = new JPanel();
        contentPane.add(passwordPanel1);
        JLabel passwordLabel1 = new JLabel("输入密码：");
        passwordLabel1.setFont(new Font("黑体", Font.PLAIN, 28));
        passwordPanel1.add(passwordLabel1);
        passwordField1 = new JPasswordField();
        passwordField1.setFont(new Font("黑体", Font.PLAIN, 24));
        passwordField1.setColumns(10);
        passwordPanel1.add(passwordField1);
        JPanel passwordPanel2 = new JPanel();
        contentPane.add(passwordPanel2);
        JLabel passwordLabel2 = new JLabel("确认密码：");
        passwordLabel2.setFont(new Font("黑体", Font.PLAIN, 28));
        passwordPanel2.add(passwordLabel2);
        passwordField2 = new JPasswordField();
        passwordField2.setFont(new Font("黑体", Font.PLAIN, 24));
        passwordField2.setColumns(10);
        passwordPanel2.add(passwordField2);
        JPanel employeePanel = new JPanel();
        contentPane.add(employeePanel);
        JLabel employeeLabel = new JLabel("员工编号：");
        employeeLabel.setFont(new Font("黑体", Font.PLAIN, 28));
        employeePanel.add(employeeLabel);
        employeeField = new JTextField();
        employeeField.setFont(new Font("黑体", Font.PLAIN, 24));
        employeeField.setColumns(10);
        employeePanel.add(employeeField);
        JPanel validatePanel = new JPanel();
        contentPane.add(validatePanel);
        JLabel validateLabel = new JLabel("验证码：");
        validateLabel.setFont(new Font("黑体", Font.PLAIN, 28));
        validatePanel.add(validateLabel);
        validateTextField = new JTextField();
        validateTextField.setFont(new Font("黑体", Font.BOLD, 24));
        validatePanel.add(validateTextField);
        validateTextField.setColumns(5);
        randomText = String.valueOf((int)(Math.random()*(9999-1000+1)+1000));
        JLabel label = new JLabel(randomText);//随机验证码
        label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        validatePanel.add(label);
        label.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                randomText = String.valueOf((int)(Math.random()*(9999-1000+1)+1000));//生成1000-9999随机数
                label.setText(randomText);
            }
        });

        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel);
        JButton submitButton = new JButton("注册");
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //判断注册两次密码是否相同，验证码是否正确，账号是否为空，密码是否为空，员工编号是否存在，判断员工编号是否已绑定
                    if(Arrays.equals(passwordField1.getPassword(),passwordField2.getPassword()) && randomText.equals(validateTextField.getText()) && !usernameTextField.getText().equals("") && !String.valueOf(passwordField1.getPassword()).equals("") && DBManager.existEmployee(employeeField.getText())==1 && DBManager.judgestate(employeeField.getText())==0) {
                        String username = usernameTextField.getText();
                        String password = String.valueOf(passwordField1.getPassword());
                        String employeeid=employeeField.getText();
                        int result=0;
                        result=DBManager.addUser(username, password,employeeid);
                        if(result!=0){//添加用户成功
                            DBManager.alterstate(employeeid,1);//修改员工信息状态为1表示已绑定
                            JOptionPane jOptionPane=new JOptionPane();
                            JOptionPane.showConfirmDialog(null, "注册成功！\n您的账号为："+username+"\n您的密码为:"+password+"\n绑定的员工编号为："+employeeid,"注册成功", JOptionPane.YES_NO_OPTION);
                            setVisible(false);
                        }
                    }else if(usernameTextField.getText().equals("")){
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "用户名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    }else if(String.valueOf(passwordField1.getPassword()).equals("")){
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    }else if(!Arrays.equals(passwordField1.getPassword(),passwordField2.getPassword()) ){//两次密码不一致
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "两次输入的密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
                    }else if(employeeField.getText().equals("")){
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "员工编号不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    }else if(!randomText.equals(validateTextField.getText())){//验证码不一致
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "验证码错误", "错误", JOptionPane.ERROR_MESSAGE);
                    }else if(DBManager.existEmployee(employeeField.getText())==0){
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "员工编号不存在", "错误", JOptionPane.ERROR_MESSAGE);
                    }else if(DBManager.judgestate(employeeField.getText())==1){
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "此员工编号已绑定", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        submitButton.setFont(new Font("黑体", Font.PLAIN, 20));
        buttonPanel.add(submitButton);
        JButton cancelButton = new JButton("取消");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //do_cancelButton_actionPerformed(e);
                    setVisible(false);
                }
            });
        cancelButton.setFont(new Font("黑体", Font.PLAIN, 20));
        buttonPanel.add(cancelButton);
        pack();// 自动调整窗体大小
        setLocation(SwingUtil.centreContainer(getSize()));// 让窗体居中显示
    }
}
