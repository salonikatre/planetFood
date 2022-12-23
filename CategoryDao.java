/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlanetFood.Dao;

import PlanetFood.Dbutil.DBConnection;
import PlanetFood.Pojo.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Administrator
 */
public class CategoryDao {
    public static HashMap<String,String> getAllCategoryId()throws SQLException{
    Connection conn= DBConnection.getConnection();
    Statement st= conn.createStatement();
    ResultSet rs=st.executeQuery("select * from categories");
    HashMap<String, String> category= new HashMap<>();
    while(rs.next())
    {
    String cat_id=rs.getString(1);
    String cat_name=rs.getString(2);
    category.put(cat_id,cat_name);
    }
    return category;
    }
    
    public static String getCategoryId(String category_name)throws SQLException{    
     Connection conn= DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("Select cat_id from categories where cat_name=?");
    ps.setString(1, category_name);
    ResultSet rs=ps.executeQuery();
     String result=null;
    while(rs.next())
    {
    result=rs.getString(1);
    }
    return result;
    }
     public static String getNewCatId()throws SQLException{
    Connection conn= DBConnection.getConnection();
    Statement st= conn.createStatement();
    int id=101;
    ResultSet rs=st.executeQuery("select count(*)from categories");
    if(rs.next())
    {
    id=id+rs.getInt(1);
    }
    return "C"+id;
    }
   public static boolean addProduct(String cat_id, String cat_name)throws SQLException{
    Connection conn= DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("insert into categories values(?,?)");
    ps.setString(1, cat_id);
    ps.setString(2, cat_name);
    int ans= ps.executeUpdate();
    if(ans==0)
        return false;
    else
        return true;
    
    } 
   public static String getCategoryName(String category_id)throws SQLException{    
     Connection conn= DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("Select cat_name from categories where cat_id=?");
    ps.setString(1, category_id);
    ResultSet rs=ps.executeQuery();
     String result=null;
    while(rs.next())
    {
    result=rs.getString(1);
    }
    return result;
    }
    public static boolean updateCategory(String cat_id, String cat_name)throws SQLException{    
       Connection conn= DBConnection.getConnection();
       PreparedStatement ps= conn.prepareStatement("Update categories set cat_name=? where cat_id=?");
       ps.setString(1, cat_name);
       ps.setString(2, cat_id);
       int ans=ps.executeUpdate();
       if(ans==0)
        return false;
       else
        return true;
    
    }
    
    public static ArrayList<Category> getAllCategory()throws SQLException{
    Connection conn=DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("select * from categories");
    ArrayList<Category> catList= new ArrayList<>();
    ResultSet rs= ps.executeQuery();
    while(rs.next()){
    Category c= new Category();
    c.setCat_id(rs.getString("cat_id"));
    c.setCat_name(rs.getString("cat_name"));
    catList.add(c);
    }
    return catList;
    }
}
