/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess.Transactions;

import java.sql.Timestamp;

/**
 *
 * @author WEIQIANGLIANG
 */
public class Transactions {
    private int id;
    private int accNum;
    private int t_id;
    private String description;
    private int amount;
    private String type;
    private boolean result;
    private Timestamp loggedTime;
    public Transactions() {
    }
    
    public Transactions(int accNum, int amount,String description,String type,boolean result){
        this.accNum=accNum;
        this.description=description;
        this.amount=amount;
        this.result=result;
        this.type=type;
    }

    public Timestamp getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(Timestamp loggedTime) {
        this.loggedTime = loggedTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAccNum() {
        return accNum;
    }

    public void setAccNum(int accNum) {
        this.accNum = accNum;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString(){
        return "Transactions: [id=T"+this.t_id+"] [AccNum="+this.getAccNum()+"] [Amount="+this.getAmount()+"] [Type="+this.getType()+"] [Description="+this.getDescription()+"]";
    }
}
