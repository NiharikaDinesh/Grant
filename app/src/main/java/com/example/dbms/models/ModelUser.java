package com.example.dbms.models;

public class ModelUser {
    String fname, bloodtype, contactNo, lname, email, address, date;
    private   String key;
    private String imageUrl;


    public ModelUser() {

    }

    public ModelUser(String fname, String bloodtype, String contactNo, String lname, String email, String address, String date, String key, String imageUrl) {
        this.fname = fname;
        this.bloodtype = bloodtype;
        this.contactNo = contactNo;
        this.lname = lname;
        this.email = email;
        this.address = address;
        this.date = date;
        this.key = key;
        this.imageUrl = imageUrl;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public  String getKey() {
        return key;
    }

    public  void setKey(String key) {
       this. key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public  void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
