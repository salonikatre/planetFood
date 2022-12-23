/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlanetFood.Dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class DBConnection {
    private static Connection conn;
     static{
      try{
      Class.forName("oracle.jdbc.OracleDriver");
      conn=DriverManager.getConnection("jdbc:oracle:thin:@//DELL-PC:1521/xe","myproject","sachin");
      //JOptionPane.showMessageDialog(null, "Connected Sucessfully to the Database","Success!",JOptionPane.INFORMATION_MESSAGE);
        }
      catch(ClassNotFoundException ex)
      {
      JOptionPane.showMessageDialog(null, "Error loading the Driver class","ERROR!",JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
      }
     catch(SQLException sq)
      {
      JOptionPane.showMessageDialog(null, "Error loading the Driver class","ERROR!",JOptionPane.ERROR_MESSAGE);
      sq.printStackTrace();
      }
     }
     public static Connection getConnection(){
     return conn;
     }
     
      public static void closeConnection(){
        try{
            if(conn!=null){
                conn.close();
            }
        } catch(SQLException ex){ 
                {
                    JOptionPane.showMessageDialog(null,"Error Closing the Connection"+ex,"Error!!",JOptionPane.ERROR_MESSAGE);
                    
                }
            
      
    }
}
    
}
