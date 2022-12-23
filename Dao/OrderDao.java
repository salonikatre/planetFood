/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlanetFood.Dao;

import PlanetFood.Dbutil.DBConnection;
import PlanetFood.Pojo.Order;
import PlanetFood.Pojo.OrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Administrator
 */


public class OrderDao {
    public static String getNewOrder()throws SQLException{
    Connection conn= DBConnection.getConnection();
    Statement st= conn.createStatement();
    int id=101;
    ResultSet rs=st.executeQuery("select count(*)from orders");
    if(rs.next())
    {
    id=id+rs.getInt(1);
    }
    return "OD"+id;
    }

   public static boolean addOrder(Order order, ArrayList<OrderDetail> orderList)throws SQLException, ParseException{
   Connection conn= DBConnection.getConnection();
   PreparedStatement ps=conn.prepareStatement("insert into orders values(?,?,?,?,?,?,?)");
   ps.setString(1, order.getOrderId());
   
   String dateStr=order.getOrdDate();
   SimpleDateFormat sdf=new SimpleDateFormat("dd/MMM/yyyy");
   java.util.Date d1= sdf.parse(dateStr);
   java.sql.Date d2= new java.sql.Date(d1.getTime());
   //System.out.println("hurrey date is:"+String.valueOf(d1.getTime()));
   ps.setDate(2, d2);
   ps.setDouble(3, order.getGst());
   ps.setDouble(4,order.getGstAmount());
   ps.setDouble(5,order.getDiscount());
   ps.setDouble(6,order.getOrdAmount());
   ps.setString(7,order.getUserId());
  // ps.setDouble(8, order.getOrdAmount());
   int x =ps.executeUpdate();
   PreparedStatement ps2= conn.prepareStatement("insert into order_details values(?,?,?,?)");
   int count=0, y=0;
   for(OrderDetail detail:orderList){
   ps2.setString(1,detail.getOrdId());
   ps2.setString(2, detail.getProdId());
   ps2.setInt(3, detail.getQuantity());
   ps2.setDouble(4,detail.getCost());
   y=ps2.executeUpdate();
   if(y>0)
       count=count+y;
   }
   if(count==0 && x<0)
       return false;
   else 
       return true;
   }
    
    
    public static ArrayList<Order> getOrdersByDate(Date startDate, Date endDate, String userId)throws SQLException{
    Connection conn=DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("select * from orders where ord_date between ? and ? and userid=?");
    long ms1=startDate.getTime();
    long ms2=endDate.getTime();
    java.sql.Date d1=new java.sql.Date(ms1);
    java.sql.Date d2=new java.sql.Date(ms2);
    ps.setDate(1,d1);
    ps.setDate(2,d2);
    ps.setString(3,userId);
    ArrayList<Order> orderList= new ArrayList<>();
    ResultSet rs= ps.executeQuery();
    while(rs.next()){
    Order o= new Order();
    o.setOrderId(rs.getString("order_id"));
    java.sql.Date d= rs.getDate("ord_date");
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
    String dateStr=sdf.format(d);
    o.setOrdDate(dateStr);
   // o.setOrdAmount(rs.getDouble("ord_amount"));
    o.setGst(rs.getDouble("gst"));
    o.setGstAmount(rs.getDouble("gst_amount"));
    o.setGrandTotal(rs.getDouble("grand_total"));
    o.setDiscount(rs.getDouble("discount"));
    o.setUserId(rs.getString("userid"));
    orderList.add(o);
    }
    return orderList;
    }
    
    
    
    
public static ArrayList<Order> getAllOrders(String userid)throws SQLException{
    Connection conn=DBConnection.getConnection();
    PreparedStatement ps= conn.prepareStatement("select * from orders where userid=?");
    ps.setString(1, userid);
    ArrayList<Order> orderList= new ArrayList<>();
    ResultSet rs= ps.executeQuery();
    while(rs.next()){
    Order o= new Order();
    o.setOrderId(rs.getString("order_id"));
    java.sql.Date d= rs.getDate("ord_date");
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
    String dateStr=sdf.format(d);
    o.setOrdDate(dateStr);
   // o.setOrdAmount(rs.getDouble("ord_amount"));
    o.setGst(rs.getDouble("gst"));
    o.setGstAmount(rs.getDouble("gst_amount"));
    o.setGrandTotal(rs.getDouble("grand_total"));
    o.setDiscount(rs.getDouble("discount"));
    o.setUserId(rs.getString("userid"));
    orderList.add(o);
    }
    return orderList;
    }
    
}
