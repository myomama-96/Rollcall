/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Date;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import student.rollcall.DAO.StudentDatabase;
import student.rollcall.Model.Student;

/**
 *
 * @author Hp
 */
public class StudentDAOTest {
    
    public StudentDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
//     @Test
     public void saveStudent() throws SQLException {
         Date date = new Date(System.currentTimeMillis());
         Student sd = new Student("su su", "susu@gamil.com","Female", date);
         StudentDatabase sdDatabase = new StudentDatabase();
         
         Assert.assertEquals(1, sdDatabase.saveStudent(sd));
     }
     
//     @Test
//     public void getStudents() throws SQLException{
//         StudentDatabase sdDatabase = new StudentDatabase();
//         Assert.assertEquals(1, sdDatabase.getStudents().size());
//     }
     
//     @Test
     public void getStudent() throws SQLException{
         StudentDatabase sdDatabase = new StudentDatabase();
         Assert.assertEquals("susu@gamil.com", sdDatabase.getStudent(1).getEmail());
     }
     
//     @Test
     public void getUpdateStudent() throws SQLException{
         
         StudentDatabase sdDatabase = new StudentDatabase();
         Student sd = sdDatabase.getStudent(1);
         sd.setEmail("susu@gmail.com");
         Assert.assertEquals(1, sdDatabase.updatStudent(sd));
     }
     
     @Test
     public void getDeleteStduent() throws SQLException{
         StudentDatabase sdDatabase = new StudentDatabase();
         Assert.assertEquals(1, sdDatabase.deleteStudent(1));
     }
     
}
  