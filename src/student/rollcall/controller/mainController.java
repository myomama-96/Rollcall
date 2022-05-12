/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.rollcall.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import student.rollcall.DAO.StudentDatabase;
import student.rollcall.Model.Student;

/**
 *
 * @author Hp
 */
public class mainController implements Initializable {
    
    private Label label;
    @FXML
    private ToggleGroup gender;
    @FXML
    private Button saveBtn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private RadioButton femaleRadio;
    @FXML
    private DatePicker dobPicker;

    private StudentDatabase studentDB;
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, Integer> idCol;
    @FXML
    private TableColumn<Student, String> nameCol;
    @FXML
    private TableColumn<Student, String> emailCol;
    @FXML
    private TableColumn<Student, String> genderCol;
    @FXML
    private TableColumn<Student, Date> dobCol;
    @FXML
    private MenuItem editItem;
    @FXML
    private MenuItem deleteItem;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       maleRadio.setUserData("Male");
       femaleRadio.setUserData("Female");
        
       studentDB = new StudentDatabase();
       
       initColumns();
       
       loadTableData();
       
    }    

    @FXML
    private void saveStudentInfo(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        RadioButton radioButton = (RadioButton)gender.getSelectedToggle();
        String stringRadio = (String) radioButton.getUserData();
        LocalDate dateLocal = dobPicker.getValue();
        
        //Validate
        if(name.isEmpty() || email.isEmpty() || dateLocal == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please Fill Out All Fields!");
            alert.setHeaderText(null);
            alert.setTitle("To Field All Fields!");
            alert.show();
            
            return;
        }
        
        Date date = Date.valueOf(dateLocal);
        
        Student sd = new Student(name, email, stringRadio, date);
        
        try {
            studentDB.saveStudent(sd);
            clearForm();
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("All Data Successful.");
            alert.setHeaderText(null);
            alert.setTitle("Data Save");
            alert.show();
            
            loadTableData();

        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }
        
        System.out.println("Save Button");
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        gender.selectToggle(maleRadio);
        dobPicker.setValue(null);
    }

    private void initColumns() {
       idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
       genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
       dobCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
    }

    private void loadTableData() {
        try {
            List<Student> listStudent = studentDB.getStudents();
            studentTable.getItems().setAll(listStudent);
        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editLoadWindow(ActionEvent event) throws IOException {
       
        //Selected Student
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        
        if(selectedStudent == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please Select You Want to Edit Student Roll.");
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.show();
            return;
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/rollcall/views/edit.fxml"));
        Parent root = loader.load(); //EditController ye instance object ya mal
        EditController controller = loader.getController();
        controller.setStudentData(selectedStudent);
        Scene scene = new Scene(root);
        Stage editStage = new Stage();
        editStage.setScene(scene);
        editStage.initModality(Modality.WINDOW_MODAL);
        editStage.initOwner(studentTable.getScene().getWindow());
        editStage.showAndWait();
        loadTableData();
        
    }

    @FXML
    private void deleteSelectedStutdent(ActionEvent event) {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        
        if(selectedStudent == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please Selected The Student You Want To Delete!");
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.show();
            return;
        }
        
        try {
            studentDB.deleteStudent(selectedStudent.getId());
            studentTable.getItems().remove(selectedStudent);
        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
