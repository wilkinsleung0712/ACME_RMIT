/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess;

import com.ACME.dataAcess.DAO.TransactionsDAO;
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
public class RDBTransactionsDAO implements TransactionsDAO{
   
private  Connection dbConnection=null;
private final String SQL_CREATE_TRANSACTIONS="INSERT INTO ACME_BANK.TRANSACTIONS(ACCNUM,AMOUNT,DESCRIPTION,TYPE,RESULT )"+ " VALUES (?,?, ?, ?,?)";
private final String SQL_READ_TRANSACTIONS="SELECT * FROM ACME_BANK.TRANSACTIONS WHERE AccNum = ?";
private final String SQL_UPDATE_TRANSACTIONS="UPDATE ACME_BANK.TRANSACTIONS SET AMOUNT=? DESCRIPTION=? TYPE=? RESULT=? WHERE T_ID=?";
private final String SQL_DELETE_TRANSACTIONS="DELTE FROM ACME_BANK.TRANSACTIONS WHERE T_ID=?";
private final String SQL_GETALL_TRANSACTIONS="SELECT * FROM ACME_BANK.TRANSACTIONS";
private final String SQL_GETALLBY_ACCNUM_TRANSACTIONS="SELECT * FROM ACME_BANK.TRANSACTIONS WHERE ACCNUM LIKE ?";




    public RDBTransactionsDAO(Connection dbConnection) {
        this.dbConnection=dbConnection;
    }

    @Override
    public boolean createTranscations(Transactions transaction) {
         try{
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_CREATE_TRANSACTIONS);
            
            prestmnt.setInt(1, transaction.getAccNum());
            prestmnt.setInt(2, transaction.getAmount());
            prestmnt.setString(3, transaction.getDescription());
            prestmnt.setString(4, transaction.getType());
            prestmnt.setBoolean(5, transaction.getResult());
            
            prestmnt.executeUpdate();
            
            return true;
            
        }catch(Exception ex){
            System.out.println("Could not record a transaction. "+transaction.getT_id());
            ex.printStackTrace();
            return false; 
        }
    }

    @Override
    public Collection getAllTranscations() {
        Collection<String> transactionsList=new HashSet<>();
        try{
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_GETALL_TRANSACTIONS);
            
            ResultSet rs=prestmnt.executeQuery();
                ResultSetMetaData metaData = rs.getMetaData();
                int columns = metaData.getColumnCount();
                String data="";
                while(rs.next()){
                    data="";
                    for(int i=1;i<=columns;i++){
                       data+=rs.getObject(i).toString()+"\t";
                    }
                    transactionsList.add(data);
                    
                    }
                
            }catch(SQLException ex){
            System.out.println("Could not record a transaction. ");
            ex.printStackTrace();
             
        }
        return transactionsList;
    
    }

    @Override
    public boolean updateTransactions(Transactions transaction) {
        try{
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_UPDATE_TRANSACTIONS);
            
            prestmnt.setInt(1, transaction.getAmount());
            prestmnt.setString(2, transaction.getDescription());
            prestmnt.setString(3, transaction.getType());
            prestmnt.setBoolean(4, transaction.getResult());
            prestmnt.setInt(5, transaction.getT_id());
            
            prestmnt.executeUpdate();
            
            return true;
            
        }catch(Exception ex){
            System.out.println("Could not update record a transaction. "+transaction.getT_id());
            ex.printStackTrace();
            return false; 
        }
    }

    @Override
    public boolean deleteTransactions(int T_ID) {
        try{
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_DELETE_TRANSACTIONS);
            
            prestmnt.setInt(1, T_ID);
            
            prestmnt.executeUpdate();
            
            return true;
            
        }catch(Exception ex){
            System.out.println("Could not delete record a transaction. "+T_ID);
            ex.printStackTrace();
            return false; 
        }
    
    }

    
    /*
    Geting a collection of transaction histroy for a particular account in database.
    */
    
    @Override
    public Collection getTranscationsByAccNum(int AccNum) {
        Collection<String> transactionsList=new HashSet<>();
        try{
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_GETALLBY_ACCNUM_TRANSACTIONS);
            
            prestmnt.setInt(1, AccNum);
            
            ResultSet rs=prestmnt.executeQuery();
                ResultSetMetaData metaData = rs.getMetaData();
                int columns = metaData.getColumnCount();
                String data="";
                while(rs.next()){
                    data="";
                    for(int i=1;i<=columns;i++){
                       data+=rs.getObject(i).toString()+"\t";
                    }
                    transactionsList.add(data);
                    
                }
            
        }catch(SQLException ex){
            System.out.println("Could not get collection of records base on Customer ID. "+AccNum);
            ex.printStackTrace();
             
        }
        return transactionsList;
    
    }
    
    
    
    
}
