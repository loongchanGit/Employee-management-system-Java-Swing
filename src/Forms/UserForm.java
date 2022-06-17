package Forms;

import DButil.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserForm extends JFrame {
    public static JTable jTable;
    private static PreparedStatement pstmt;
    private static Connection conn;
    private ResultSet rs;
    public static JScrollPane scrollPane;
    private static DefaultTableModel tableModel;
    private static Object[][] O1;
    private static String sql;
    public UserForm(){
        setTitle("用户管理");
        setResizable(false);//禁止改变窗口大小
        setVisible(true);
        setLayout(null);
        setBounds(600,250,800,500);

        JPanel panel1=new JPanel();//左边pannel
        panel1.setBounds(0,0,250,500);
        panel1.setLayout(null);
        this.add(panel1);

        JLabel jLabel1=new JLabel();//标题标签
        jLabel1.setText("用户管理");
        jLabel1.setHorizontalAlignment(JLabel.CENTER);//水平居中
        jLabel1.setVerticalAlignment(JLabel.CENTER);//垂直居中
        jLabel1.setBounds(0,0,250,150);
        jLabel1.setFont(new Font("黑体", Font.BOLD, 45));
        panel1.add(jLabel1);

        JTextField searchText=new JTextField();//搜索框
        searchText.setBounds(30,150,150,30);
        panel1.add(searchText);
        JButton searchButton=new JButton(new ImageIcon("src/images/search.png"));//搜索按钮
        searchButton.setBounds(190,150,30,30);
        panel1.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sql="select * from tb_user where username='"+searchText.getText()+"'";
                gain();
            }
        });

        JButton deleteButton=new JButton("删除");
        deleteButton.setBounds(60,220,100,35);
        panel1.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowcount=jTable.getSelectedRow();//返回列表框选择的记录
                if(rowcount==-1){//-1为未选中
                    JOptionPane jOptionPane=new JOptionPane();
                    jOptionPane.showMessageDialog(null, "请选中删除项", "警告", JOptionPane.WARNING_MESSAGE);
                }
                if(rowcount>=0){//有选中的情况
                    if(jTable.getValueAt(jTable.getSelectedRow(),0).equals("admin")){
                        JOptionPane jOptionPane=new JOptionPane();
                        jOptionPane.showMessageDialog(null, "admin账号不可删除", "警告", JOptionPane.WARNING_MESSAGE);
                    }else{
                        tableModel.removeRow(rowcount);//删除掉列表框选择那一条记录
                        try {
                            pstmt=conn.prepareStatement("delete from tb_user where username=?");//删除数据库对应的记录
                            pstmt.setString(1,String.valueOf(O1[rowcount][0]));//O1数组里存储每条记录每个字段的值，[第几条记录][字段]
                            pstmt.execute();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            }
        });

        JButton RefreshButton=new JButton("刷新");
        RefreshButton.setBounds(60,290,100,35);
        panel1.add(RefreshButton);
        RefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sql="select * from tb_user";
                gain();
            }
        });

        JButton modifyButton=new JButton("退出");
        modifyButton.setBounds(60,360,100,35);
        panel1.add(modifyButton);
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        JPanel panel2=new JPanel();//右边panel
        panel2.setBounds(250,0,550,500);
        panel2.setLayout(null);
        this.add(panel2);

        try{
            scrollPane=new JScrollPane(jTable);//创建滚动条，把jtable加入滚动条
            sql="select * from tb_user";
            gain();
            scrollPane.setBounds(0,0,550,500);
            scrollPane.setVisible(true);
            panel2.add(scrollPane);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void gain(){//执行特定查询sql语句并更新列表框状态
        ResultSet rs=null;
        conn = DBConnection.getDBConnection();
        try{
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            java.sql.ResultSetMetaData property =rs.getMetaData();//获取数据库对应列名
            int count=0;
            while(rs.next()){
                count++;
            }
            rs=pstmt.executeQuery();
            O1=new Object[count][property.getColumnCount()];//创建记录对应数组
            int sum=0;
            while(rs.next()){//存储每条记录
                O1[sum][0]=rs.getString(1);
                O1[sum][1]=rs.getString(2);
                O1[sum][2]=rs.getString(3);
                O1[sum][3]=rs.getString(4);
                sum++;
            }
            String[] columns={"用户名","密码","权限组","员工编号"};
            tableModel = new DefaultTableModel(O1,columns);//创建表格模型
            jTable=new JTable(tableModel){
                public boolean isCellEditable(int rowIndex,int ColIndex){//单元格不可以编辑
                    if(ColIndex==0){//第一列账号不可编辑
                        return false;
                    }
                    return true;
                }
            };
            scrollPane.setViewportView(jTable);//jtable作为视口
            JTableHeader jTableHeader= jTable.getTableHeader();//获取jtable表头
            jTableHeader.setPreferredSize(new Dimension(jTableHeader.getWidth(), 35));//设置完美大小
            jTableHeader.setFont(new Font("楷体", Font.PLAIN, 18));
            jTable.setRowHeight(18);
            DefaultTableCellRenderer dc=new DefaultTableCellRenderer();//定义单元格渲染器
            dc.setHorizontalAlignment(SwingConstants.CENTER);//定义单元格居中对齐
            jTable.setDefaultRenderer(Object.class,dc);
            jTable.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        }catch (Exception e){
            e.printStackTrace();
        }
        jTable.addKeyListener(new KeyListener() {//jTable键盘监听事件

            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {//键盘弹起事件
                if(jTable.getValueAt(jTable.getSelectedRow(),0).equals("admin")) {//超级管理员账号不可修改
                    JOptionPane jOptionPane = new JOptionPane();
                    jOptionPane.showMessageDialog(null, "admin账号不可修改", "警告", JOptionPane.WARNING_MESSAGE);
                    gain();//重新获取列表框的值
                }else{
                    int column;
                    int row;
                    String[] listing={"用户名","密码","权限组","员工编号"};//列表名
                    String[] columns={"username","password","Permission_group","Employee_id"};//数据库列名
                    column = jTable.getSelectedColumn();//获取选中列数
                    row=jTable.getSelectedRow();//获取选中行数
                    try {
                        Object O3=jTable.getValueAt(row,column);//取选中单元格值
                        pstmt=conn.prepareStatement("update tb_user set "+columns[column]+"=? where username=?");
                        if(column==0 || column==1 || column==3){
                            pstmt.setString(1,String.valueOf(O3));
                            pstmt.setString(2,String.valueOf(O1[row][0]));
                            pstmt.execute();
                        }else if(column==2){
                            pstmt.setInt(1,Integer.parseInt(O3.toString()));
                            pstmt.setString(2,String.valueOf(O1[row][0]));
                            pstmt.execute();
                        }
                        gain();
                    } catch (SQLException throwables) {
                        System.out.println("添加错误");
                        throwables.printStackTrace();
                    }
                }

            }
        });
    }
}
