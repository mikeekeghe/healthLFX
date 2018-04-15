/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthline;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Eche Michael
 */
public class AddNewPatientController implements Initializable {

    @FXML
    private Button btnClose;

    @FXML
    private TextField item_first_name_box;
    @FXML
    private TextField item_last_name_box;
    @FXML
    private TextField item_age_box;
    @FXML
    private DatePicker item_dob_box;
    @FXML
    private TextArea item_home_address_box;
    @FXML
    private TextArea item_work_address_box;
    @FXML
    private TextField item_phone_box;
    @FXML
    private TextField item_email_box;
    @FXML
    private ChoiceBox choicebox_hmo;
    ObservableList hmos;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClear;
    @FXML
    private ToggleGroup radioGroupToggle;
    @FXML
    private RadioButton radio_male;
    @FXML
    private RadioButton radio_female;
    
    String item_dob;

    @FXML
    private boolean onAddButtonClicked(ActionEvent event) {
        //insert into db if valid
        RadioButton selectedRadioButton = (RadioButton) radioGroupToggle.getSelectedToggle();
        String selectedGender = selectedRadioButton.getText();
        Date date = new Date();
        String theDate = date.toString();
        String item_first_name = item_first_name_box.getText();
        String item_last_name = item_last_name_box.getText();
        String item_age = item_age_box.getText();
        if (item_dob_box.getValue()==null) {
           
        }
        else{
             item_dob = item_dob_box.getValue().toString();
        }
        String item_home_address = item_home_address_box.getText();
        String item_work_address = item_work_address_box.getText();
        String item_phone = item_phone_box.getText();
        String item_email = item_email_box.getText();
        String choiceboxhmo = choicebox_hmo.getValue().toString();

        if ((!item_phone.matches("[0-9]*")) || (item_phone.trim().length() == 0) || (item_phone == "") || (item_phone.trim().isEmpty())) {

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Information Dialog");
            alert1.setHeaderText(null);
            alert1.setContentText("PHONE NUMBER has to be numeric.");

            alert1.showAndWait();
            //System.clearProperty(str_cost);
            item_phone_box.setText("0");
            return false;

        }

        if ((item_first_name.trim().length() == 0) || (item_first_name == "") || (item_first_name.trim().isEmpty()) || (item_first_name == "--SELECT ONE--")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("FIRST NAME is mandatory.");

            alert.showAndWait();
            return false;
        }

        item_first_name = item_first_name.toUpperCase();
        item_last_name = item_last_name.toUpperCase();
        item_age = item_age.toUpperCase();
        item_dob = item_dob.toUpperCase();
        item_home_address = item_home_address.toUpperCase();
        item_work_address = item_work_address.toUpperCase();
        item_phone = item_phone.toUpperCase();
        item_email = item_email.toUpperCase();
        choiceboxhmo = choiceboxhmo.toUpperCase();

        String txnMonth = theDate.substring(4, 7);
        String txnYear = theDate.substring(24, 28);
        String txnDay = theDate.substring(0, 10) + " " + txnYear;
//         Calendar cal = Calendar.getInstance();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        cal.add(Calendar.DATE, 0);
//        String strDateInFormat = dateFormat.format(cal.getTime());
//        System.out.println("strDateInFormat is: " + strDateInFormat);

        String query = "INSERT INTO Patient_tbl (first_name,last_name,age,dob,gender,address,address2,phone,email,hmo,patient_date ) VALUES ("
                + "'" + item_first_name + "'," + "'" + item_last_name + "'," + "'" + item_age + "'," + "'" + item_dob + "'," + "'" + selectedGender + "',"
                + "'" + item_home_address + "'," + "'" + item_work_address + "'," + "'" + item_phone + "'," + "'" + item_email + "'," + "'"
                + choiceboxhmo + "','" + theDate + "');";

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
        item_age_box.clear();
        item_dob_box.setValue(null);
        item_home_address_box.clear();
        item_work_address_box.clear();
        item_phone_box.clear();
        item_email_box.clear();
        choicebox_hmo.getSelectionModel().clearSelection();

        selectFirstOne();

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
        item_age_box.clear();
        item_dob_box.setValue(null);
        item_home_address_box.clear();
        item_work_address_box.clear();
        item_phone_box.clear();
        item_email_box.clear();
        choicebox_hmo.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshItemListInCombo();
        selectFirstOne();
    }

    private void selectFirstOne() {

        choicebox_hmo.getSelectionModel().selectFirst();
    }

    private void refreshItemListInCombo() {
        //Set items equal to an empty ArrayList
        hmos = FXCollections.observableArrayList();

        //Select out of the DB, fill accordingly
        getHmos(hmos);

        //Set the listview to what we just populated with DB contents
        choicebox_hmo.setItems(hmos);

    }

    private void getHmos(ObservableList hmos) {
        Connection c = null;
        Statement stmt = null;

        //orderby = sort_menubutton.getText();
        // ascdesc = ascdesc_menubutton.getText();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            //if (orderby.equals("Time")) { orderby = "TIMING";}
            //else if (orderby.equals("Title")) { orderby = "TITLE";}
            //else { orderby = "LOCATION";}
            String orderby = "hmo_id";
            System.out.println("Query is: SELECT * FROM hmo_tbl" + " ORDER BY " + orderby);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM hmo_tbl" + " ORDER BY " + orderby);
            hmos.add("--NONE--");
            while (rs.next()) {
                String hmo_name = rs.getString("hmo_name");

                //IMPORTANT STATEMENT HERE:
                hmos.add(hmo_name);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

}
