package Forms;


import DButil.DBManager;
import Main.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//登陆窗口
public class LoginForm extends JFrame {
    public static RegisterForm registerform;//注册窗口
    private static final long serialVersionUID = -4655235896173916415L;
    private JLabel logoLabel;//logo标签
    private JPanel contentPane;//面板容器
    public static JTextField usernameTextField;//用户文本框
    private JPasswordField passwordField;//密码文本框
    private JTextField validateTextField;//验证码文本框
    private String randomText;//随机数字符串

    public LoginForm() {
        setTitle("系统登录");
        setResizable(false);//禁止改变窗口大小
        Image icon = Toolkit.getDefaultToolkit().getImage("src/images/logo0.png");  // 图片的具体位置
        this.setIconImage(icon);   //设置窗口的logo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置用户在此窗体上发起 "close" 时执行System exit 方法
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        JPanel logoPanel=new JPanel();
        contentPane.add(logoPanel);
        logoLabel=new JLabel(new ImageIcon("src/images/logo1.png"));
        logoPanel.add(logoLabel);

        JPanel usernamePanel = new JPanel();
        contentPane.add(usernamePanel);
        JLabel usernameLable = new JLabel("账号：");
        usernameLable.setFont(new Font("黑体", Font.BOLD, 28));
        usernamePanel.add(usernameLable);
        usernameTextField = new JTextField();
        usernameTextField.setFont(new Font("黑体", Font.BOLD, 24));
        usernamePanel.add(usernameTextField);
        usernameTextField.setColumns(15);

        JPanel passwordPanel = new JPanel();
        contentPane.add(passwordPanel);
        JLabel passwordLabel = new JLabel("密码：");
        passwordLabel.setFont(new Font("黑体", Font.BOLD, 28));
        passwordPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setColumns(15);
        passwordField.setFont(new Font("黑体", Font.BOLD, 24));
        passwordPanel.add(passwordField);

        JPanel validatePanel = new JPanel();
        contentPane.add(validatePanel);
        JLabel validateLabel = new JLabel("验证码：");
        validateLabel.setFont(new Font("黑体", Font.BOLD, 26));
        validatePanel.add(validateLabel);
        validateTextField = new JTextField();
        validateTextField.setFont(new Font("黑体", Font.BOLD, 24));
        validatePanel.add(validateTextField);
        validateTextField.setColumns(8);
        randomText = String.valueOf((int)(Math.random()*(9999-1000+1)+1000));
        JLabel label=new JLabel(randomText);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        validatePanel.add(label);
        label.addMouseListener(new MouseAdapter(){//随机数标签点击事件
            @Override
            public void mouseClicked(MouseEvent e){
                randomText = String.valueOf((int)(Math.random()*(9999-1000+1)+1000));//生成1000-9999的随机数
                label.setText(randomText);
            }
        });

        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel);
        JButton submitButton = new JButton("登录");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!usernameTextField.getText().equals("") && !String.valueOf(passwordField.getPassword()).equals("") && randomText.equals(validateTextField.getText())){//文本框和密码框不为空，验证码输入正确
                    List<Main.User> result= DBManager.selectUser(usernameTextField.getText());//查询username信息
                    if(result==null){//查询没结果
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "用户名不存在", "警告", JOptionPane.WARNING_MESSAGE);
                    }else{
                        if(usernameTextField.getText().equals(result.get(0).getUsername()) && String.valueOf(passwordField.getPassword()).equals(result.get(0).getPassword())){//账号密码和查询到的账号密码相同
                            JOptionPane jOptionPane=new JOptionPane();
                            jOptionPane.showMessageDialog(null, "登录成功！！！", "登录成功", JOptionPane.INFORMATION_MESSAGE);
                            MainForm mainForm=new MainForm();//创建主窗体对象
                        }else if(!String.valueOf(passwordField.getPassword()).equals(result.get(0).getPassword())){//密码不同
                            JOptionPane jOptionPane=new JOptionPane();
                            jOptionPane.showMessageDialog(null, "密码输入错误", "警告", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }else if(usernameTextField.getText().equals("")){//用户名为空
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "用户名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                }else if(String.valueOf(passwordField.getPassword()).equals("")){//密码为空
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                }else if(!validateTextField.getText().equals(randomText)){//验证码输入不一致
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "验证码输入错误", "错误", JOptionPane.ERROR_MESSAGE);
                }else if(validateTextField.getText().equals("")){//验证码为空
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "验证码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        submitButton.setFont(new Font("黑体", Font.BOLD, 25));
        buttonPanel.add(submitButton);

        JButton cancelButton = new JButton("注册");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerform=new RegisterForm();//new注册窗口
                registerform.setVisible(true);
            }
        });
        cancelButton.setFont(new Font("黑体", Font.BOLD, 25));
        buttonPanel.add(cancelButton);
        pack();// 自动调整窗体大小
         setLocation(Forms.SwingUtil.centreContainer(getSize()));// 让窗体居中显示
    }
}
