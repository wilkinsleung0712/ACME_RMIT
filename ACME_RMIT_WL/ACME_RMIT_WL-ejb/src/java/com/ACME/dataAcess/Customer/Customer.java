/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess.Customer;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author WEIQIANGLIANG
 */
public class Customer {
    private int c_id;
    private String firstname;
    private String lastname;
    private Timestamp joinedTime;
    private Date dob;
    private String address;
    private String password;
    private String type;

    public Customer() {
    }

    public int getC_id() {
        return c_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Timestamp getJoinedTime() {
        return joinedTime;
    }

    public void setJoinedTime(Timestamp joinedTime) {
        this.joinedTime = joinedTime;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
