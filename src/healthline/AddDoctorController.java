/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthline;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eche Michael
 */
public class AddDoctorController implements Initializable {

    @FXML
    private Button btnClose;
    @FXML
    private TextField item_first_name_box;

    @FXML
    private TextField item_phone_box;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClear;
    @FXML
    private TextField item_email_box;
    @FXML
    private TextField item_last_name_box;
    @FXML
    private TextArea item_home_address_box;
    @FXML
    private RadioButton radio_male;
    @FXML
    private ToggleGroup radioGroupToggle;
    @FXML
    private RadioButton radio_female;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private boolean onAddButtonClicked(ActionEvent event) {
        //insert into db if valid
        RadioButton selectedRadioButton = (RadioButton) radioGroupToggle.getSelectedToggle();
        String selectedGender = selectedRadioButton.getText();
        Date date = new Date();
        String theDate = date.toString();
        String doctor_firstname = item_first_name_box.getText();
        String doctor_lastname = item_last_name_box.getText();
        String doctor_address1 = item_home_address_box.getText();
        String doctor_phone_1 = item_phone_box.getText();
        String doctor_email = item_email_box.getText();
        if ((doctor_firstname.trim().length() == 0) || (doctor_firstname == "") || (doctor_firstname.trim().isEmpty()) || (doctor_firstname == "--SELECT ONE--")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Enter a DOCTOR.");

            alert.showAndWait();
            System.clearProperty(doctor_firstname);
            return false;
        }
        if ((!doctor_phone_1.matches("[0-9]*")) || (doctor_phone_1.trim().length() == 0) || (doctor_phone_1 == "") || (doctor_phone_1.trim().isEmpty())) {

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Information Dialog");
            alert1.setHeaderText(null);
            alert1.setContentText("PHONE NUMBER has to be numeric.");

            alert1.showAndWait();
            //System.clearProperty(str_cost);
            item_phone_box.setText("0");
            return false;

        }

        doctor_firstname = "DR. "+doctor_firstname.toUpperCase();
        doctor_lastname = doctor_lastname.toUpperCase();
        doctor_address1 = doctor_address1.toUpperCase();
        doctor_phone_1 = doctor_phone_1.toUpperCase();
        doctor_email = doctor_email.toUpperCase();

        String query = "INSERT INTO Doctors_tbl (first_name,last_name,gender,address,phone,email,dotor_date ) VALUES ("
                + "'" + doctor_firstname + "'," + "'" + doctor_lastname + "'," + "'"+ selectedGender + "'," + "'" + doctor_address1 + "'," + "'" + doctor_phone_1 + "'," + "'"
                + doctor_email + "','" + theDate + "');";

        System.out.println("Inserting\n" + query);
        insertStatement(query);
        
        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Information Dialog");
        alert3.setHeaderText(null);
        alert3.setContentText("Record Added Succesfully.");

        alert3.showAndWait();
        
        System.out.println("Succesfully Inserted");
        item_first_name_box.clear();
        item_last_name_box.clear();
        item_home_address_box.clear();
        item_phone_box.clear();
        item_email_box.clear();
        
        
        return true;

    }

    private void insertStatement(String insert_query) {

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            System.out.println("Our query was: " + insert_query);
            stmt.executeUpdate(insert_query);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    @FXML
    private void onClickClose(ActionEvent event) throws IOException {

        Parent clpage_parent = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene page_scene = new Scene(clpage_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(page_scene);
        app_stage.show();
    }

    @FXML
    private void onClearButtonClicked() {
        item_first_name_box.clear();
        item_last_name_box.clear();
        item_home_address_box.clear();
        item_phone_box.clear();
        item_email_box.clear();
    }


}
