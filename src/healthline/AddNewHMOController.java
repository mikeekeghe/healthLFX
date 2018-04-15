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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eche Michael
 */
public class AddNewHMOController implements Initializable {

    @FXML
    private Button btnClose;
    @FXML
    private TextField hmo_name_box;
    @FXML
    private TextField item_phone_box;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClear;
    @FXML
    private TextField item_email_box;
    @FXML
    private TextField reg_no_box;
    @FXML
    private TextArea hmo_address_box;
    @FXML
    private TextField hmo_type_box;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private boolean onAddButtonClicked(ActionEvent event) {
        //insert into db if valid
        Date date = new Date();
        String theDate = date.toString();
        String hmo_name = hmo_name_box.getText();
        String reg_no = reg_no_box.getText();
        String hmo_address = hmo_address_box.getText();
        String item_phone = item_phone_box.getText();
        String item_email = item_email_box.getText();
        String hmo_type = hmo_type_box.getText();

        if ((hmo_name.trim().length() == 0) || (hmo_name == "") || (hmo_name.trim().isEmpty()) || (hmo_name == "--SELECT ONE--")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("HMO NAME is missing.");

            alert.showAndWait();
            //System.clearProperty(hmo_name);
            return false;
        }

        hmo_name = hmo_name.toUpperCase();
        reg_no = reg_no.toUpperCase();
        hmo_address = hmo_address.toUpperCase();
        item_phone = item_phone.toUpperCase();
        item_email = item_email.toUpperCase();
        hmo_type = hmo_type.toUpperCase();

        String query = "INSERT INTO hmo_tbl (hmo_name,hmo_address1,hmo_reg_no,hmo_phone_1,hmo_email,hmo_type,date_time ) VALUES ('"
                + hmo_name + "'," + "'" + hmo_address + "',"
                + "'" + reg_no + "'," + "'" + item_phone + "'," + "'" + item_email + "'," + "'" + hmo_type
                + "','" + theDate + "');";

        System.out.println("Inserting\n" + query);
        insertStatement(query);
        
       Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Information Dialog");
        alert3.setHeaderText(null);
        alert3.setContentText("Record Added Succesfully.");

        alert3.showAndWait();
        
        System.out.println("Succesfully Inserted");
        hmo_name_box.clear();
        reg_no_box.clear();
        hmo_address_box.clear();
        item_phone_box.clear();
        item_email_box.clear();
        hmo_type_box.clear();
        return true;
    }

    @FXML
    private void onClearButtonClicked(ActionEvent event) {
        hmo_name_box.clear();
        reg_no_box.clear();
        hmo_address_box.clear();
        item_phone_box.clear();
        item_email_box.clear();
        hmo_type_box.clear();
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

}
