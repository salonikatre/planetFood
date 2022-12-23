/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlanetFood.Dao;

import PlanetFood.Dbutil.DBConnection;
import PlanetFood.Pojo.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class ProductDao {
    public static String getNewId()throws SQLException{
    Connection conn= DBConnection.getConnection();
    Statement st= conn.createStatement();
    int id=101;
    ResultSet rs=st.executeQuery("select count(*)from products");
    if(rs.next())
    {
    id=id+rs.getInt(1);
    }
    return "P"+id;
    }
   
    public static boolean addProduct(Product p)throws SQLException{
    Connection conn= DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("insert into products values(?,?,?,?,?)");
    ps.setString(1,p.getProdId());
    ps.setString(2,p.getCatId());
    ps.setString(3,p.getProdName());
    ps.setDouble(4,p.getProdPrice());
    ps.setString(5,p.getIsActive());
    int ans= ps.executeUpdate();
    if(ans==0)
        return false;
    else
        return true;
    
    }
public static HashMap<String,Product>getProductsByCategory(String catId)throws SQLException{
Connection conn= DBConnection.getConnection();
String qry="Select * from Products where cat_id=?";
    PreparedStatement ps= conn.prepareStatement(qry);
    ps.setString(1,catId);
    HashMap<String,Product> productList=new HashMap<String,Product>();
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
    Product p= new Product();
    p.setCatId(catId);
    p.setProdId(rs.getString("prod_id"));
    p.setProdName(rs.getString("prod_name"));
    p.setProdPrice(rs.getDouble("prod_price"));
    p.setIsActive(rs.getString("active"));
    productList.put(p.getProdId(),p);
    }
    return productList;
}
public static ArrayList<Product> getAllData()throws SQLException{
Connection conn= DBConnection.getConnection();
Statement st=conn.createStatement();
ResultSet rs=st.executeQuery("select * from products");
ArrayList<Product> prodList=new ArrayList<>();
while(rs.next())
{
Product p= new Product();
p.setProdId(rs.getString(1));
p.setCatId(rs.getString(2));
p.setProdName(rs.getString(3));
p.setProdPrice(rs.getDouble(4));
p.setIsActive(rs.getString(5));
prodList.add(p);
}
return prodList;
}
 public static boolean updateProduct(Product p)throws SQLException{
 Connection conn= DBConnection.getConnection();
 PreparedStatement ps= conn.prepareStatement("update products set prod_name=?,prod_price=?,active=? where prod_id=? ");
 ps.setString(1, p.getProdName());
 ps.setDouble(2, p.getProdPrice());
 ps.setString(3, p.getIsActive());
 ps.setString(4, p.getProdId());
 int x=ps.executeUpdate();
 if(x>0)
     return true;
 else 
     return false;
 }
 
 
 public static HashMap<String,String>getActiveProductByCategory(String catId)throws SQLException{
 Connection conn= DBConnection.getConnection();
 PreparedStatement ps= conn.prepareStatement("select * from products where cat_id=? and active='y'");
 ps.setString(1, catId);
 ResultSet rs=ps.executeQuery();
 HashMap<String, String> productList=new HashMap<>();
 while(rs.next()){
 String prodName=rs.getString("prod_name");
 String prodId=rs.getString("prod_id");
 productList.put(prodName,prodId);
 }
 return productList;
 }
 public static boolean removeProduct(String prodId)throws SQLException{
  Connection conn= DBConnection.getConnection();
   PreparedStatement ps= conn.prepareStatement("update products set active='n' where prod_id=? ");
   ps.setString(1,prodId);
   int x=ps.executeUpdate();
   if(x>0)
     return true;
 else 
     return false;
   
 }
 public static HashMap<String,Double> getAllActiveProdAndPriceByCat(String cat)throws SQLException{
   Connection conn= DBConnection.getConnection();
   PreparedStatement ps= conn.prepareStatement("select * from products where active=? and cat_id=?");
  ps.setString(1, "y");
   ps.setString(2,cat);
  // ps.setString(2, cat);
   ResultSet rs=ps.executeQuery();
   HashMap<String,Double> productList=new HashMap<>();
 while(rs.next()){
 String prodName=rs.getString("prod_name");
 Double prodPrice=rs.getDouble("prod_price");
 productList.put(prodName,prodPrice);
 }
 return productList;
 }
 
}
