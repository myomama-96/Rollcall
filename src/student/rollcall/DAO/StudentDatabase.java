/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.rollcall.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import student.rollcall.Model.Student;
import student.rollcall.database.Database;

/**
 *
 * @author Hp
 */
public class StudentDatabase {
    
    //CRUD
    
    public int saveStudent(Student student) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "insert into students (name, email, gender, dob) value (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, student.getName());
        stmt.setString(2, student.getEmail());
        stmt.setString(3, student.getGender());
        stmt.setDate(4, student.getDob());
        int rows = stmt.executeUpdate();
        return rows;
    }
    
    public List<Student> getStudents() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from students";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        List<Student> studentlist = new ArrayList<>();
        
        while(result.next()){
            int id = result.getInt("id");
            String name = result.getString("name");
            String email = result.getString("email");
            String gender = result.getString("gender");
            Date dob = result.getDate("dob");
            
            Student sd = new Student(id, name, email, gender, dob);
            studentlist.add(sd);
        }
        
        return studentlist;
    }
    
    public Student getStudent(int id) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from students where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        Student sd =  null;
        
        if(result.next()){
            String name = result.getString("name");
            String email = result.getString("email");
            String gender = result.getString("gender");
            Date dob = result.getDate("dob");
            
            sd = new Student(id, name, email, gender, dob);
            
        }
        
        return sd;
    }
    
    public int updatStudent(Student student) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "update students set name=?, email=?, gender=?, dob=? where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, student.getName());
        stmt.setString(2, student.getEmail());
        stmt.setString(3, student.getGender());
        stmt.setDate(4, student.getDob());
        stmt.setInt(5, student.getId());
        
        int rows = stmt.executeUpdate();
        
        return rows;   
    }    
        
    public int deleteStudent(int id) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "delete from students where id =?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();
        return rows;
    }
}
    
