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
public class hmo {
    
    private String hmo_id;
    private String hmo_name;
    private String hmo_address1;
    private String hmo_reg_no;
    private String hmo_phone_1;
    private String hmo_email;
    private String hmo_type;
        private String date_time;


    public hmo() {
    }

    public hmo(String hmo_id, String hmo_name, String hmo_address1, String hmo_reg_no, String hmo_phone_1, String hmo_email, String hmo_type, String date_time) {
        this.hmo_id = hmo_id;
        this.hmo_name = hmo_name;
        this.hmo_address1 = hmo_address1;
        this.hmo_reg_no = hmo_reg_no;
        this.hmo_phone_1 = hmo_phone_1;
        this.hmo_email = hmo_email;
        this.hmo_type = hmo_type;
        this.date_time = date_time;
    }

    public String getHmo_id() {
        return hmo_id;
    }

    public void setHmo_id(String hmo_id) {
        this.hmo_id = hmo_id;
    }

    public String getHmo_name() {
        return hmo_name;
    }

    public void setHmo_name(String hmo_name) {
        this.hmo_name = hmo_name;
    }

    public String getHmo_address1() {
        return hmo_address1;
    }

    public void setHmo_address1(String hmo_address1) {
        this.hmo_address1 = hmo_address1;
    }

    public String getHmo_reg_no() {
        return hmo_reg_no;
    }

    public void setHmo_reg_no(String hmo_reg_no) {
        this.hmo_reg_no = hmo_reg_no;
    }

    public String getHmo_phone_1() {
        return hmo_phone_1;
    }

    public void setHmo_phone_1(String hmo_phone_1) {
        this.hmo_phone_1 = hmo_phone_1;
    }

    public String getHmo_email() {
        return hmo_email;
    }

    public void setHmo_email(String hmo_email) {
        this.hmo_email = hmo_email;
    }

    public String getHmo_type() {
        return hmo_type;
    }

    public void setHmo_type(String hmo_type) {
        this.hmo_type = hmo_type;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

   

}
