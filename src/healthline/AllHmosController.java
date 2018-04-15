/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthline;

import healthline.entities.hmo;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
public class AllHmosController implements Initializable {

    @FXML
    private Button btnClose;
    String orderby;

    int intBal;

    private ObservableList<hmo> hmoData;

    @FXML
    private TableView tableHmo;

    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn nameCol;

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
    private String hmo_name;
    private String hmo_address1;
    private String hmo_reg_no;
    private String hmo_phone_1;
    private String hmo_email;
    private String hmo_type;
    private String date_time;
    
    @FXML
    private Button btnDownloadReport;

    private final String REPORT_NAME = "ALL_HMOs_REPORT";
    private String path;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("before refresh");
        refreshList();

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
        hmoData = FXCollections.observableArrayList();

        idCol.setCellValueFactory(
                new PropertyValueFactory<hmo, String>("hmo_id")
        );
        nameCol.setCellValueFactory(
                new PropertyValueFactory<hmo, String>("hmo_name")
        );
        addressCol.setCellValueFactory(
                new PropertyValueFactory<hmo, String>("hmo_address1")
        );

        phoneCol.setCellValueFactory(
                new PropertyValueFactory<hmo, String>("hmo_phone_1")
        );
        emailCol.setCellValueFactory(
                new PropertyValueFactory<hmo, String>("hmo_email")
        );

        typeCol.setCellValueFactory(
                new PropertyValueFactory<hmo, String>("hmo_type")
        );
        dateCol.setCellValueFactory(
                new PropertyValueFactory<hmo, String>("date_time")
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
            orderby = "hmo_id";
            String todRepQuery = "SELECT * FROM hmo_tbl"
                    + " ORDER BY " + orderby;
            System.out.println("todRepQuery is :" + todRepQuery);
            ResultSet rs = con.createStatement().executeQuery(todRepQuery);
            while (rs.next()) {
                hmo thehmo = new hmo();

                id = rs.getString("hmo_id");
                hmo_name = rs.getString("hmo_name");
                hmo_address1 = rs.getString("hmo_address1");
                hmo_phone_1 = rs.getString("hmo_phone_1");
                hmo_email = rs.getString("hmo_email");
                hmo_type = rs.getString("hmo_type");
                date_time = rs.getString("date_time");

                System.out.println("id = " + id);
                System.out.println("hmo_name = " + hmo_name);
                System.out.println("hmo_address1 = " + hmo_address1);
                System.out.println("phone = " + hmo_phone_1);
                System.out.println("email = " + hmo_email);
                System.out.println("type = " + hmo_type);
                System.out.println("hmosDate = " + date_time);

                thehmo.setHmo_id(id);
                System.out.println("after seting id is :" + thehmo.getHmo_id());

                thehmo.setHmo_name(hmo_name);
                System.out.println("after seting firstName is :" + thehmo.getHmo_name());

                thehmo.setHmo_address1(hmo_address1);
                System.out.println("after seting hmos_address1 is :" + thehmo.getHmo_address1());

                thehmo.setHmo_phone_1(hmo_phone_1);
                System.out.println("after seting hmos_phone_1 is :" + thehmo.getHmo_phone_1());

                thehmo.setHmo_email(hmo_email);
                System.out.println("after seting hmos_email is :" + thehmo.getHmo_email());

                thehmo.setHmo_type(hmo_type);
                System.out.println("after seting type is :" + thehmo.getHmo_type());

                thehmo.setDate_time(date_time);
                System.out.println("after seting hmosDate is :" + thehmo.getDate_time());

                hmoData.add(thehmo);
                System.out.println("after hmos  data");
            }
            tableHmo.setItems(hmoData);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    @FXML
    private void onClickDownloadReport(ActionEvent event) throws FileNotFoundException, IOException {
        System.err.println("inside onClickDownloadReport");
        TableView<hmo> table = new TableView<hmo>();

        ObservableList<hmo> teamMembers = getReportDownloadMembers();
        table.setItems(teamMembers);
//column names and values
        TableColumn<hmo, String> excelIdCol = new TableColumn<hmo, String>("ID");
        excelIdCol.setCellValueFactory(new PropertyValueFactory<hmo, String>("hmo_id"));

        TableColumn<hmo, String> excelnameCol = new TableColumn<hmo, String>("HMO NAME");
        excelnameCol.setCellValueFactory(new PropertyValueFactory<hmo, String>("hmo_name"));

        TableColumn<hmo, String> exceladdressCol = new TableColumn<hmo, String>("ADDRESS");
        exceladdressCol.setCellValueFactory(new PropertyValueFactory<hmo, String>("hmo_address1"));

        TableColumn<hmo, String> excelphoneCol = new TableColumn<hmo, String>("PHONE");
        excelphoneCol.setCellValueFactory(new PropertyValueFactory<hmo, String>("hmo_phone_1"));

        TableColumn<hmo, String> excelemailCol = new TableColumn<hmo, String>("EMAIL ADDRESS");
        excelemailCol.setCellValueFactory(new PropertyValueFactory<hmo, String>("hmo_email"));

        TableColumn<hmo, String> exceltypeCol = new TableColumn<hmo, String>("HMO TYPE");
        exceltypeCol.setCellValueFactory(new PropertyValueFactory<hmo, String>("hmo_type"));

        TableColumn<hmo, String> exceldateCol = new TableColumn<hmo, String>("REG DATE");
        exceldateCol.setCellValueFactory(new PropertyValueFactory<hmo, String>("date_time"));

        System.err.println("after property factory");

        ObservableList<TableColumn<hmo, ?>> columns = table.getColumns();
        columns.add(excelIdCol);
        columns.add(excelnameCol);
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

    private ObservableList<hmo> getReportDownloadMembers() {

        ObservableList<hmo> theHmos = FXCollections.observableArrayList();
        try {
            java.util.Date maindate = new java.util.Date();
            String theDate = maindate.toString();
            String theYear = theDate.substring(24, 28);
            String theDay = theDate.substring(0, 10) + " " + theYear;

            // SQLiteConfig config = new SQLiteConfig();
            con = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            stat = con.createStatement();
            System.out.println("Opened database successfully");
            orderby = "hmo_id";
            String todRepQuery = "SELECT * FROM hmo_tbl"
                    + " ORDER BY " + orderby;
            System.out.println("todRepQuery is :" + todRepQuery);
            ResultSet rs = con.createStatement().executeQuery(todRepQuery);
            while (rs.next()) {
                hmo myHmo = new hmo();

                id = rs.getString("hmo_id");
                hmo_name = rs.getString("hmo_name");
                hmo_address1 = rs.getString("hmo_address1");
                hmo_phone_1 = rs.getString("hmo_phone_1");
                hmo_email = rs.getString("hmo_email");
                hmo_type = rs.getString("hmo_type");
                date_time = rs.getString("date_time");

                System.out.println("id = " + id);
                System.out.println("hmo_name = " + hmo_name);
                System.out.println("hmo_address1 = " + hmo_address1);
                System.out.println("phone = " + hmo_phone_1);
                System.out.println("email = " + hmo_email);
                System.out.println("type = " + hmo_type);
                System.out.println("hmosDate = " + date_time);

                myHmo.setHmo_id(id);
                System.out.println("after seting id is :" + myHmo.getHmo_id());

                myHmo.setHmo_name(hmo_name);
                System.out.println("after seting firstName is :" + myHmo.getHmo_name());

                myHmo.setHmo_address1(hmo_address1);
                System.out.println("after seting hmos_address1 is :" + myHmo.getHmo_address1());

                myHmo.setHmo_phone_1(hmo_phone_1);
                System.out.println("after seting hmos_phone_1 is :" + myHmo.getHmo_phone_1());

                myHmo.setHmo_email(hmo_email);
                System.out.println("after seting hmos_email is :" + myHmo.getHmo_email());

                myHmo.setHmo_type(hmo_type);
                System.out.println("after seting type is :" + myHmo.getHmo_type());

                myHmo.setDate_time(date_time);
                System.out.println("after seting hmosDate is :" + myHmo.getDate_time());

                theHmos.add(myHmo);
            }

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return theHmos;
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

        hmo myhmo = new hmo();
        Date date = new Date();
        String theDate = date.toString();
        String hmo_name;
        String hmo_phone;
        String hmo_address;
        String hmo_email;
        String hmo_type;

        myhmo = (hmo) tableHmo.getSelectionModel().getSelectedItem();
        id = myhmo.getHmo_id();
        hmo_name = myhmo.getHmo_name();
        hmo_phone = myhmo.getHmo_phone_1();
        hmo_address = myhmo.getHmo_address1();
        hmo_email = myhmo.getHmo_email();
        hmo_type = myhmo.getHmo_type();

        name_box.setText(hmo_name);
        address_box.setText(hmo_address);
        phone_box.setText(hmo_phone);
        email_box.setText(hmo_email);
        type_box.setText(hmo_type);

    }

    @FXML
    private boolean onClickUpdate(ActionEvent event) throws IOException {
        hmo myhmo = new hmo();
        Date date = new Date();
        int theId = Integer.parseInt(id);
        String theDate = date.toString();
        //format for report queries
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        cal.add(Calendar.DATE, 0);
        String hmo_name = name_box.getText();
        String hmo_address = address_box.getText().toUpperCase();
        String hmo_phone = phone_box.getText().toUpperCase();
        String hmo_email = email_box.getText().toUpperCase();
        String hmo_type = type_box.getText().toUpperCase();

        System.out.println("hmo_name is: " + hmo_name);

        if ((hmo_name.trim().length() == 0) || (hmo_name == "") || (hmo_name.trim().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Highlight A hmo Record");

            alert.showAndWait();
            //System.clearProperty(hmo_name);
            return false;
        }
        //myhmo.setId(rs.getString("id"));

        myhmo.setHmo_name(hmo_name);
        myhmo.setDate_time(theDate);
        myhmo.setHmo_address1(hmo_address);
        myhmo.setHmo_phone_1(hmo_phone);
        myhmo.setHmo_email(hmo_email);
        myhmo.setHmo_type(hmo_type);

        ObservableList highlightedhmoRecord, allhmoRecords;

        highlightedhmoRecord = tableHmo.getSelectionModel().getSelectedItems();

        System.out.println("hmo_name is : " + hmo_name);
        System.out.println("the ID IS: " + theId);
        String query = "UPDATE hmo_tbl set "
                + "hmo_address1 ='"
                + hmo_address + "',"
                + "hmo_phone_1 ='"
                + hmo_phone + "',"
                + "hmo_email ='"
                + hmo_email + "',"
                + "hmo_type ='"
                + hmo_type + "',"
                + "date_time ='"
                + theDate + "'"
                + " WHERE hmo_id = "
                + theId;

        System.out.println("updating\n" + query);
        updateStatement(query);

        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Information Dialog");
        alert3.setHeaderText(null);
        alert3.setContentText("Record updated Succesfully.");

        alert3.showAndWait();

        System.out.println("Succesfully Updated");

        Parent TodayReport_page_parent = FXMLLoader.load(getClass().getResource("AllHmos.fxml"));
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
        String hmo_name = name_box.getText();

        if ((hmo_name.trim().length() == 0) || (hmo_name == "") || (hmo_name.trim().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Highlight A Class Room Record");

            alert.showAndWait();
            System.clearProperty(hmo_name);
        }
        //myhmo.setId(rs.getString("id"));

        ObservableList highlightedhmoRecord, allhmoRecords;
        allhmoRecords = tableHmo.getItems();
        highlightedhmoRecord = tableHmo.getSelectionModel().getSelectedItems();
        System.out.println("hmo_name is : " + hmo_name);

        String query = "DELETE FROM hmo_tbl "
                + " WHERE hmo_id = "
                + theId;

        System.out.println("updating\n" + query);
        updateStatement(query);

        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Information Dialog");
        alert3.setHeaderText(null);
        alert3.setContentText("Record Deleted Succesfully.");

        alert3.showAndWait();

        System.out.println("Succesfully Updated");

        Parent TodayReport_page_parent = FXMLLoader.load(getClass().getResource("AllHmos.fxml"));
        Scene TodayReport_page_scene = new Scene(TodayReport_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(TodayReport_page_scene);
        app_stage.show();

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

}
