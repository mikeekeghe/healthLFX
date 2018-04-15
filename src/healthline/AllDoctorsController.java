/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthline;

import healthline.entities.doctor;
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
import java.text.SimpleDateFormat;
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
public class AllDoctorsController implements Initializable {

    @FXML
    private Button btnClose;
    String orderby;

    int intBal;

    private ObservableList<doctor> doctorData;

    @FXML
    private TableView tableDoctor;

    @FXML
    private TableColumn idCol;

 @FXML
    private TableColumn genderCol;

    @FXML
    private TableColumn addressCol;

    @FXML
    private TableColumn phoneCol;
    @FXML
    private TableColumn emailCol;

    @FXML
    private TableColumn typeCol;
    @FXML
    private TableColumn dateCol;

    //START | SQLITE
    private static Connection con;
    private static Statement stat;
    private PreparedStatement prep;
    //END | SQLITE
    private String id;
    private String first_name;
    private String last_name;
    private String address;

    private String phone;
    private String email;
    private String type;
    private String doctor_date;
    @FXML
    private Button btnDownloadReport;
    
    private final String REPORT_NAME = "ALL_DOCTORS_REPORT";
    private String path;
    private String gender;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TextField name_box;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField type_box;
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
        doctorData = FXCollections.observableArrayList();

        idCol.setCellValueFactory(
                new PropertyValueFactory<doctor, String>("doctor_id")
        );
        nameCol.setCellValueFactory(
                new PropertyValueFactory<doctor, String>("name")
        );
    
        genderCol.setCellValueFactory(
                new PropertyValueFactory<doctor, String>("gender")
        );
           addressCol.setCellValueFactory(
                new PropertyValueFactory<doctor, String>("address")
        );

        phoneCol.setCellValueFactory(
                new PropertyValueFactory<doctor, String>("phone")
        );
        emailCol.setCellValueFactory(
                new PropertyValueFactory<doctor, String>("email")
        );

        typeCol.setCellValueFactory(
                new PropertyValueFactory<doctor, String>("type")
        );
        dateCol.setCellValueFactory(
                new PropertyValueFactory<doctor, String>("dotor_date")
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
            orderby = "doctor_id";
            String todRepQuery = "SELECT * FROM Doctors_tbl"
                    + " ORDER BY " + orderby;
            System.out.println("todRepQuery is :" + todRepQuery);
            ResultSet rs = con.createStatement().executeQuery(todRepQuery);
            while (rs.next()) {
                doctor thedoctor = new doctor();

                id = rs.getString("doctor_id");
                first_name = rs.getString("first_name");
                last_name = rs.getString("last_name");
                String full_name = first_name + " " + last_name;
                gender = rs.getString("gender");
                address = rs.getString("address");
                phone = rs.getString("phone");
                email = rs.getString("email");
                type = rs.getString("type");
                doctor_date = rs.getString("dotor_date");

                System.out.println("id = " + id);
                System.out.println("firstName = " + first_name);
                System.out.println("lastName = " + last_name);
                System.out.println("gender = " + gender);
                System.out.println("address = " + address);
                System.out.println("phone = " + phone);
                System.out.println("email = " + email);
                System.out.println("type = " + type);
                System.out.println("doctorsDate = " + doctor_date);

                thedoctor.setName(full_name);
                thedoctor.setDoctor_id(id);
                System.out.println("after seting id is :" + thedoctor.getDoctor_id());

                thedoctor.setFirst_name(first_name);
                System.out.println("after seting firstName is :" + thedoctor.getFirst_name());

                thedoctor.setLast_name(last_name);
                System.out.println("after seting lastName is :" + thedoctor.getLast_name());
                
                 thedoctor.setGender(gender);
                System.out.println("after seting gender is :" + thedoctor.getGender());

                thedoctor.setAddress(address);
                System.out.println("after seting doctors_address1 is :" + thedoctor.getAddress());

                thedoctor.setPhone(phone);
                System.out.println("after seting doctors_phone_1 is :" + thedoctor.getPhone());

                thedoctor.setEmail(email);
                System.out.println("after seting doctors_email is :" + thedoctor.getEmail());

                thedoctor.setType(type);
                System.out.println("after seting type is :" + thedoctor.getType());

                thedoctor.setDotor_date(doctor_date);
                System.out.println("after seting doctorsDate is :" + thedoctor.getDotor_date());

                doctorData.add(thedoctor);
                System.out.println("after doctors  data");
            }
            tableDoctor.setItems(doctorData);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

     @FXML
    private void onClickDownloadReport(ActionEvent event) throws FileNotFoundException, IOException {
        System.err.println("inside onClickDownloadReport");
        TableView<doctor> table = new TableView<doctor>();

        ObservableList<doctor> teamMembers = getReportDownloadMembers();
        table.setItems(teamMembers);
//column names and values
        TableColumn<doctor, String> excelIdCol = new TableColumn<doctor, String>("ID");
        excelIdCol.setCellValueFactory(new PropertyValueFactory<doctor, String>("doctor_id"));

        TableColumn<doctor, String> excelnameCol = new TableColumn<doctor, String>("NAME");
        excelnameCol.setCellValueFactory(new PropertyValueFactory<doctor, String>("name"));
        
        TableColumn<doctor, String> excelgenderCol = new TableColumn<doctor, String>("GENDER");
        excelgenderCol.setCellValueFactory(new PropertyValueFactory<doctor, String>("gender"));

        TableColumn<doctor, String> exceladdressCol = new TableColumn<doctor, String>("ADDRESS");
        exceladdressCol.setCellValueFactory(new PropertyValueFactory<doctor, String>("address"));

        TableColumn<doctor, String> excelphoneCol = new TableColumn<doctor, String>("PHONE");
        excelphoneCol.setCellValueFactory(new PropertyValueFactory<doctor, String>("phone"));

        TableColumn<doctor, String> excelemailCol = new TableColumn<doctor, String>("EMAIL");
        excelemailCol.setCellValueFactory(new PropertyValueFactory<doctor, String>("email"));

        TableColumn<doctor, String> exceltypeCol = new TableColumn<doctor, String>("TYPE");
        exceltypeCol.setCellValueFactory(new PropertyValueFactory<doctor, String>("type"));

        TableColumn<doctor, String> exceldateCol = new TableColumn<doctor, String>("DATE");
        exceldateCol.setCellValueFactory(new PropertyValueFactory<doctor, String>("dotor_date"));

        
        System.err.println("after property factory");

        ObservableList<TableColumn<doctor, ?>> columns = table.getColumns();
        columns.add(excelIdCol);
        columns.add(excelnameCol);
         columns.add(excelgenderCol);
        columns.add(exceladdressCol);
        columns.add(excelphoneCol);
        columns.add(excelemailCol);
        columns.add(exceltypeCol);
        columns.add(exceldateCol);
        

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
    private ObservableList<doctor> getReportDownloadMembers() {

        ObservableList<doctor> thedoctor = FXCollections.observableArrayList();
        try {
            java.util.Date maindate = new java.util.Date();
            String theDate = maindate.toString();
            String theYear = theDate.substring(24, 28);
            String theDay = theDate.substring(0, 10) + " " + theYear;

            // SQLiteConfig config = new SQLiteConfig();
            con = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            stat = con.createStatement();
            System.out.println("Opened database successfully");
            orderby = "doctor_id";
            String todRepQuery = "SELECT * FROM Doctors_tbl"
                    + " ORDER BY " + orderby;
            System.out.println("todRepQuery is :" + todRepQuery);
            ResultSet rs = con.createStatement().executeQuery(todRepQuery);
            while (rs.next()) {
                doctor myDoctor = new doctor();

                  id = rs.getString("doctor_id");
                first_name = rs.getString("first_name");
                last_name = rs.getString("last_name");
                String full_name = first_name + " " + last_name;
                 gender = rs.getString("gender");
                address = rs.getString("address");
                phone = rs.getString("phone");
                email = rs.getString("email");
                type = rs.getString("type");
                doctor_date = rs.getString("dotor_date");

                System.out.println("id = " + id);
                System.out.println("firstName = " + first_name);
                System.out.println("lastName = " + last_name);
                System.out.println("gender = " + gender);
                System.out.println("address = " + address);
                System.out.println("phone = " + phone);
                System.out.println("email = " + email);
                System.out.println("type = " + type);
                System.out.println("doctorsDate = " + doctor_date);

                myDoctor.setDoctor_id(id);
                System.out.println("after seting id is :" + myDoctor.getDoctor_id());

                myDoctor.setName(full_name);

                myDoctor.setLast_name(last_name);
                System.out.println("after seting lastName is :" + myDoctor.getLast_name());
                
                 myDoctor.setGender(gender);
                System.out.println("after seting doctors_address1 is :" + myDoctor.getGender());


                myDoctor.setAddress(address);
                System.out.println("after seting doctors_address1 is :" + myDoctor.getAddress());

                myDoctor.setPhone(phone);
                System.out.println("after seting doctors_phone_1 is :" + myDoctor.getPhone());

                myDoctor.setEmail(email);
                System.out.println("after seting doctors_email is :" + myDoctor.getEmail());

                myDoctor.setType(type);
                System.out.println("after seting type is :" + myDoctor.getType());

                myDoctor.setDotor_date(doctor_date);
                System.out.println("after seting doctorsDate is :" + myDoctor.getDotor_date());

                thedoctor.add(myDoctor);
            }

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return thedoctor;
    }

    private void onClickExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void onClickTable(MouseEvent event) {
         name_box.clear();
        address_box.clear();
        phone_box.clear();
        email_box.clear();
        type_box.clear();

        choiceboxGender.getSelectionModel().clearSelection();
        selectFirstOne();
        doctor mydoctor = new doctor();
        Date date = new Date();
        String theDate = date.toString();
        String doctor_name;
        String selected_Gender;
        String doctor_phone;
        String doctor_address;
        String doctor_email;
        String doctor_type;
        String selected_Subject1;
        String selected_Subject2;
        String selected_Subject3;
        String selected_Subject4;

        mydoctor = (doctor) tableDoctor.getSelectionModel().getSelectedItem();
        id = mydoctor.getDoctor_id();
        doctor_name = mydoctor.getName();
        selected_Gender = mydoctor.getGender();
        doctor_phone = mydoctor.getPhone();
        doctor_address = mydoctor.getAddress();
        doctor_email = mydoctor.getEmail();
        doctor_type = mydoctor.getType();
    

        name_box.setText(doctor_name);
        address_box.setText(doctor_address);
        phone_box.setText(doctor_phone);
        email_box.setText(doctor_email);
        type_box.setText(doctor_type);
    
        choiceboxGender.setValue(selected_Gender);
        
        if (("".equals(selected_Gender)) || (selected_Gender==null)) {
            choiceboxGender.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private boolean onClickUpdate(ActionEvent event) throws IOException {
          doctor mydoctor = new doctor();
        Date date = new Date();
        int theId = Integer.parseInt(id);
        String theDate = date.toString();
        //format for report queries
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        cal.add(Calendar.DATE, 0);
        String doctor_name = name_box.getText();
        String doctor_address = address_box.getText().toUpperCase();
        String doctor_phone = phone_box.getText().toUpperCase();
        String doctor_email = email_box.getText().toUpperCase();
        String doctor_type = type_box.getText().toUpperCase();
        String selected_Gender = choiceboxGender.getValue().toString().toUpperCase();
       

        System.out.println("doctor_name is: " + doctor_name);

        if ((doctor_name.trim().length() == 0) || (doctor_name == "") || (doctor_name.trim().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Highlight A Doctor Record");

            alert.showAndWait();
            //System.clearProperty(doctor_name);
            return false;
        }
        //mydoctor.setId(rs.getString("id"));

        mydoctor.setName(doctor_name);
        mydoctor.setDotor_date(theDate);
        mydoctor.setAddress(doctor_address);
        mydoctor.setPhone(doctor_phone);
        mydoctor.setEmail(doctor_email);
        mydoctor.setType(doctor_type);
        mydoctor.setGender(selected_Gender);

        ObservableList highlighteddoctorRecord, alldoctorRecords;

        highlighteddoctorRecord = tableDoctor.getSelectionModel().getSelectedItems();

        System.out.println("doctor_name is : " + doctor_name);
        System.out.println("the ID IS: " + theId);
        String query = "UPDATE Doctors_tbl set gender ='"
                + selected_Gender + "',"
                + "address ='"
                + doctor_address + "',"
                + "phone ='"
                + doctor_phone + "',"
                + "email ='"
                + doctor_email + "',"
                + "type ='"
                + doctor_type + "'"
                + " WHERE doctor_id = "
                + theId;

        System.out.println("updating\n" + query);
        updateStatement(query);

        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Information Dialog");
        alert3.setHeaderText(null);
        alert3.setContentText("Record updated Succesfully.");

        alert3.showAndWait();

        System.out.println("Succesfully Updated");

        Parent TodayReport_page_parent = FXMLLoader.load(getClass().getResource("AllDoctors.fxml"));
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
        String doctor_name = name_box.getText();

        if ((doctor_name.trim().length() == 0) || (doctor_name == "") || (doctor_name.trim().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Highlight A Class Room Record");

            alert.showAndWait();
            System.clearProperty(doctor_name);
        }
        //mydoctor.setId(rs.getString("id"));

        ObservableList highlighteddoctorRecord, alldoctorRecords;
        alldoctorRecords = tableDoctor.getItems();
        highlighteddoctorRecord = tableDoctor.getSelectionModel().getSelectedItems();
        System.out.println("doctor_name is : " + doctor_name);

        String query = "DELETE FROM Doctors_tbl "
                + " WHERE doctor_id = "
                + theId;

        System.out.println("updating\n" + query);
        updateStatement(query);

        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Information Dialog");
        alert3.setHeaderText(null);
        alert3.setContentText("Record Deleted Succesfully.");

        alert3.showAndWait();

        System.out.println("Succesfully Updated");

        Parent TodayReport_page_parent = FXMLLoader.load(getClass().getResource("AllDoctors.fxml"));
        Scene TodayReport_page_scene = new Scene(TodayReport_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(TodayReport_page_scene);
        app_stage.show();

    }

    private void selectFirstOne() {
       choiceboxGender.getSelectionModel().selectFirst();   }

    private void refreshItemListInCombo() {
        //Set items equal to an empty ArrayList
        allGenders = FXCollections.observableArrayList();

        //Select out of the DB, fill accordingly
        getGenders(allGenders);

        //Set the listview to what we just populated with DB contents
        choiceboxGender.setItems(allGenders); }

    private void getGenders(ObservableList<Object> allGenders) {
        allGenders.add("--NONE SELECTED--");
        allGenders.add("MALE");
        allGenders.add("FEMALE"); }

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
        } }
}
