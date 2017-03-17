package org.com.huang.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.com.huang.entity.HuoDong;
import org.com.huang.util.DB_Util;

public class HuoDongService {
    public static void save(HuoDong huodong){
    	Connection conn=new DB_Util().getConnection();
    	Statement stm=null;
    	ResultSet rs=null;
        int id=0;
    	String sql="select max(id) as id from huodong";

    	try {
    		stm=conn.createStatement();
			rs = stm.executeQuery(sql);// 查询出最大ID
			if (rs.next()) {// 如果有ID，原有ID+1
				id = rs.getInt("id") + 1;
			} else {// 否则 ID 初始为0
				id = 0;
			}
    		stm=conn.createStatement();
			stm.executeUpdate("insert into huodong value("+id+",'"+huodong.getOpenid()+"','2017-3-14',"+huodong.getPrice()+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		} 
    	new DB_Util().release(rs, stm, conn);
    }
   public static int queryByPrice(int price){
	   Connection conn=new DB_Util().getConnection();
	   Statement stm=null;
	   ResultSet rs=null;
	   int count=0;
	   try {
		stm=conn.createStatement();
		rs=stm.executeQuery("select count(*) as price from huodong where price="+price+"");

        if(rs.next()){
        	count=rs.getInt("price");
        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   new DB_Util().release(rs, stm, conn);  
	   System.out.println(count);
	   return count;
   }
   public static void main(String[] args){
	   queryByPrice(27);
   }
}
