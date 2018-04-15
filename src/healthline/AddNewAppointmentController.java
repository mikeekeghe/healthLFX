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
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eche Michael
 */
public class AddNewAppointmentController implements Initializable {

    @FXML
    private Button btnClose;
    @FXML
    private TextArea symptoms_box;
    @FXML
    private TextField treatment_box;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClear;
    @FXML
    private TextField medication_box;
    @FXML
    private TextField diagnosis_box;
    @FXML
    private TextField amount_box;
    @FXML
    private TextArea notes_box;

    @FXML
    private ChoiceBox<?> choicebox_patient;

    @FXML
    private ChoiceBox<?> choicebox_doctors;

    @FXML
    private ChoiceBox<?> choicebox_types;
    @FXML
    private TextField date_time_box;
    //String patient_id = "";

    String orderby;
    String ascdesc;
    ObservableList patients;
    ObservableList doctors;
    ObservableList patientTypes;
    @FXML
    private MenuBar myMenuBar;
    @FXML
    private MenuItem menuExit;
    
    @FXML
    private TextField roomNo_box;
    @FXML
    private TextField invoiceNo_box;

//    String[] arrayIndexStore ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDateTime();
        refreshItemListInCombo();
        selectFirstOne();
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

    private void loadDateTime() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        cal.add(Calendar.DATE, 0);
        String strDateInFormat = dateFormat.format(cal.getTime());
        System.out.println("strDateInFormat is: " + strDateInFormat);
        date_time_box.setText(strDateInFormat);

    }

    //code for combo starts
    private void refreshItemListInCombo() {
        //Set items equal to an empty ArrayList
        patients = FXCollections.observableArrayList();
        doctors = FXCollections.observableArrayList();
        patientTypes = FXCollections.observableArrayList();

        //Select out of the DB, fill accordingly
        getPatients(patients);
        getDoctors(doctors);
        getPatientTypes(patientTypes);

        //Set the listview to what we just populated with DB contents
        choicebox_patient.setItems(patients);
        choicebox_doctors.setItems(doctors);
        choicebox_types.setItems(patientTypes);
    }

    private void getPatients(ObservableList patients) {
        Connection c = null;
        Statement stmt = null;

        //orderby = sort_menubutton.getText();
        // ascdesc = ascdesc_menubutton.getText();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            c.setAutoCommit(false);
            System.out.println("Opened database getPatients successfully");

            //if (orderby.equals("Time")) { orderby = "TIMING";}
            //else if (orderby.equals("Title")) { orderby = "TITLE";}
            //else { orderby = "LOCATION";}
            String orderby = "patient_id";
            System.out.println("Query is: SELECT * FROM Patient_tbl" + " ORDER BY " + orderby);
            stmt = c.createStatement();
            System.out.println("after stmt");

            ResultSet rs = stmt.executeQuery("SELECT * FROM Patient_tbl" + " ORDER BY " + orderby);
            patients.add("--SELECT ONE--");
            int arrayIndexCounter = 0;
            ArrayList<String> arrayIndexStore = new ArrayList<String>();
            System.out.println("before while");

            while (rs.next()) {
                String patient_firstname = rs.getString("first_name");
                String patient_lastname = rs.getString("last_name");
                String patient_full_name = patient_firstname + " " + patient_lastname;

                String patient_id = rs.getString("patient_id");
                System.out.println("patient_full_name = " + patient_full_name);
                System.out.println("the patient_id = " + patient_id);

                arrayIndexStore.add(arrayIndexCounter, patient_id);
                arrayIndexCounter++;
                //IMPORTANT STATEMENT HERE:
                patients.add(patient_full_name);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    @FXML
    private boolean onAddButtonClicked(ActionEvent event) {
        //insert into db if valid
        Date date = new Date();
        String theDate = date.toString();
        //String full_name = full_name_box.getText();

        String full_name = choicebox_patient.getValue().toString();
        String symptoms = symptoms_box.getText();
        String treatment = treatment_box.getText();
        String medication = medication_box.getText();
        String diagnosis = diagnosis_box.getText();
        //String selectedDoctor_id = getSelectedDoctorId();
        String amount = amount_box.getText();
        String notes = notes_box.getText();
        String selectedPatient_id = getSelectedPatientId();
        String selected_doctor = choicebox_doctors.getValue().toString();
         String invoiceNo = invoiceNo_box.getText();
        String type = choicebox_types.getValue().toString();
        String room = roomNo_box.getText();
      
        if ((!amount.matches("[0-9]*")) || (amount.trim().length() == 0) || (amount == "") || (amount.trim().isEmpty())) {

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Information Dialog");
            alert1.setHeaderText(null);
            alert1.setContentText("AMOUNT has to be numeric.");

            alert1.showAndWait();
            //System.clearProperty(str_cost);
            amount_box.setText("0");
            return false;

        }

        if ((full_name.trim().length() == 0) || (full_name == "") || (full_name.trim().isEmpty()) || (full_name == "--SELECT ONE--")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Select a PATIENT.");

            alert.showAndWait();
            System.clearProperty(full_name);
            return false;
        }

        if ((selected_doctor.trim().length() == 0) || (selected_doctor == "") || (selected_doctor.trim().isEmpty()) || (selected_doctor == "--SELECT ONE--")) {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Information Dialog");
            alert2.setHeaderText(null);
            alert2.setContentText("select a DOCTOR.");

            alert2.showAndWait();
            System.clearProperty(selected_doctor);
            return false;
        }

        full_name = full_name.toUpperCase();
        symptoms = symptoms.toUpperCase();
        treatment = treatment.toUpperCase();
        medication = medication.toUpperCase();
        diagnosis = diagnosis.toUpperCase();
        notes = notes.toUpperCase();
        selected_doctor = selected_doctor.toUpperCase();

        String txnMonth = theDate.substring(4, 7);
        String txnYear = theDate.substring(24, 28);
        String txnDay = theDate.substring(0, 10) + " " + txnYear;
        //format for report queries
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        cal.add(Calendar.DATE, 0);
        String strDateInFormat = dateFormat.format(cal.getTime());
        System.out.println("strDateInFormat is: " + strDateInFormat);

        String query = "INSERT INTO Appointment_tbl (patient_id,full_name, symptoms,treatment,medication,diagnosis,doctor_treating,amount,notes,txnDay,txnMonth,txnYear,appointment_date,type,room,invoiceNo,date_time ) VALUES ("
                + "'" + selectedPatient_id + "'," + "'" + full_name + "'," + "'" + symptoms + "'," + "'" + treatment + "',"
                + "'" + medication + "'," + "'" + diagnosis + "'," + "'" + selected_doctor + "'," + "'" + amount + "'," + "'"
                + notes + "','" + txnDay + "'," + "'" + txnMonth + "'," + "'" + txnYear + "'," + "'" + strDateInFormat + "'," + "'"
                + type + "'," + "'" + room + "'," + "'" + invoiceNo + "'," + "'"
                + theDate + "');";

        System.out.println("Inserting\n" + query);
        insertStatement(query);

        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Information Dialog");
        alert3.setHeaderText(null);
        alert3.setContentText("Record Added Succesfully.");

        alert3.showAndWait();

        System.out.println("Succesfully Inserted");

        symptoms_box.clear();
        treatment_box.clear();
        medication_box.clear();
        diagnosis_box.clear();
        choicebox_doctors.getSelectionModel().clearSelection();
        amount_box.clear();
        notes_box.clear();
        choicebox_patient.getSelectionModel().clearSelection();

        roomNo_box.clear();
        invoiceNo_box.clear();

        loadDateTime();
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
    private void onClearButtonClicked(ActionEvent event) throws IOException {
        symptoms_box.clear();
        treatment_box.clear();
        medication_box.clear();
        diagnosis_box.clear();
        choicebox_doctors.getSelectionModel().clearSelection();
        amount_box.clear();
        notes_box.clear();
        choicebox_patient.getSelectionModel().clearSelection();
        roomNo_box.clear();
        invoiceNo_box.clear();
    }

    private void selectFirstOne() {

        choicebox_patient.getSelectionModel().selectFirst();
        choicebox_doctors.getSelectionModel().selectFirst();
        choicebox_types.getSelectionModel().selectFirst();

    }

    private String getSelectedPatientId() {
        System.out.println("inside getSelectedPatientId");
        Connection c = null;
        Statement stmt = null;
        String selectedPatientId = "";
        int choiceIndex = choicebox_patient.getSelectionModel().getSelectedIndex();

        System.out.println("Patient choiceIndex is: " + choiceIndex);

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
            String orderby = "patient_id";
            System.out.println("Query is: SELECT * FROM Patient_tbl WHERE patient_id = " + choiceIndex + " ORDER BY " + orderby);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Patient_tbl WHERE patient_id = " + choiceIndex + " ORDER BY " + orderby);

            int arrayIndexCounter = 0;
            while (rs.next()) {

                String patient_id = rs.getString("patient_id");
                System.out.println("patient_id = " + patient_id);
                selectedPatientId = patient_id;

            }
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return selectedPatientId;

    }

    private void getDoctors(ObservableList doctors) {
        Connection c = null;
        Statement stmt = null;

        //orderby = sort_menubutton.getText();
        // ascdesc = ascdesc_menubutton.getText();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            c.setAutoCommit(false);
            System.out.println("Opened database getPatients successfully");

            //if (orderby.equals("Time")) { orderby = "TIMING";}
            //else if (orderby.equals("Title")) { orderby = "TITLE";}
            //else { orderby = "LOCATION";}
            String orderby = "doctor_id";
            System.out.println("Query is: SELECT * FROM Doctors_tbl" + " ORDER BY " + orderby);
            stmt = c.createStatement();
            System.out.println("after stmt");

            ResultSet rs = stmt.executeQuery("SELECT * FROM Doctors_tbl" + " ORDER BY " + orderby);
            doctors.add("--SELECT ONE--");
            int arrayIndexCounter = 0;
            ArrayList<String> arrayIndexStore = new ArrayList<String>();
            System.out.println("before while");

            while (rs.next()) {
                String doctor_firstname = rs.getString("first_name");
                String doctor_lastname = rs.getString("last_name");
                String doctor_full_name = doctor_firstname + " " + doctor_lastname;

                String doctor_id = rs.getString("doctor_id");
                System.out.println("doctor_full_name = " + doctor_full_name);
                System.out.println("the doctor_id = " + doctor_id);

                arrayIndexStore.add(arrayIndexCounter, doctor_id);
                arrayIndexCounter++;
                //IMPORTANT STATEMENT HERE:
                doctors.add(doctor_full_name);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private String getSelectedDoctorId() {
        System.out.println("inside getSelectedDoctorId");
        Connection c = null;
        Statement stmt = null;
        String selectedDoctorId = "";
        int choiceIndex = choicebox_doctors.getSelectionModel().getSelectedIndex();

        System.out.println("Doctor choiceIndex is: " + choiceIndex);

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
            String orderby = "doctor_id";
            System.out.println("Query is: SELECT * FROM Doctors_tbl WHERE doctor_id = " + choiceIndex + " ORDER BY " + orderby);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Doctors_tbl WHERE doctor_id = " + choiceIndex + " ORDER BY " + orderby);

            int arrayIndexCounter = 0;
            while (rs.next()) {

                String doctor_id = rs.getString("doctor_id");
                System.out.println("doctor_id = " + doctor_id);
                selectedDoctorId = doctor_id;

            }
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return selectedDoctorId;
    }

    private void getPatientTypes(ObservableList patientTypes) {
        patientTypes.add("--SELECT ONE--");
        patientTypes.add("OUT PATIENT");
        patientTypes.add("IN PATIENT");
    }

    @FXML
    private void onClickExit(ActionEvent event) {
    }

}
