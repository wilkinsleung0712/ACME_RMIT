/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess;

import com.ACME.dataAcess.DAO.SavingsDAO;
import com.ACME.dataAcess.DAO.TransactionsDAO;
import com.ACME.dataAcess.Savings.Savings;
import com.ACME.dataAcess.Transactions.Transactions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author WEIQIANGLIANG
 */
public class RDBSavingsDAO implements SavingsDAO{
private  Connection dbConnection=null;
private final String SQL_CREATE_SAVINGS="INSERT INTO ACME_BANK.SAVINGS (C_ID)"+"VALUES (?)";
private final String SQL_READ_SAVINGS="SELECT * FROM ACME_BANK.SAVINGS WHERE C_ID = ?";
private final String SQL_READ_SAVINGS_BY_ACCNUM="SELECT * FROM ACME_BANK.SAVINGS WHERE ACCNUM = ?";
private final String SQL_READ_SAVINGS_BY_C_ID="SELECT * FROM ACME_BANK.SAVINGS WHERE C_ID = ?";
private final String SQL_GETALL_SAVINGS="SELECT * FROM ACME_BANK.SAVINGS";
private final String SQL_UPDATE_SAVINGS="UPDATE ACME_BANK.SAVINGS SET ACCNUM=?, BALANCE=? WHERE ACCNUM=?";
private final String SQL_DELETE_SAVINGS="DELTE FROM ACME_BANK.SAVINGS WHERE ACCNUM=?";
private final String SQL_VIEW_BALANCE="SELECT BALANCE FROM ACME_BANK.SAVINGS WHERE ACCNUM=?";
private final String SQL_SET_BALANCE="UPDATE  ACME_BANK.SAVINGS SET BALANCE=? WHERE ACCNUM=?";
//constraint
private final boolean SUCCESS=true;
private final boolean FAIL=false;
private final int MAXIMUM_SAVINGS_ACCOUNT=2;

    public RDBSavingsDAO(Connection dbConnection) {
         this.dbConnection = dbConnection; 
    }

    @Override
    public void createSavingsAccount(int C_ID) {
        int numbersOfSavingsAccount=countSavingsAccountByCId(C_ID);
        try {
            if(numbersOfSavingsAccount<MAXIMUM_SAVINGS_ACCOUNT){
                Savings account=new Savings();

                 PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_CREATE_SAVINGS);

                 prestmnt.setInt(1, C_ID);
            
                 prestmnt.executeUpdate();
            }else{
                System.out.println("Could not create any more saving account on customer:  ["+C_ID+"] as it already has ["+numbersOfSavingsAccount+"]");
          
            }
            
        } catch (SQLException ex) {
            System.out.println("Could not open a saving account for customer. "+C_ID);
            ex.printStackTrace();
        }
    }

    @Override
    public boolean readSavingsAccountByCustomer(int C_ID) {
         boolean savingsExist=FAIL;
        try{
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_READ_SAVINGS);
            
            prestmnt.setInt(1,C_ID);
            
            ResultSet rs=prestmnt.executeQuery();
            
            if(rs.next()){
                savingsExist=SUCCESS;
            }
        }catch(SQLException ex){
            System.out.println("Error find a saving account for customer. "+C_ID);
            ex.printStackTrace();
        }
        return savingsExist;
    }

    @Override
    public boolean readSavingsAccountByAccNum(int AccNum) {
         boolean savingsExist=FAIL;
        try{
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_READ_SAVINGS_BY_ACCNUM);
            
            prestmnt.setInt(1,AccNum);
            
            ResultSet rs=prestmnt.executeQuery();
            
            if(rs.next()){
                savingsExist=SUCCESS;
            }
        }catch(SQLException ex){
            System.out.println("Error find a saving account for customer. "+AccNum);
            ex.printStackTrace();
        }
        return savingsExist;
    }
    
    
    @Override
    public Savings getSavingsAccountByAccNum(int AccNum){
        Savings account=null;
         try{
             if(readSavingsAccountByAccNum(AccNum)){
                 PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_READ_SAVINGS_BY_ACCNUM);

                    prestmnt.setInt(1,AccNum);

                    ResultSet rs=prestmnt.executeQuery();
                    if(rs.next()){
                        account=new Savings();
                        account.setAccNum(AccNum);
                        account.setBalance(rs.getInt("Balance"));
                        account.setC_id(rs.getInt("C_ID"));
                    }
             }
        }catch(SQLException ex){
            System.out.println("Error find a saving account for customer. "+AccNum);
            ex.printStackTrace();
        }
         return account;
    }
    
    @Override
    public boolean updateSavingsAccount(Savings savingsAccount) {
         try{
             if(readSavingsAccountByAccNum(savingsAccount.getAccNum())){
                  PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_UPDATE_SAVINGS);
                  prestmnt.setInt(1, savingsAccount.getAccNum());
                  prestmnt.setInt(2, savingsAccount.getBalance());
                  prestmnt.setInt(3, savingsAccount.getC_id());
                  prestmnt.executeUpdate();
                  return SUCCESS;
             }
          }catch(Exception ex){
           System.out.println("Could not delete a savings account for customer. ");
           ex.printStackTrace();
       }
         return FAIL;
    }

    @Override
    public boolean deleteSavingsAccount(int AccNum) {
        try{
            if(readSavingsAccountByAccNum(AccNum)){
                 PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_DELETE_SAVINGS);
                 prestmnt.setInt(1, AccNum);
                  prestmnt.executeUpdate();
                  return SUCCESS;
             }
       }catch(Exception ex){
           System.out.println("Could not delete a savings account for customer. "+AccNum);
           ex.printStackTrace();
       }
            return FAIL;
    }

    @Override
    public void withdraw(int AccNum, int amount) {
        boolean withdrawflag=FAIL;
        String description="AccNum: ["+AccNum+"]"+" withdrawed "+amount+" on "+System.currentTimeMillis()+" Result: ";
        try{
           Savings account = getSavingsAccountByAccNum(AccNum);
           withdrawflag=account.withDraw(amount);
           if(withdrawflag){
               PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_SET_BALANCE);
               prestmnt.setInt(1, account.getBalance());
               prestmnt.setInt(2, account.getAccNum());
               prestmnt.executeUpdate();
               
           }else{
               System.out.println("Insufficient fund on Account. "+AccNum);
           }
        }catch(Exception ex){
            System.out.println("Could not withdraw on Account. "+AccNum);
            ex.printStackTrace();
        }finally{
            TransactionsDAO transaction=new RDBTransactionsDAO(dbConnection);
            description+=String.valueOf(withdrawflag);
           // transaction.createTranscations(new Transactions(C_ID,accNum,amount,description));
        }
    }

    @Override
    public boolean deposit(int AccNum, int amount) {
        boolean deposit=FAIL;
        String description=" AccNum: ["+AccNum+"]"+" deposit "+amount+" on "+System.currentTimeMillis()+" Success: ";
        try{
            if(readSavingsAccountByAccNum(AccNum)){
                Savings account = getSavingsAccountByAccNum(AccNum);
                account.deposit(amount);
               PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_SET_BALANCE);
               prestmnt.setInt(1, account.getBalance());
               prestmnt.setInt(2, account.getAccNum());
               prestmnt.executeUpdate();
               deposit=SUCCESS;
               return deposit;
            }
        }catch(Exception ex){
            System.out.println("Could not deposit on Account. "+AccNum);
            ex.printStackTrace();
        }finally{
            RDBTransactionsDAO transaction=new RDBTransactionsDAO(dbConnection);
            description+=String.valueOf(deposit);
           // transaction.createTranscations(new Transactions(C_ID,accNum,amount,description));
        }
         return deposit;
    }

    @Override
    public int viewBalance(int AccNum) {
         int balance = -1;
        try{
           PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_VIEW_BALANCE);
           
           prestmnt.setInt(1, AccNum);
           
           ResultSet rs=prestmnt.executeQuery();
           
           rs.next();
           
           balance=rs.getInt("BALANCE");
           
        }catch(Exception ex){
            System.out.println("Could not view balance on a savings account for customer. "+AccNum);
            ex.printStackTrace();
        }
         return balance;
    }

    @Override
    public Collection getAllSavingsAccount() {
        Collection<String> customerList=new HashSet<>();
        try {
                PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_GETALL_SAVINGS);
                ResultSet rs=prestmnt.executeQuery();
                ResultSetMetaData metaData = rs.getMetaData();
                int columns = metaData.getColumnCount();
                String data="";
                while(rs.next()){
                    data="";
                    for(int i=1;i<=columns;i++){
                       data+=rs.getObject(i).toString()+"\t";
                    }
                    customerList.add(data);
                    
                }
       
             } catch (SQLException ex) {
                System.out.println("Could not get all  savings account.");
                ex.printStackTrace();
         }
        return customerList;
    }
    
    
    public int countSavingsAccountByCId(int C_ID){
        int numbersOfSavingsAccount=0;
         try{
               PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_READ_SAVINGS_BY_C_ID);
               prestmnt.setInt(1, C_ID);
               ResultSet rs=prestmnt.executeQuery();
               while(rs.next()){
                   numbersOfSavingsAccount++;
               }
            }
        catch(SQLException ex){
            System.out.println("Could not create any more saving account on customer:  ["+C_ID+"] as it already has ["+numbersOfSavingsAccount+"]");
            ex.printStackTrace();
        }
         return numbersOfSavingsAccount;
    }
   }
    

