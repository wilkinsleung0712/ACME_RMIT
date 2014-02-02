/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess.Employee;

import java.sql.Timestamp;

/**
 *
 * @author WEIQIANGLIANG
 */
public class Employee {
    private int e_id;
    private String password;
    private String firstname;
    private String lastname;
    private Timestamp joinedTime;

    public Employee() {
    }

  

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
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
    
    
}
