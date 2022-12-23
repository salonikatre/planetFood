/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlanetFood.Dao;

import PlanetFood.Dbutil.DBConnection;
import PlanetFood.Pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class UserDao {
    public static String validateUser(User u)throws SQLException{
    Connection conn= DBConnection.getConnection();
    String qry="Select username from Users where userid=? and password=? and usertype=?";
    PreparedStatement ps= conn.prepareStatement(qry);
    ps.setString(1, u.getUserId());
    ps.setString(2, u.getPassword());
    ps.setString(3, u.getUserType());
    ResultSet rs= ps.executeQuery();
    String username= null;
    if(rs.next())
    {
    username = rs.getString(1);
    }
    return username;
    }
    
    public static boolean registerUser(User u)throws SQLException{
    Connection conn= DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("insert into Users values(?,?,?,?,?)");
    ps.setString(1,u.getUserId());
    ps.setString(2,u.getUserName());
    ps.setString(3,u.getPassword());
    ps.setString(4,u.getEmpId());
    ps.setString(5,u.getUserType());
    int result=ps.executeUpdate();
    if(result>0)
        return true;
    else 
        return false;
    }
    public static boolean validCashier(String emp_id)throws SQLException{
    Connection conn= DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("select * from users where empid=?");
    ps.setString(1, emp_id);
    int flag=0;
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
     flag++;
    }
    if(flag>0)
        return true;
    else 
        return false;
    }
    
    public static User searchCashier(String emp_id)throws SQLException{
  Connection conn= DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("select * from users where empid=? and usertype='Cashier'");
    ps.setString(1,emp_id);
    User u= new User();
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    u.setUserId(rs.getString("USERID"));
    u.setUserName(rs.getString("USERNAME"));
    u.setEmpId(rs.getString("EMPID"));
    }
    return u;
  } 
    
    public static boolean removeCashier(String empid) throws SQLException{
    Connection conn= DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("delete from users where empid=?");
    ps.setString(1, empid);
    int ans= ps.executeUpdate();
    if(ans==1)
        return true;
    else
        return false; 
    }
}
