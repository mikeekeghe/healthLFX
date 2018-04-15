/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthline;

import healthline.entities.Patients;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * FXML Controller class
 *
 * @author Eche Michael
 */
public class AllPatientsController implements Initializable {

    @FXML
    private Button btnClose;
    String orderby;

    int intBal;

    private ObservableList<Patients> PatientData;

    @FXML
    private TableView tablePatient;

    @FXML
    private TableColumn idCol;

    @FXML
    private TableColumn addressCol;
    @FXML
    private TableColumn ageCol;
    @FXML
    private TableColumn genderCol;
    @FXML
    private TableColumn dobCol;
    @FXML
    private TableColumn phoneCol;
    @FXML
    private TableColumn emailCol;
    @FXML
    private TableColumn hmoCol;

    @FXML
    private TableColumn dateCol;

    //START | SQLITE
    private static Connection con;
    private static Statement stat;
    private PreparedStatement prep;
    //END | SQLITE
    private String id;
    private String address;
    private String age;
    private String dob;
    private String gender;
    private String phone;
    private String email;
    private String hmo;
    private String Patients_date;

    @FXML
    private Button btnDownloadReport;
    private final String REPORT_NAME = "ALL_PATIENTS_REPORT";
    private String path;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TextField name_box;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;
    @FXML
    private TextField phone_box;
    @FXML
    private TextField address_box;
    @FXML
    private TextField email_box;
    @FXML
    private ChoiceBox choiceboxGender;
    @FXML
    private TextField age_box;
    @FXML
    private ChoiceBox choiceboxHmo;
    @FXML
    private TextField dob_box;
    private ObservableList AllHmos;
    private ObservableList allGenders;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("before refresh");
        refreshList();
       refreshItemListInCombo();
        selectFirstOne();
        System.out.println("after refresh");

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

    private void refreshList() {
        PatientData = FXCollections.observableArrayList();

        idCol.setCellValueFactory(
                new PropertyValueFactory<Patients, String>("patient_id")
        );
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Patients, String>("name")
        );

        addressCol.setCellValueFactory(
                new PropertyValueFactory<Patients, String>("address")
        );
        ageCol.setCellValueFactory(
                new PropertyValueFactory<Patients, String>("age")
        );
        genderCol.setCellValueFactory(
                new PropertyValueFactory<Patients, String>("gender")
        );
        dobCol.setCellValueFactory(
                new PropertyValueFactory<Patients, String>("dob")
        );
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<Patients, String>("phone")
        );
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Patients, String>("email")
        );
        hmoCol.setCellValueFactory(
                new PropertyValueFactory<Patients, String>("hmo")
        );
        dateCol.setCellValueFactory(
                new PropertyValueFactory<Patients, String>("patient_date")
        );

        try {
            java.util.Date maindate = new java.util.Date();
            String theDate = maindate.toString();
            String theYear = theDate.substring(24, 28);
            String theDay = theDate.substring(0, 10) + " " + theYear;

            // SQLiteConfig config = new SQLiteConfig();
            con = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            stat = con.createStatement();
            System.out.println("Opened database successfully");
            orderby = "patient_id";
            String todRepQuery = "SELECT * FROM Patient_tbl"
                    + " ORDER BY " + orderby;
            System.out.println("todRepQuery is :" + todRepQuery);
            ResultSet rs = con.createStatement().executeQuery(todRepQuery);
            while (rs.next()) {
                Patients thePatient = new Patients();

                id = rs.getString("patient_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String full_name = first_name + " " + last_name;
                address = rs.getString("address");
                age = rs.getString("age");
                gender = rs.getString("gender");
                dob = rs.getString("dob");
                phone = rs.getString("phone");
                email = rs.getString("email");
                hmo = rs.getString("hmo");

                Patients_date = rs.getString("patient_date");

                System.out.println("id = " + id);
                System.out.println("firstName = " + first_name);
                System.out.println("lastName = " + last_name);
                System.out.println("address = " + address);
                System.out.println("age = " + age);
                System.out.println("dob = " + dob);
                System.out.println("phone = " + phone);
                System.out.println("email = " + email);
                System.out.println("hmo = " + hmo);
                System.out.println("PatientsDate = " + Patients_date);

                thePatient.setPatient_id(id);
                System.out.println("after seting id is :" + thePatient.getPatient_id());

                thePatient.setName(full_name);

                thePatient.setAddress(address);
                System.out.println("after seting Patients_address1 is :" + thePatient.getAddress());

                thePatient.setAge(age);
                System.out.println("after seting Patients_age is :" + thePatient.getAge());

                thePatient.setGender(gender);
                System.out.println("after seting Patients_age is :" + thePatient.getGender());

                thePatient.setDob(dob);
                System.out.println("after seting Patients_date_of_birth is :" + thePatient.getDob());

                thePatient.setPhone(phone);
                System.out.println("after seting Patients_phone_1 is :" + thePatient.getPhone());

                thePatient.setEmail(email);
                System.out.println("after seting Patients_email is :" + thePatient.getEmail());

                thePatient.setHmo(hmo);
                System.out.println("after seting hmo is :" + thePatient.getHmo());
                thePatient.setPatient_date(Patients_date);
                System.out.println("after seting PatientsDate is :" + thePatient.getPatient_date());

                PatientData.add(thePatient);
                System.out.println("after Patients  data");
            }
            tablePatient.setItems(PatientData);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    @FXML
    private void onClickDownloadReport(ActionEvent event) throws FileNotFoundException, IOException {
        System.err.println("inside onClickDownloadReport");
        TableView<Patients> table = new TableView<Patients>();

        ObservableList<Patients> teamMembers = getReportDownloadMembers();
        table.setItems(teamMembers);
//column names and values
        TableColumn<Patients, String> excelIdCol = new TableColumn<Patients, String>("ID");
        excelIdCol.setCellValueFactory(new PropertyValueFactory<Patients, String>("patient_id"));

        TableColumn<Patients, String> excelnameCol = new TableColumn<Patients, String>("FULL NAME");
        excelnameCol.setCellValueFactory(new PropertyValueFactory<Patients, String>("name"));

        TableColumn<Patients, String> exceladdressCol = new TableColumn<Patients, String>("ADDRESS");
        exceladdressCol.setCellValueFactory(new PropertyValueFactory<Patients, String>("address"));

        TableColumn<Patients, String> excelageCol = new TableColumn<Patients, String>("AGE");
        excelageCol.setCellValueFactory(new PropertyValueFactory<Patients, String>("age"));

        TableColumn<Patients, String> excelgenderCol = new TableColumn<Patients, String>("GENDER");
        excelgenderCol.setCellValueFactory(new PropertyValueFactory<Patients, String>("gender"));

        TableColumn<Patients, String> exceldobCol = new TableColumn<Patients, String>("DATE OF BIRTH");
        exceldobCol.setCellValueFactory(new PropertyValueFactory<Patients, String>("dob"));

        TableColumn<Patients, String> excelphoneCol = new TableColumn<Patients, String>("PHONE");
        excelphoneCol.setCellValueFactory(new PropertyValueFactory<Patients, String>("phone"));

        TableColumn<Patients, String> excelemailCol = new TableColumn<Patients, String>("EMAIL");
        excelemailCol.setCellValueFactory(new PropertyValueFactory<Patients, String>("email"));

        TableColumn<Patients, String> exceldateCol = new TableColumn<Patients, String>("REG DATE");
        exceldateCol.setCellValueFactory(new PropertyValueFactory<Patients, String>("patient_date"));

        TableColumn<Patients, String> excelhmoCol = new TableColumn<Patients, String>("HMO NAME");
        excelhmoCol.setCellValueFactory(new PropertyValueFactory<Patients, String>("hmo"));

        System.err.println("after property factory");

        ObservableList<TableColumn<Patients, ?>> columns = table.getColumns();
        columns.add(excelIdCol);

        columns.add(excelnameCol);
        columns.add(exceladdressCol);
        columns.add(excelageCol);
        columns.add(excelgenderCol);
        columns.add(exceldobCol);
        columns.add(excelphoneCol);
        columns.add(excelemailCol);

        columns.add(exceldateCol);
        columns.add(excelhmoCol);

        System.err.println("after adding columns");

        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet(REPORT_NAME);
        System.err.println("after creating sheet in excel file");

        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < table.getColumns().size(); j++) {
            row.createCell(j).setCellValue(table.getColumns().get(j).getText());
        }

        for (int i = 0; i < table.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < table.getColumns().size(); j++) {
                if (table.getColumns().get(j).getCellData(i) != null) {
                    row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString());
                } else {
                    row.createCell(j).setCellValue("");
                }
            }
        }

        try {
//            java.util.Date date = new java.util.Date();
//            String theDate = date.toString();
            Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            cal.add(Calendar.DATE, 0);
            String strDateInFormat = dateFormat.format(cal.getTime());
            System.out.println("strDateInFormat is: " + strDateInFormat);
            strDateInFormat = strDateInFormat.replace("-", "");
            strDateInFormat = strDateInFormat.replace(" ", "");
            strDateInFormat = strDateInFormat.replace(":", "");
            System.out.println("strDateInFormat is: " + strDateInFormat);

            path = "C:\\Downloads\\";
            path = path.replace("\\", "/");
            FileOutputStream fileOut = new FileOutputStream(path + REPORT_NAME + strDateInFormat + ".xls");
            workbook.write(fileOut);
            System.out.println("after creating excel file");
            fileOut.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        cal.add(Calendar.DATE, 0);
        String strDateInFormat = dateFormat.format(cal.getTime());
        System.out.println("strDateInFormat is: " + strDateInFormat);
        strDateInFormat = strDateInFormat.replace("-", "");
        strDateInFormat = strDateInFormat.replace(" ", "");
        strDateInFormat = strDateInFormat.replace(":", "");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Report Downloaded Succesfully to " + path + ".\n with Filename: " + REPORT_NAME + strDateInFormat + ".xls");

        alert.showAndWait();
    }

    private ObservableList<Patients> getReportDownloadMembers() {

        ObservableList<Patients> thePatients = FXCollections.observableArrayList();
        try {
            java.util.Date maindate = new java.util.Date();
            String theDate = maindate.toString();
            String theYear = theDate.substring(24, 28);
            String theDay = theDate.substring(0, 10) + " " + theYear;

            // SQLiteConfig config = new SQLiteConfig();
            con = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            stat = con.createStatement();
            System.out.println("Opened database successfully");
            orderby = "patient_id";
            String todRepQuery = "SELECT * FROM Patient_tbl"
                    + " ORDER BY " + orderby;
            System.out.println("todRepQuery is :" + todRepQuery);
            ResultSet rs = con.createStatement().executeQuery(todRepQuery);
            while (rs.next()) {
                Patients myPatient = new Patients();

                id = rs.getString("patient_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String full_name = first_name + " " + last_name;
                address = rs.getString("address");
                age = rs.getString("age");
                dob = rs.getString("dob");
                phone = rs.getString("phone");
                email = rs.getString("email");
                hmo = rs.getString("hmo");
                Patients_date = rs.getString("patient_date");

                System.out.println("id = " + id);
                System.out.println("firstName = " + first_name);
                System.out.println("lastName = " + last_name);
                System.out.println("address = " + address);
                System.out.println("age = " + age);
                System.out.println("gender = " + gender);
                System.out.println("dob = " + dob);
                System.out.println("phone = " + phone);
                System.out.println("email = " + email);
                System.out.println("hmo = " + hmo);
                System.out.println("PatientsDate = " + Patients_date);

                myPatient.setPatient_id(id);
                System.out.println("after seting id is :" + myPatient.getPatient_id());

                myPatient.setName(full_name);
                System.out.println("after seting firstName is :" + myPatient.getFirst_name());

                myPatient.setAddress(address);
                System.out.println("after seting Patients_address1 is :" + myPatient.getAddress());

                myPatient.setAge(age);
                System.out.println("after seting Patients_age is :" + myPatient.getAge());

                myPatient.setGender(gender);
                System.out.println("after seting Patients_age is :" + myPatient.getGender());

                myPatient.setDob(dob);
                System.out.println("after seting Patients_date_of_birth is :" + myPatient.getDob());

                myPatient.setPhone(phone);
                System.out.println("after seting Patients_phone_1 is :" + myPatient.getPhone());

                myPatient.setEmail(email);
                System.out.println("after seting Patients_email is :" + myPatient.getEmail());

                myPatient.setHmo(hmo);
                System.out.println("after seting hmo is :" + myPatient.getHmo());

                myPatient.setPatient_date(Patients_date);
                System.out.println("after seting PatientsDate is :" + myPatient.getPatient_date());

                thePatients.add(myPatient);
            }

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return thePatients;
    }

    private void onClickExit(ActionEvent event) {
        Platform.exit();
    }

    private void refreshItemListInCombo() {
        //Set items equal to an empty ArrayList
        allGenders = FXCollections.observableArrayList();
        AllHmos = FXCollections.observableArrayList();

        //Select out of the DB, fill accordingly
        getGenders(allGenders);
        getAllHmos(AllHmos);

        //Set the listview to what we just populated with DB contents
        choiceboxGender.setItems(allGenders);
        choiceboxHmo.setItems(AllHmos);

    }

    private void selectFirstOne() {
        choiceboxGender.getSelectionModel().selectFirst();
        choiceboxHmo.getSelectionModel().selectFirst();
    }

    private void updateStatement(String update_query) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            System.out.println("Our query was: " + update_query);
            stmt.executeUpdate(update_query);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private void getAllHmos(ObservableList Subjects) {
        Connection c = null;
        Statement stmt = null;

        //orderby = sort_menubutton.getText();
        // ascdesc = ascdesc_menubutton.getText();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            c.setAutoCommit(false);
            System.out.println("Opened database getStudents successfully");

            //if (orderby.equals("Time")) { orderby = "TIMING";}
            //else if (orderby.equals("Title")) { orderby = "TITLE";}
            //else { orderby = "LOCATION";}
            String orderby = "hmo_id";
            System.out.println("Query is: SELECT * FROM hmo_tbl" + " ORDER BY " + orderby);
            stmt = c.createStatement();
            System.out.println("after stmt");

            ResultSet rs = stmt.executeQuery("SELECT * FROM hmo_tbl" + " ORDER BY " + orderby);
            Subjects.add("--NONE SELECTED--");
            int arrayIndexCounter = 0;
            ArrayList<String> arrayIndexStore = new ArrayList<String>();
            System.out.println("before while");

            while (rs.next()) {
                String hmo_name = rs.getString("hmo_name");

                AllHmos.add(hmo_name);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private void getGenders(ObservableList allGenders) {
        allGenders.add("--NONE SELECTED--");
        allGenders.add("MALE");
        allGenders.add("FEMALE");
    }

    @FXML
    private void onClickTable(MouseEvent event) throws ParseException {
        name_box.clear();
        address_box.clear();
        phone_box.clear();
        email_box.clear();
        age_box.clear();
        dob_box.clear();
        choiceboxHmo.getSelectionModel().clearSelection();
       choiceboxGender.getSelectionModel().clearSelection();
        selectFirstOne();
        Patients myPatients = new Patients();
        Date date = new Date();
        String theDate = date.toString();
        String Patients_name;
        String selected_Gender;
        String Patients_phone;
        String Patients_address;
        String Patients_email;
        String Selected_Hmo;
        String age;
        String dob;
        String Selected_hmo;

        myPatients = (Patients) tablePatient.getSelectionModel().getSelectedItem();
        id = myPatients.getPatient_id();
        Patients_name = myPatients.getName();
        selected_Gender = myPatients.getGender();
        Patients_phone = myPatients.getPhone();
        Patients_address = myPatients.getAddress();
        Patients_email = myPatients.getEmail();
        Selected_hmo = myPatients.getHmo();
        age = myPatients.getAge();
        dob = myPatients.getDob();
        Selected_hmo= myPatients.getHmo();

        name_box.setText(Patients_name);
        address_box.setText(Patients_address);
        phone_box.setText(Patients_phone);
        email_box.setText(Patients_email);
        System.out.println("THE selected_Hmo is : " + Selected_hmo);
        System.out.println("selected_Gender is : " + selected_Gender);
        choiceboxHmo.setValue(Selected_hmo);
        choiceboxGender.setValue(selected_Gender);
        age_box.setText(age);
        dob_box.setText(dob);

        if (("".equals(selected_Gender)) || (selected_Gender == null)) {
            choiceboxGender.getSelectionModel().selectFirst();
        }
        
         if (("".equals(Selected_hmo)) || (Selected_hmo == null)) {
            choiceboxHmo.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private boolean onClickUpdate(ActionEvent event) throws IOException {
        Patients myPatients = new Patients();
        Date date = new Date();
        int theId = Integer.parseInt(id);
        String theDate = date.toString();
        //format for report queries
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        cal.add(Calendar.DATE, 0);
        String Patients_name = name_box.getText();
        String Patients_address = address_box.getText().toUpperCase();
        String Patients_phone = phone_box.getText().toUpperCase();
        String Patients_email = email_box.getText().toUpperCase();
        String selected_Gender = choiceboxGender.getValue().toString().toUpperCase();
        String age = age_box.getText().toUpperCase();
        String dob = dob_box.getText().toUpperCase();
        String selected_Hmo = choiceboxHmo.getValue().toString().toUpperCase();

        System.out.println("selected_Hmo is: " + selected_Hmo);

        if ((Patients_name.trim().length() == 0) || (Patients_name == "") || (Patients_name.trim().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Highlight A Patients Record");

            alert.showAndWait();
            //System.clearProperty(Patients_name);
            return false;
        }
        //myPatients.setId(rs.getString("id"));

        myPatients.setName(Patients_name);
        myPatients.setPatient_date(theDate);
        myPatients.setAddress(Patients_address);

        myPatients.setPhone(Patients_phone);
        myPatients.setEmail(Patients_email);
        myPatients.setGender(selected_Gender);

        myPatients.setAge(age);
        myPatients.setDob(dob);
        myPatients.setHmo(selected_Hmo);
        ObservableList highlightedPatientsRecord, allPatientsRecords;

        highlightedPatientsRecord = tablePatient.getSelectionModel().getSelectedItems();

        System.out.println("Patients_name is : " + Patients_name);
        System.out.println("the ID IS: " + theId);
        String query = "UPDATE Patient_tbl set gender ='"
                + selected_Gender + "',"
                + "address ='"
                + Patients_address + "',"
                + "phone ='"
                + Patients_phone + "',"
                + "email ='"
                + Patients_email + "',"
                + "age ='"
                + age + "',"
                + "dob ='"
                + dob + "',"
                + "hmo ='"
                + selected_Hmo + "',"
                + "patient_date ='"
                + theDate + "'"
                + " WHERE patient_id = "
                + theId;

        System.out.println("updating\n" + query);
        updateStatement(query);

        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Information Dialog");
        alert3.setHeaderText(null);
        alert3.setContentText("Record updated Succesfully.");

        alert3.showAndWait();

        System.out.println("Succesfully Updated");

        Parent TodayReport_page_parent = FXMLLoader.load(getClass().getResource("AllPatients.fxml"));
        Scene TodayReport_page_scene = new Scene(TodayReport_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(TodayReport_page_scene);
        app_stage.show();

        return true;

    }

    @FXML
    private void onClickDelete(ActionEvent event) throws IOException {
        int theId = Integer.parseInt(id);
        String Patients_name = name_box.getText();

        if ((Patients_name.trim().length() == 0) || (Patients_name == "") || (Patients_name.trim().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Highlight A Class Room Record");

            alert.showAndWait();
            System.clearProperty(Patients_name);
        }
        //myPatients.setId(rs.getString("id"));

        ObservableList highlightedPatientsRecord, allPatientsRecords;
        allPatientsRecords = tablePatient.getItems();
        highlightedPatientsRecord = tablePatient.getSelectionModel().getSelectedItems();
        System.out.println("Patients_name is : " + Patients_name);

        String query = "DELETE FROM Patient_tbl "
                + " WHERE patient_id = "
                + theId;

        System.out.println("updating\n" + query);
        updateStatement(query);

        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Information Dialog");
        alert3.setHeaderText(null);
        alert3.setContentText("Record Deleted Succesfully.");

        alert3.showAndWait();

        System.out.println("Succesfully Updated");

        Parent TodayReport_page_parent = FXMLLoader.load(getClass().getResource("AllPatients.fxml"));
        Scene TodayReport_page_scene = new Scene(TodayReport_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(TodayReport_page_scene);
        app_stage.show();

    }

}
