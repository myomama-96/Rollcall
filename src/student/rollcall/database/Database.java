/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.rollcall.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Hp
 */
public class Database {
    
    private String url = "jdbc:mysql://localhost:3306/studentapp";
    private String username = "root";
    private String password = "";
    
    private Connection conn;
    private static Database db;
    
    private Database() throws SQLException{
        connect();
    }
    
    public static Database getInstance() throws SQLException{
        if(db == null){
            db = new Database();
        }
        return db;
    }
    
    public boolean connect() throws SQLException{
        conn = DriverManager.getConnection(url, username, password);
       
        return true;
    }
   
    
    public Connection getConnection(){
        return conn;
    }
}
