/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.rollcall.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import student.rollcall.DAO.StudentDatabase;
import student.rollcall.Model.Student;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class EditController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private ToggleGroup genderRadio;
    @FXML
    private RadioButton femaleRadio;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    
    private int studentID;
    
    private StudentDatabase studentDB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        maleRadio.setUserData("Male");
        femaleRadio.setUserData("Female");
        
        studentDB = new StudentDatabase();
    }    

    @FXML
    private void updateStudent(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        RadioButton selectedRadio = (RadioButton)genderRadio.getSelectedToggle();
        String stringRadio = (String)selectedRadio.getUserData();
        LocalDate localdate = (LocalDate)dobPicker.getValue();
        if(name.isEmpty() || email.isEmpty() || localdate == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please Enter all Field.");
            alert.setHeaderText(null); 
            alert.setTitle("Error");
            alert.show();
        }
        
        Date date = Date.valueOf(localdate);
        
        Student sd = new Student(studentID, name, email, stringRadio, date);
        
        try {
            studentDB.updatStudent(sd);
            Stage stage = (Stage)saveBtn.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void closeCurrentWindow(ActionEvent event) {
        Stage stage = (Stage)saveBtn.getScene().getWindow();
        stage.close();
    }
    
    public void setStudentData(Student student){
        studentID = student.getId();
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
        if(student.getGender().equals("Male")){
            maleRadio.setSelected(true);
        }else{
            femaleRadio.setSelected(true);
        }
        Date date = student.getDob();
        dobPicker.setValue(date.toLocalDate());
    }
    
}
