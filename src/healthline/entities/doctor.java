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
public class doctor {

    private String doctor_id;
    private String name;
    private String first_name;
    private String last_name;
    private String address;
    private String age;
    private String dob;
    private String phone;
    private String email;
    private String hmo;
    private String type;
    private String gender;
    private String dotor_date;

    public doctor() {
    }

    public doctor(String doctor_id, String name, String first_name, String last_name, String address, String age, String dob, String phone, String email, String hmo, String type, String gender, String dotor_date) {
        this.doctor_id = doctor_id;
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.age = age;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.hmo = hmo;
        this.type = type;
        this.gender = gender;
        this.dotor_date = dotor_date;
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

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
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

    public String getDotor_date() {
        return dotor_date;
    }

    public void setDotor_date(String dotor_date) {
        this.dotor_date = dotor_date;
    }

}
