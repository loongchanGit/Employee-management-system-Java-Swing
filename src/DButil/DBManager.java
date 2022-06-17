package DButil;

import javax.swing.*;
import java.sql.*;
import java.util.*;



public class DBManager {
    private static PreparedStatement pstmt1;
    private static PreparedStatement pstmt2;
    private static Connection conn;
    private static ResultSet rs;
    private  static  int rss;
    public static ArrayList<Main.User> result;

    public static int addUser(String username,String password,String employeeid){//增加用户
        try{
            int rss=0;//插入结果条数
            conn=DBConnection.getDBConnection();//加载数据库驱动
            if(selectUser(username)==null){//数据库不存在该用户
                conn=DBConnection.getDBConnection();//加载数据库驱动
                String sql="insert into tb_user values(?,?,?,?)";
                pstmt1=conn.prepareStatement(sql);//动态执行sql语句
                pstmt1.setString(1,username);//用户名
                pstmt1.setString(2,password);//密码
                pstmt1.setInt(3,0);//新用户注册权限组初始为0
                pstmt1.setString(4,employeeid);//员工编号
                rss=pstmt1.executeUpdate();//执行修改sql语句
                return rss;//插入成功，返回插入记录数
            }else{
                JOptionPane jOptionPane=new JOptionPane();//消息框
                jOptionPane.showMessageDialog(null, "存在相同用户名", "错误", JOptionPane.ERROR_MESSAGE);
                return rss;//已存在username
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(pstmt1!=null){
                try{
                    pstmt1.close();//关闭
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();//关闭
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }
    public static List<Main.User> selectUser(String username){//查找用户信息
        try{
            conn=DBConnection.getDBConnection();//注册数据库驱动
            String sql="select username,password from tb_user where username=?";
            pstmt2=conn.prepareStatement(sql);//动态执行sql语句
            pstmt2.setString(1,String.valueOf(username));
            rs=pstmt2.executeQuery();//执行sql查询语句
            if(rs!=null){
                if(!rs.next()){//结果集不存在
                    return null;
                }else{
                    List result=new ArrayList<Main.User>();//创建user泛型集合
                    do{
                        Main.User user=new Main.User();//new一个user对象
                        user.setUsername(rs.getString(1));
                        user.setPassword(rs.getString(2));
                        result.add(user);//对象加入集合
                    }while(rs.next());
                    return result;//返回集合
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {//关闭资源
            if(pstmt2!=null){
                try{
                    pstmt2.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static int existEmployee(String id){//查找员工编号，1存在，0不存在
        try{
            rs=null;
            conn=DBConnection.getDBConnection();//注册驱动程序
            String sql="select id from tb_staff where id=?";
            pstmt2=conn.prepareStatement(sql);//动态执行sql语句
            pstmt2.setString(1,id);
            rs=pstmt2.executeQuery();//执行sql查询语句
            if(rs!=null){
                while(rs.next()){//存在结果集返回1
                    return 1;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {//关闭资源
            if(pstmt2!=null){
                try{
                    pstmt2.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return 0;//不存在返回0
    }
    public static int judgestate(String id){//查找员工状态并返回状态，0表示未绑定，1表示已绑定
        try{
            rs=null;
            conn=DBConnection.getDBConnection();//注册驱动程序
            String sql="select state from tb_staff where id=?";
            pstmt2=conn.prepareStatement(sql);//动态执行sql语句
            pstmt2.setString(1,id);
            rs=pstmt2.executeQuery();//执行sql查询语句
            if(rs!=null){
                while(rs.next()){//存在结果集返回第一个字段
                    return rs.getInt(1);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {//关闭资源
            if(pstmt2!=null){
                try{
                    pstmt2.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
    public static int alterstate(String id,int state){//修改员工状态
        try{
            rss=0;
            conn=DBConnection.getDBConnection();//注册驱动程序
            String sql="update tb_staff set state = ? where id=?";
            pstmt2=conn.prepareStatement(sql);//动态执行sql语句
            pstmt2.setInt(1,state);
            pstmt2.setString(2,id);
            rss=pstmt2.executeUpdate();//执行sql修改语句
            return rss;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {//关闭资源
            if(pstmt2!=null){
                try{
                    pstmt2.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
    public static String selectEid(String username){//查找用户员工编号
        try{
            rs=null;
            conn=DBConnection.getDBConnection();//注册驱动程序
            String sql="select Employee_id from tb_user where username=?";
            pstmt2=conn.prepareStatement(sql);//动态执行sql语句
            pstmt2.setString(1,username);
            rs=pstmt2.executeQuery();//执行sql查询语句
            if(rs!=null){
                while(rs.next()){//存在结果集
                    return rs.getString(1);//取结果集第一个字段
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {//关闭资源
            if(pstmt2!=null){
                try{
                    pstmt2.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static int selectGroup(String username){//查找用户权限组
        try{
            rs=null;
            conn=DBConnection.getDBConnection();//注册驱动程序
            String sql="select Permission_group from tb_user where username=?";
            pstmt2=conn.prepareStatement(sql);////动态执行sql语句
            pstmt2.setString(1,username);
            rs=pstmt2.executeQuery();//执行sql查询语句
            if(rs!=null){
                while(rs.next()){//存在结果集
                    return rs.getInt(1);//返回结果集第一个字段
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {//关闭资源
            if(pstmt2!=null){
                try{
                    pstmt2.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
}
