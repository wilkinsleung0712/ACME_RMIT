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
    public boolean readSavingsAccountByCustomer(int C_ID);
    public boolean readSavingsAccountByAccNum(int AccNum);
    public Savings getSavingsAccountByAccNum(int AccNum);
    public boolean updateSavingsAccount(Savings savingsAccount);
    public boolean deleteSavingsAccount (int AccNum);
    public void withdraw(int C_ID,int amount);
    public boolean deposit(int C_ID,int amount);
    public int viewBalance(int C_ID);

    public Collection getAllSavingsAccount();
}
