package DButil;
import java.sql.*;
public class DBConnection {
    public static Connection getDBConnection() {
        // 1. 注册驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 获取数据库的连接
        try {
            Connection conn  = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/eims?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "lr18020737930");
            return conn;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
