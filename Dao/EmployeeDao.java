/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlanetFood.Dao;

import PlanetFood.Dbutil.DBConnection;
import PlanetFood.Pojo.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class EmployeeDao {
     public static boolean addEmployee(Employee e)throws SQLException{
    Connection conn= DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("insert into employees values(?,?,?,?)");
    ps.setString(1,e.getEmpId());//1 stands for the place and the next argument for the value to be replaced for the ? we had put above
    ps.setString(2,e.getEmpName());//jaise 2nd per name jana cahiye db main isiliye humne usse second no. per rakha hai
    ps.setString(3,e.getJob());
    ps.setDouble(4, e.getSalary());
    int result=ps.executeUpdate();
     if (result==0)
         return false;
     else
         return true;
    }
    
      public static boolean editEmployee(Employee e)throws SQLException{
    Connection conn= DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("update employees set ename=?, job=?, sal=? where empid=?");
    ps.setString(1,e.getEmpName());
    ps.setString(2,e.getJob());
    ps.setDouble(3, e.getSalary());
    ps.setString(4,e.getEmpId()); 
    int ans= ps.executeUpdate();
    if(ans==1)
        return true;
    else
        return false; 
    }
      
    public static boolean removeEmployee(String empid) throws SQLException{
    Connection conn= DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("delete from employees where empid=?");
    ps.setString(1, empid);
    int ans= ps.executeUpdate();
    if(ans==1)
        return true;
    else
        return false; 
    }
  public static Employee searchEmployee(String emp_id)throws SQLException{
  Connection conn= DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("select * from employees where empid=?");
    ps.setString(1,emp_id);
    Employee e= new Employee();
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    e.setEmpName(rs.getString("ENAME"));
    e.setJob(rs.getString("JOB"));
    e.setSalary(rs.getDouble("SAL"));
    e.setEmpId(emp_id); 
    }
    return e;
  } 
  public static String getEmployeeId()throws SQLException{
   Connection conn= DBConnection.getConnection();
   Statement st= conn.createStatement();
    int id=101;
    ResultSet rs=st.executeQuery("select count(*)from employees");
    if(rs.next())
    {
    id=id+rs.getInt(1);
    }
    return "E"+id;
  }
  
  public static ArrayList<Employee> getAllEmpData()throws SQLException{
  Connection conn= DBConnection.getConnection();
   Statement st= conn.createStatement();
   ResultSet rs=st.executeQuery("Select * from employees");
   ArrayList<Employee> empdata= new ArrayList<>();
   while(rs.next()){
   Employee e=new Employee();
   e.setEmpId(rs.getString(1));
   e.setEmpName(rs.getString(2));
   e.setJob(rs.getString(3));
   e.setSalary(rs.getDouble(4));
   empdata.add(e);
   }
   return empdata;
  }
  public static ArrayList<String> getAllEmpId()throws SQLException{
    Connection conn=DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("select empid from employees");
    ArrayList<String> empIdList= new ArrayList<>();
    ResultSet rs= ps.executeQuery();
    while(rs.next()){
    String id=rs.getString(1);
    empIdList.add(id);
    }
    return empIdList;
    }
  public static ArrayList<String> getAllOnlyNonCashierId()throws SQLException{
  Connection conn=DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("select empid from employees where job='Cashier'");
     ArrayList<String> empIdList= new ArrayList<>();
    ResultSet rs= ps.executeQuery();
    while(rs.next()){
    String id=rs.getString(1);
    empIdList.add(id);
    }
    return empIdList;
    }
  
 
}
