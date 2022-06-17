package Forms;

import DButil.DBConnection;
import java.sql.*;
import DButil.DBManager;
import Main.Nowtime;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class MainForm extends JFrame{
    public JPanel contentPane;
    public static JLabel labelyaer;
    public static JLabel labelday;
    private JPanel panel3;
    public static JTable jTable;
    private static PreparedStatement pstmt;
    private static Connection conn;
    private ResultSet rs;
    public static JScrollPane scrollPane;
    private static DefaultTableModel tableModel;
    private static Object[][] O1;
    private static final long serialVersionUID = 1L;

    public MainForm() {
        setTitle("主页面");
        setResizable(false);//禁止改变窗口大小
        getContentPane().setLayout(null);
        setBounds(220, 50, 1500, 900);

        JPanel panel1 = new JPanel();
        panel1.setBounds(0, 0, 300, 170);
        JLabel jLabel1=new JLabel();
        jLabel1.setBounds(30,20,300,40);
        jLabel1.setFont(new Font("黑体", Font.BOLD, 26));
        jLabel1.setText("用户名:"+LoginForm.usernameTextField.getText());
        panel1.add(jLabel1);
        JLabel jLabel2=new JLabel();
        jLabel2.setBounds(30,65,300,40);
        jLabel2.setFont(new Font("黑体", Font.BOLD, 26));
        jLabel2.setText("员工编号："+ DBManager.selectEid(LoginForm.usernameTextField.getText()));
        panel1.add(jLabel2);
        JLabel jLabel3=new JLabel();
        jLabel3.setBounds(30,110,300,40);
        jLabel3.setFont(new Font("黑体", Font.BOLD, 26));
        if(DBManager.selectGroup(LoginForm.usernameTextField.getText())==1){//字段1为管理员权限
            jLabel3.setText("权限组：管理员");
        }else if(DBManager.selectGroup(LoginForm.usernameTextField.getText())==0){//字段0为普通用户权限
            jLabel3.setText("权限组：普通用户");
        }
        panel1.add(jLabel3);
        panel1.setLayout(null);
        panel1.setVisible(true);
        getContentPane().add(panel1);

        JPanel panel2 = new JPanel();
        panel2.setBounds(300, 0, 900, 170);
        //panel2.setBackground(Color.PINK);
        panel2.setVisible(true);
        panel2.setLayout(null);
        getContentPane().add(panel2);
        JLabel labeltitle=new JLabel();
        panel2.add(labeltitle);
        labeltitle.setText("员工信息管理系统");
        labeltitle.setFont(new Font("华文琥珀",Font.BOLD,80));
        labeltitle.setBounds(50,30,900,100);

        JPanel panel3 = new JPanel();//时间日期panel
        panel3.setBounds(1200, 0, 300, 170);
        panel3.setLayout(null);
        panel3.setVisible(true);
        getContentPane().add(panel3);

        labelyaer=new JLabel();//年月日
        panel3.add(labelyaer);
        labelyaer.setForeground(Color.DARK_GRAY);
        labelyaer.setFont(new Font("幼圆", Font.PLAIN, 32));
        labelyaer.setBounds(30, 40, 300, 50);
        labelday=new JLabel();//时分秒
        panel3.add(labelday);
        labelday.setForeground(Color.DARK_GRAY);
        labelday.setFont(new Font("幼圆", Font.PLAIN, 32));
        labelday.setBounds(80, 90, 300, 50);
        Thread thread=new Thread(new Nowtime());
        thread.start();//启动线程

        JPanel panel4 = new JPanel();
        panel4.setBounds(0, 170, 300, 730);
        panel4.setLayout(null);
        panel4.setVisible(true);
        getContentPane().add(panel4);

        JLabel labelid=new JLabel("编号：");
        labelid.setBounds(20,5,60,20);
        labelid.setFont(new Font("黑体",Font.PLAIN,20));
        panel4.add(labelid);
        JTextField textid=new JTextField();
        textid.setBounds(80,0,180,30);
        panel4.add(textid);

        JLabel labelname=new JLabel("姓名：");
        labelname.setBounds(20,45,60,20);
        labelname.setFont(new Font("黑体",Font.PLAIN,20));
        panel4.add(labelname);
        JTextField textname=new JTextField();
        textname.setBounds(80,40,180,30);
        panel4.add(textname);
        
        JLabel labelgender=new JLabel("性别：");
        labelgender.setBounds(20,85,60,20);
        labelgender.setFont(new Font("黑体",Font.PLAIN,20));
        panel4.add(labelgender);
        JTextField textgender=new JTextField();
        textgender.setBounds(80,80,180,30);
        panel4.add(textgender);

        JLabel labelposition=new JLabel("职位：");
        labelposition.setBounds(20,125,60,20);
        labelposition.setFont(new Font("黑体",Font.PLAIN,20));
        panel4.add(labelposition);
        JTextField textposition=new JTextField();
        textposition.setBounds(80,120,180,30);
        panel4.add(textposition);

        JLabel labelsalary=new JLabel("工资：");
        labelsalary.setBounds(20,165,60,20);
        labelsalary.setFont(new Font("黑体",Font.PLAIN,20));
        panel4.add(labelsalary);
        JComboBox jComboBox1=new JComboBox();
        jComboBox1.setBounds(80,162,65,25);
        jComboBox1.addItem("");
        jComboBox1.addItem("大于");
        jComboBox1.addItem("等于");
        jComboBox1.addItem("小于");
        panel4.add(jComboBox1);
        JTextField textsalary1=new JTextField();
        textsalary1.setBounds(150,160,110,30);
        panel4.add(textsalary1);
        JComboBox jComboBox2=new JComboBox();
        jComboBox2.addItem("");
        jComboBox2.addItem("小于");
        jComboBox2.setBounds(80,202,65,25);
        panel4.add(jComboBox2);
        JTextField textsalary2=new JTextField();
        textsalary2.setBounds(150,200,110,30);
        panel4.add(textsalary2);

        JLabel labelphone=new JLabel("电话：");
        labelphone.setBounds(20,245,60,20);
        labelphone.setFont(new Font("黑体",Font.PLAIN,20));
        panel4.add(labelphone);
        JTextField textphone=new JTextField();
        textphone.setBounds(80,240,180,30);
        panel4.add(textphone);

        JButton selectButton = new JButton(("查询"));
        selectButton.setBounds(75, 290, 150, 45);
        selectButton.setFocusPainted(false);
        selectButton.setBorderPainted(false);
        //selectButton.setBackground(Color.pink);
        panel4.add(selectButton);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs;
                String[] columns={"id","name","gender","position","salary","telephone"};
                String sql="select * from tb_staff";
                int flag=0;//标记是否第一个字段，第一个字段特殊处理

                if(!textid.getText().equals("")){
                    if(flag==0){
                        sql=sql+" where id='"+textid.getText()+"'";
                        flag=1;
                    }else{
                        sql=sql+" and id='"+textid.getText()+"'";
                    }
                }
                if(!textname.getText().equals("")){
                    if(flag==0){
                        sql=sql+" where name='"+textname.getText()+"'";
                        flag=1;
                    }else{
                        sql=sql+" and name='"+textname.getText()+"'";
                    }
                }
                if(!textgender.getText().equals("")){
                    if(flag==0){
                        sql=sql+" where gender='"+textgender.getText()+"'";
                        flag=1;
                    }else{
                        sql=sql+" and gender='"+textgender.getText()+"'";
                    }
                }
                if(!textposition.getText().equals("")){
                    if(flag==0){
                        sql=sql+" where position ='"+textposition.getText()+"'";
                        flag=1;
                    }else{
                        sql=sql+" and position ='"+textposition.getText()+"'";
                    }
                }
                if(!textsalary1.getText().equals("") && isNumber(textsalary1.getText())&&jComboBox1.getSelectedIndex()!=0){//第一个文本框不为空且为数字且选中条件
                    if(jComboBox2.getSelectedIndex()!=0 && !textsalary2.getText().equals("")&& isNumber(textsalary2.getText())){//第二个下拉框选中条件且第二个文本框不为空且为数字的情况
                        if(jComboBox1.getSelectedItem().equals("大于") && jComboBox2.getSelectedItem().equals("小于") ){//大于小于情况
                            if(flag==0){
                                sql=sql+" where salary between "+textsalary1.getText()+" and "+textsalary2.getText();
                                flag=1;
                            }else{
                                sql=sql+" and salary between "+textsalary1.getText()+" and "+textsalary2.getText();
                            }
                        }else{//其他选中情况不修改列表框原样
                            return;
                        }
                    }else if(jComboBox2.getSelectedIndex()!=0 && textsalary2.getText().equals("")){//文本框为空
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "请输入待查询工资范围", "警告", JOptionPane.WARNING_MESSAGE);
                        return;//防止执行接下来代码造成刷新列表框
                    }else if(jComboBox2.getSelectedIndex()!=0 && !isNumber(textsalary2.getText())){//输入的不是数字
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "请输入数字", "警告", JOptionPane.WARNING_MESSAGE);
                        return;//防止执行接下来代码造成刷新列表框
                    }

                    if(jComboBox2.getSelectedIndex()==0){//第二个下拉框没选情况
                        if(jComboBox1.getSelectedItem().equals("大于")){//大于
                            if(flag==0){
                                sql=sql+" where salary>"+textsalary1.getText();
                                flag=1;
                            }else{
                                sql=sql+" and salary>"+textsalary1.getText();
                            }
                        }
                        if(jComboBox1.getSelectedItem().equals("等于")){//等于
                            if(flag==0){
                                sql=sql+" where salary="+textsalary1.getText();
                                flag=1;
                            }else{
                                System.out.println(1);
                                sql=sql+" and salary="+textsalary1.getText();
                            }
                        }
                        if(jComboBox1.getSelectedItem().equals("小于")){//小于
                            if(flag==0){
                                sql=sql+" where salary<"+textsalary1.getText();
                                flag=1;
                            }else{
                                sql=sql+" and salary<"+textsalary1.getText();
                            }
                        }
                    }
                }else if(jComboBox1.getSelectedIndex()!=0 && textsalary1.getText().equals("")){//第一个下拉框已选择且文本框为空
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "请输入待查询工资数", "警告", JOptionPane.WARNING_MESSAGE);
                    return;//防止执行接下来代码造成刷新列表框
                }else if(!isNumber(textsalary1.getText()) && jComboBox1.getSelectedIndex()!=0){//第一个下拉框已选择且输入的不是数字
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "请输入数字", "警告", JOptionPane.WARNING_MESSAGE);
                    return;//防止执行接下来代码造成刷新列表框
                }else if(!textsalary1.getText().equals("") && jComboBox1.getSelectedIndex()==0){//第一个文本框已输入数据但下拉框条件没选中
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "请选择查询工资的条件", "警告", JOptionPane.WARNING_MESSAGE);
                    return;//防止执行接下来代码造成刷新列表框
                }

                if(!textphone.getText().equals("")){
                    if(flag==0){
                        sql=sql+" where telephone='"+textphone.getText()+"'";
                        flag=1;
                    }else{
                        sql=sql+" and telephone='"+textphone.getText()+"'";
                    }
                }

                try {
                    sql=sql+";";
                    conn=DBConnection.getDBConnection();
                    pstmt=conn.prepareStatement(sql);
                    rs=pstmt.executeQuery();
                    java.sql.ResultSetMetaData property =rs.getMetaData();//获取数据库表对应列明
                    int count=0;
                    while(rs.next()){
                        count++;
                    }
                    O1=new Object[count][property.getColumnCount()];
                    rs=pstmt.executeQuery();
                    int sum=0;
                    while(rs.next()){
                        O1[sum][0]=rs.getString(1);
                        O1[sum][1]=rs.getString(2);
                        O1[sum][2]=rs.getString(3);
                        O1[sum][3]=rs.getString(4);
                        O1[sum][4]=rs.getDouble(5);
                        O1[sum][5]=rs.getString(6);
                        sum++;
                    }
                    tableModel = new DefaultTableModel(O1,columns);//创建表格模型
                    jTable=new JTable(tableModel);
                    scrollPane.setViewportView(jTable);//把jTable作为视口
                    JTableHeader jTableHeader= jTable.getTableHeader();//获取表头
                    jTableHeader.setPreferredSize(new Dimension(jTableHeader.getWidth(), 35));//设置完美大小
                    jTableHeader.setFont(new Font("楷体", Font.BOLD,24 ));
                    jTable.setRowHeight(18);
                    jTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
                    for (int i = 0; i < 6; i++) {
                        TableColumn column3 = jTable.getColumnModel().getColumn(i);
                        if (i == 0) {
                            column3.setPreferredWidth(50);
                        }
                    }
                    DefaultTableCellRenderer dc=new DefaultTableCellRenderer();//定义单元格渲染器
                    dc.setHorizontalAlignment(SwingConstants.CENTER);//设置居中对齐
                    jTable.setDefaultRenderer(Object.class,dc);
                    jTable.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        JButton addButton = new JButton("增加");
        addButton.setBounds(75, 355, 150, 45);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        //addButton.setBackground(Color.PINK);
        panel4.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jLabel3.getText().equals("权限组：管理员")){//判断当前权限为管理员还是普通用户
                    new AddForm();//管理员可进行添加操作
                }else{//普通用户进行提示无此权限
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "您是普通用户，无此权限！", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton deleteButton = new JButton(("删除"));
        deleteButton.setBounds(75, 420, 150, 45);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        //deleteButton.setBackground(Color.PINK);
        panel4.add(deleteButton);
        deleteButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jLabel3.getText().equals("权限组：管理员")){//判断当前权限为管理员还是普通用户，管理员可进行添加操作
                    int rowcount=jTable.getSelectedRow();//返回列表框选择的记录
                    if(rowcount==-1){//-1为未选中
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "请选中删除项", "警告", JOptionPane.WARNING_MESSAGE);
                    }
                    if(rowcount>=0){//有选中的情况
                        tableModel.removeRow(rowcount);//删除掉列表框选择那一条记录
                        try {
                            pstmt=conn.prepareStatement("delete from tb_user where Employee_id=?");//删除tb_user对应的记录,因有外键约束，必须先删除
                            pstmt.setString(1,String.valueOf(O1[rowcount][0]));//O1数组里存储每条记录每个字段的值，[第几条记录][字段]
                            pstmt.execute();
                            pstmt=conn.prepareStatement("delete from tb_staff where id=?");//删除tb_staff对应的记录
                            pstmt.setString(1,String.valueOf(O1[rowcount][0]));//O1数组里存储每条记录每个字段的值，[第几条记录][字段]
                            pstmt.execute();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                }else{//普通用户进行提示无此权限
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "您是普通用户，无此权限！", "警告", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        JButton userButton = new JButton("用户管理");
        userButton.setBounds(75, 485, 150, 45);
        userButton.setFocusPainted(false);
        userButton.setBorderPainted(false);
        //userButton.setBackground(Color.PINK);
        panel4.add(userButton);
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jLabel3.getText().equals("权限组：管理员")){//判断当前权限为管理员还是普通用户
                    new UserForm();;//管理员可进行用户管理操作
                }else{//普通用户进行提示无此权限
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "您是普通用户，无此权限！", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton refreshButton = new JButton(("刷新"));
        refreshButton.setBounds(75, 550, 150, 45);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        //refreshButton.setBackground(Color.pink);
        panel4.add(refreshButton);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });

        JButton exitButton = new JButton(("退出"));
        exitButton.setBounds(75, 615, 150, 45);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        //exitButton.setBackground(Color.pink);
        panel4.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        conn = DBConnection.getDBConnection();
        scrollPane = null;
        scrollPane=new JScrollPane();
        refresh();//获取列表框的值
        JPanel panel5 = new JPanel();
        panel5.setBounds(300, 170, 1200, 730);
        panel5.setLayout(null);
        scrollPane.setBounds(0,0,1200,690);
        panel5.add(scrollPane);
        panel5.setVisible(true);
        getContentPane().add(panel5);
        setVisible(true);
    }

    public static boolean isNumber(String string) {
        if (string == null)
            return false;
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//正则表达式
        return pattern.matcher(string).matches();
    }

    public static void refresh(){
        ResultSet rs;
        String[] columns={"id","name","gender","position","salary","telephone"};//列表框标题属性
        try {
            conn=DBConnection.getDBConnection();//获取数据库驱动
            pstmt=conn.prepareStatement("select * from tb_staff;");
            rs=pstmt.executeQuery();//查询sql语句
            java.sql.ResultSetMetaData property =rs.getMetaData();//获取数据库表对应的列名。
            int count=0;
            while(rs.next()){//计算多少条记录
                count++;
            }
            O1=new Object[count][property.getColumnCount()];//创建数组，对应每条记录的每个字段
            rs=pstmt.executeQuery();
            int sum=0;
            while(rs.next()){//存数据到数组
                O1[sum][0]=rs.getString(1);
                O1[sum][1]=rs.getString(2);
                O1[sum][2]=rs.getString(3);
                O1[sum][3]=rs.getString(4);
                O1[sum][4]=rs.getDouble(5);
                O1[sum][5]=rs.getString(6);
                sum++;
            }
            tableModel = new DefaultTableModel(O1,columns);//创建表格模型
            jTable=new JTable(tableModel){//创建表格并使表格模型与之关联
                public boolean isCellEditable(int rowIndex,int ColIndex){//单元格不可以编辑
                    if(ColIndex==0){//第一列员工编号不可编辑
                        return false;
                    }
                    return true;
                }
            };
            scrollPane.setViewportView(jTable);//把jTable作为视口
            JTableHeader jTableHeader= jTable.getTableHeader();//获得jTable表头
            jTableHeader.setPreferredSize(new Dimension(jTableHeader.getWidth(), 35));//设置完美大小
            jTableHeader.setFont(new Font("楷体", Font.BOLD,24 ));
            jTable.setRowHeight(18);
            DefaultTableCellRenderer dc=new DefaultTableCellRenderer();//定义单元格渲染器
            dc.setHorizontalAlignment(SwingConstants.CENTER);//设置居中对齐
            jTable.setDefaultRenderer(Object.class,dc);
            jTable.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

            jTable.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }
                @Override
                public void keyPressed(KeyEvent e) {

                }
                @Override
                public void keyReleased(KeyEvent e) {
                    if (DBManager.selectGroup(LoginForm.usernameTextField.getText())==1){//管理员权限
                        int column;
                        int row;
                        column = jTable.getSelectedColumn();//获取选中列
                        row=jTable.getSelectedRow();//获取选中行
                        try {
                            Object O3=jTable.getValueAt(row,column);//获取当前选中单元格
                            pstmt=conn.prepareStatement("update tb_staff set "+columns[column]+"=? where id=?");
                            if(column>=0 && column<=3 || column==5){
                                pstmt.setString(1,String.valueOf(O3));
                                pstmt.setString(2,String.valueOf(O1[row][0]));
                                pstmt.execute();
                            }else if(column==4){
                                pstmt.setDouble(1,Double.valueOf(O3.toString()));
                                pstmt.setString(2,String.valueOf(O1[row][0]));
                                pstmt.execute();
                            }
                        } catch (SQLException throwables) {
                            JOptionPane jOptionPane=new JOptionPane();
                            jOptionPane.showMessageDialog(null, "插入错误", "警告", JOptionPane.WARNING_MESSAGE);
                            refresh();
                            throwables.printStackTrace();
                        }
                    }else{
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "您是普通用户，无此权限！", "警告", JOptionPane.WARNING_MESSAGE);
                        refresh();
                    }
                }
            });
    }

}
