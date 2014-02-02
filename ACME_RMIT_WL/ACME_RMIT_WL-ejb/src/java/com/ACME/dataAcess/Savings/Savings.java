/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess.Savings;

/**
 *
 * @author WEIQIANGLIANG
 */
public class Savings {
    private int accNum;
    private int balance;
    private int c_id;

    public Savings() {
    }

    public int getAccNum() {
        return accNum;
    }

    public void setAccNum(int accNum) {
        this.accNum = accNum;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }
    
    public boolean withDraw(int amount){
        if(amount>balance){
            return false;
        }
        this.balance-=amount;
        return true;
    }
    
    public void deposit(int amount){
        this.balance+=amount;
    }
}
