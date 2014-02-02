/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess;

import com.ACME.dataAcess.DAO.SavingsDAO;
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
private final String SQL_CREATE_SAVINGS="INSERT INTO ACME_BANK.SAVINGS( C_ID, BALANCE)"+ " VALUES ( ?, ?)";
private final String SQL_READ_SAVINGS="SELECT * FROM ACME_BANK.SAVINGS WHERE C_ID = ?";
private final String SQL_GETALL_SAVINGS="SELECT * FROM ACME_BANK.SAVINGS";
private final String SQL_UPDATE_SAVINGS="UPDATE ACME_BANK.SAVINGS SET ACCNUM=?, BALANCE=? WHERE C_ID=?";
private final String SQL_DELETE_SAVINGS="DELTE FROM ACME_BANK.SAVINGS WHERE C_ID=?";
private final String SQL_VIEW_BALANCE="SELECT BALANCE FROM ACME_BANK.SAVINGS WHERE C_ID=?";
private final String SQL_SET_BALANCE="UPDATE  ACME_BANK.SAVINGS SET BALANCE=? WHERE C_ID=?";
    
    public RDBSavingsDAO(Connection dbConnection) {
    }

    @Override
    public void createSavingsAccount(int C_ID) {
        try {
            Savings account=new Savings();

            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_CREATE_SAVINGS);

            prestmnt.setInt(1, account.getC_id());
            prestmnt.setInt(2, account.getBalance());

            prestmnt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Could not open a saving account for customer. "+C_ID);
            ex.printStackTrace();
        }
    }

    @Override
    public Savings readSavingsAccount(int C_ID) {
        SavingsAccount account=null;
        try{
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_READ_SAVINGS);
            
            prestmnt.setInt(1,C_ID);
            
            ResultSet rs=prestmnt.executeQuery();
            
            rs.next();
            
            account=new SavingsAccount(rs.getInt("C_ID"));
            account.setAccNum(rs.getString("ACCNUM"));
            account.setBalance(rs.getInt("BALANCE"));
            
            
            
        }catch(Exception ex){
            System.out.println("Could not find a saving account for customer. "+C_ID);
            ex.printStackTrace();
        }
        return account;
    }

    @Override
    public void updateSavingsAccount(int C_ID) {
         try{
           PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_DELETE_SAVINGS);
           
           prestmnt.setInt(1, C_ID);
           
           prestmnt.executeUpdate();
           
           
       }catch(Exception ex){
           System.out.println("Could not delete a savings account for customer. "+C_ID);
           ex.printStackTrace();
       }
    }

    @Override
    public void deleteSavingsAccount(int C_ID) {
        boolean withdrawflag=false;
        String accNum="S"+C_ID;
        String description="Customer ID: ["+C_ID+"], AccNum: ["+accNum+"]"+" withdrawed "+amount+" on "+System.currentTimeMillis()+" Success: ";
        try{
           SavingsAccount account = readSavingsAccount(C_ID);
           
           
           withdrawflag=account.withdraw(amount);
           if(withdrawflag){
               PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_SET_BALANCE);
               prestmnt.setInt(1, account.getBalance());
               prestmnt.setInt(2, account.getC_ID());
               prestmnt.executeUpdate();
               
               
               
           }else{
               System.out.println("Insufficient fund on Account. "+C_ID);
           }
        }catch(Exception ex){
            System.out.println("Could not withdraw on Account. "+C_ID);
            ex.printStackTrace();
        }finally{
            RDBTransactionsDAO transaction=new RDBTransactionsDAO(dbConnection);
            description+=String.valueOf(withdrawflag);
            transaction.createTranscations(new Transactions(C_ID,accNum,amount,description));
        }
    }

    @Override
    public void withdraw(int C_ID, int amount) {
        boolean withdrawflag=false;
        String accNum="S"+C_ID;
        String description="Customer ID: ["+C_ID+"], AccNum: ["+accNum+"]"+" withdrawed "+amount+" on "+System.currentTimeMillis()+" Success: ";
        try{
           SavingsAccount account = readSavingsAccount(C_ID);
           
           
           withdrawflag=account.withdraw(amount);
           if(withdrawflag){
               PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_SET_BALANCE);
               prestmnt.setInt(1, account.getBalance());
               prestmnt.setInt(2, account.getC_ID());
               prestmnt.executeUpdate();
               
               
               
           }else{
               System.out.println("Insufficient fund on Account. "+C_ID);
           }
        }catch(Exception ex){
            System.out.println("Could not withdraw on Account. "+C_ID);
            ex.printStackTrace();
        }finally{
            RDBTransactionsDAO transaction=new RDBTransactionsDAO(dbConnection);
            description+=String.valueOf(withdrawflag);
            transaction.createTranscations(new Transactions(C_ID,accNum,amount,description));
        }
    }

    @Override
    public void deposit(int C_ID, int amount) {
        String accNum="S"+C_ID;
        boolean deposit=true;
        String description="Customer ID: ["+C_ID+"], AccNum: ["+accNum+"]"+" deposit "+amount+" on "+System.currentTimeMillis()+" Success: ";
        try{
            
           SavingsAccount account = readSavingsAccount(C_ID);
           account.deposit(amount);
               PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_SET_BALANCE);
               prestmnt.setInt(1, account.getBalance());
               prestmnt.setInt(2, account.getC_ID());
               prestmnt.executeUpdate();
          
        }catch(Exception ex){
            System.out.println("Could not deposit on Account. "+C_ID);
            ex.printStackTrace();
        }finally{
            RDBTransactionsDAO transaction=new RDBTransactionsDAO(dbConnection);
            description+=String.valueOf(deposit);
            transaction.createTranscations(new Transactions(C_ID,accNum,amount,description));
        }
    }

    @Override
    public int viewBalance(int C_ID) {
         int balance = -1;
        try{
           PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_VIEW_BALANCE);
           
           prestmnt.setInt(1, C_ID);
           
           ResultSet rs=prestmnt.executeQuery();
           
           rs.next();
           
           balance=rs.getInt("BALANCE");
           
        }catch(Exception ex){
            System.out.println("Could not view balance on a savings account for customer. "+C_ID);
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
   }
    

