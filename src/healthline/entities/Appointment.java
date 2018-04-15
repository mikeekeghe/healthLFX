/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthline.entities;

/**
 *
 * @author Eche Michael
 */
public class Appointment {

    private String appointment_id;
    private String full_name;
    private String doctor_id;
    private String appointment_date;
    private String appointment_name;
    private String amount;
    private String treatment;
    private String medication;
    private String diagnosis;
    private String notes;
    private String type;
    private String room;
    private String date_time;
    private String doctor_treating;
    private String invoiceNo;

    public Appointment(String appointment_id, String full_name, String doctor_id, String appointment_date, String appointment_name, String amount, String treatment, String medication, String diagnosis, String notes, String type, String room, String date_time, String doctor_treating, String invoiceNo) {
        this.appointment_id = appointment_id;
        this.full_name = full_name;
        this.doctor_id = doctor_id;
        this.appointment_date = appointment_date;
        this.appointment_name = appointment_name;
        this.amount = amount;
        this.treatment = treatment;
        this.medication = medication;
        this.diagnosis = diagnosis;
        this.notes = notes;
        this.type = type;
        this.room = room;
        this.date_time = date_time;
        this.doctor_treating = doctor_treating;
        this.invoiceNo = invoiceNo;
    }

  
    public Appointment() {
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getAppointment_name() {
        return appointment_name;
    }

    public void setAppointment_name(String appointment_name) {
        this.appointment_name = appointment_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDoctor_treating() {
        return doctor_treating;
    }

    public void setDoctor_treating(String doctor_treating) {
        this.doctor_treating = doctor_treating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
    
    

}
