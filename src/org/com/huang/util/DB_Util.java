package org.com.huang.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DB_Util {
	private static Connection conn = null;
	private static   Statement stmt = null;
	private static   ResultSet  rs = null;
	
	public   Connection getConnection() {
		init();
		return conn;
	}
    public  void init(){  
        try {  
        	  //1 将数据库驱动类注册到DriverManager
        	   Class.forName("com.mysql.jdbc.Driver");
        	   //2 作数据库连接（通过DriverManager得到当前应用程序和数据库的对话）
        	   conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/wx", "root", "system");
               System.out.println("连接数据了成功");
        } catch (Exception e) {  
            // TODO: handle exception  
            e.printStackTrace();  
        }  
    } 
 	public  void release(ResultSet rs, Statement ps, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (ps != null) {
			try {
				ps.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (con != null) {
			try {
				con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}    
}
