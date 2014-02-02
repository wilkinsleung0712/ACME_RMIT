/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess.DAO;

import com.ACME.dataAcess.Savings.Savings;
import java.util.Collection;

/**
 *
 * @author WEIQIANGLIANG
 */
public interface SavingsDAO {
    public void createSavingsAccount(int C_ID);
    public Savings readSavingsAccount(int C_ID);
    public void updateSavingsAccount(int C_ID);
    public void deleteSavingsAccount (int C_ID);
    public void withdraw(int C_ID,int amount);
    public void deposit(int C_ID,int amount);
    public int viewBalance(int C_ID);

    public Collection getAllSavingsAccount();
}
