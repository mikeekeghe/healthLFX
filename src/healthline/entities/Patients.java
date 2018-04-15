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
public class Patients {

    private String patient_id;
    private String symptoms;
    private String treatment;
    private String first_name;
    private String last_name;
    private String address;
    private String age;
    private String gender;
    private String dob;
    private String phone;
    private String email;
    private String hmo;
    private String type;
    private String patient_date;
    private String name;

    public Patients() {
    }

    public Patients(String patient_id, String symptoms, String treatment, String first_name, String last_name, String address, String age, String gender, String dob, String phone, String email, String hmo, String type, String patient_date, String name) {
        this.patient_id = patient_id;
        this.symptoms = symptoms;
        this.treatment = treatment;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.hmo = hmo;
        this.type = type;
        this.patient_date = patient_date;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

   

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHmo() {
        return hmo;
    }

    public void setHmo(String hmo) {
        this.hmo = hmo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPatient_date() {
        return patient_date;
    }

    public void setPatient_date(String patient_date) {
        this.patient_date = patient_date;
    }

}
