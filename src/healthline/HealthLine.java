/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthline;

import java.io.File;
import java.sql.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Eche Michael
 */
public class HealthLine extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        checkPatient_tbl();
        checkAppointment_tbl();
        checkDoctors_tbl();
        checkhmos_tbl();
        //checkIncomeAmount_tbl();
        checkExpAmount_tbl();
        //checkusersTables();
        createDownloadsDir();
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void checkPatient_tbl() {

        String sqlCreateExpItems = "CREATE TABLE IF NOT EXISTS Patient_tbl ("
                + "patient_id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + "first_name	TEXT,"
                + "last_name	TEXT,"
                + "address	TEXT,"
                + "address2	TEXT,"
                + "dob	TEXT,"
                + "age	TEXT,"
                + "gender	TEXT,"
                + "phone	TEXT,"
                + "email	TEXT,"
                + "hmo	TEXT,"
                + "type	TEXT,"
                + "patient_date	TEXT"
                + ")";

        //String CleanExpTbl = "DELETE FROM Patient_tbl WHERE itemName = \"\"";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            stmt = conn.createStatement();
            stmt.execute(sqlCreateExpItems);
            //stmt.execute(CleanExpTbl);
//		conn.commit();
//		conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private void checkAppointment_tbl() {

        String sqlCreateExpItems = "CREATE TABLE IF NOT EXISTS Appointment_tbl ("
                + "appointment_id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + "patient_id	TEXT,"
                + "full_name	TEXT,"
                + "doctor_id	TEXT,"
                + "appointment_date	TEXT,"
                + "appointment_name	TEXT,"
                + "symptoms	TEXT,"
                + "amount	TEXT,"
                + "treatment	TEXT,"
                + "medication	TEXT,"
                + "diagnosis	TEXT,"
                + "doctor_treating	TEXT,"
                + "notes	TEXT,"
                + "txnDay	TEXT,"
                + "txnMonth	TEXT,"
                + "txnYear	TEXT,"
                + "type 	TEXT,"
                + "room         TEXT,"
                 + "invoiceNo	TEXT,"
                + "date_time	TEXT"
                + ")";

        // String CleanExpTbl = "DELETE FROM expItems WHERE itemName = \"\"";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            stmt = conn.createStatement();
            stmt.execute(sqlCreateExpItems);
            //stmt.execute(CleanExpTbl);
//		conn.commit();
//		conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private void checkDoctors_tbl() {
        String sqlCreateExpItems = "CREATE TABLE IF NOT EXISTS Doctors_tbl ("
                + "doctor_id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + "first_name	TEXT,"
                + "last_name	TEXT,"
                + "address	TEXT,"
                + "phone	TEXT,"
                + "email	TEXT,"
                 + "gender	TEXT,"
                + "type	TEXT,"
                + "dotor_date	TEXT"
                + ")";

        //String CleanExpTbl = "DELETE FROM expItems WHERE itemName = \"\"";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            stmt = conn.createStatement();
            stmt.execute(sqlCreateExpItems);
            //stmt.execute(CleanExpTbl);
            //		conn.commit();
            //		conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private void checkhmos_tbl() {
        String sqlCreateExpItems = "CREATE TABLE IF NOT EXISTS hmo_tbl ("
                + "hmo_id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + "hmo_name	TEXT,"
                + "hmo_address1	TEXT,"
                + "hmo_reg_no	TEXT,"
                + "hmo_phone_1	TEXT,"
                + "hmo_email	TEXT,"
                + "hmo_type 	TEXT,"
                + "date_time	TEXT"
                + ")";

        //String CleanExpTbl = "DELETE FROM expItems WHERE itemName = \"\"";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            stmt = conn.createStatement();
            stmt.execute(sqlCreateExpItems);
            //stmt.execute(CleanExpTbl);
            //		conn.commit();
            //		conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private void checkusersTables() {
        String sqlDropUsers = "drop table if exists users";
        String sqlCreateUsers = "CREATE TABLE IF NOT EXISTS users ("
                + "username	TEXT,"
                + "password	TEXT,"
                + "PRIMARY KEY(username)"
                + ")";
        String sqlInsertUsers = "INSERT INTO users (username,password) VALUES ('admin','admin')";
        System.out.println(sqlInsertUsers);
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            stmt = conn.createStatement();
            stmt.execute(sqlDropUsers);
            stmt.execute(sqlCreateUsers);
            stmt.execute(sqlInsertUsers);
            stmt.close();
            //conn.commit();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private void checkIncomeAmount_tbl() {
        String sqlCreateExpItems = "CREATE TABLE IF NOT EXISTS IncomeAmount_tbl ("
                + "amount_id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + "patient_id	TEXT,"
                + "doctor_id	TEXT,"
                + "amount_date	TEXT,"
                + "amount_name	TEXT,"
                + "amount_details	TEXT,"
                + "amount	TEXT,"
                + "txnDay	TEXT,"
                + "txnMonth	TEXT,"
                + "txnYear	TEXT,"
                + "notes	TEXT"
                + ")";

        // String CleanExpTbl = "DELETE FROM expItems WHERE itemName = \"\"";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            stmt = conn.createStatement();
            stmt.execute(sqlCreateExpItems);
            //stmt.execute(CleanExpTbl);
//		conn.commit();
//		conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private void checkExpAmount_tbl() {
        String sqlCreateExpItems = "CREATE TABLE IF NOT EXISTS ExpAmount_tbl ("
                + "amount_id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + "amount_name	TEXT,"
                + "amount_details	TEXT,"
                + "amount_date	TEXT,"
                + "txnDay	TEXT,"
                + "txnMonth	TEXT,"
                + "txnYear	TEXT,"
                + "formated_date	TEXT,"
                + "amount	TEXT"
                + ")";

        // String CleanExpTbl = "DELETE FROM expItems WHERE itemName = \"\"";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:dbhealthline.db");
            stmt = conn.createStatement();
            stmt.execute(sqlCreateExpItems);
            //stmt.execute(CleanExpTbl);
//		conn.commit();
//		conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

	public void createDownloadsDir() {
        try {
            String directoryName = "C:\\Downloads\\";
            directoryName = directoryName.replace("\\", "/");
            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdir();
                System.out.println("Directory does not exist");
                System.out.println("Directory has been created");
            } else {
                System.out.println("Directory existe");
                System.out.println("Doing nothing");
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

}
