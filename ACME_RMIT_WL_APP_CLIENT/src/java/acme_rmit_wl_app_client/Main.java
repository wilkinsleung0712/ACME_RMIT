/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package acme_rmit_wl_app_client;

import com.ACME.sessionbeans.customerbeans.customerSessionBeanRemote;
import com.ACME.sessionbeans.employeebeans.employeeSessionBeanRemote;
import com.ACME.sessionbeans.savingsbeans.savingsSessionBeanRemote;
import com.ACME.sessionbeans.transactionsbeans.transactionsSessionBeanRemote;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import javax.ejb.EJB;

/**
 *
 * @author WEIQIANGLIANG
 */
public class Main {
    @EJB
    private static transactionsSessionBeanRemote transactionsBean;
    @EJB
    private static savingsSessionBeanRemote savingsBean;
    @EJB
    private static employeeSessionBeanRemote employeeBean;
    @EJB
    private static customerSessionBeanRemote customerBean;
    
     
    private static int menuSelection;
    private static Collection<String> customerCollections;
    private static Collection<String> accountCollections;
    private static Collection<String> transactionCollections;
    private static boolean quit=false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       //customerBean.createCustomer("wilkins", "leung", new Date(1988,07,12), "unit 5, 9-11 kent road boxhill, VICTORIA");
       //savingsBean.createSavingsAccount(2);
        do{
            // System.out.println("Cart:");
           // List<String> cartItems = shoppingCart.getItemsInCart();
            
            //print out menu.
            System.out.println();
            System.out.println("1: Create a Customer");
            System.out.println("2: Open a Savings account");
            System.out.println("3: Make deposit into Savings account");
            System.out.println("4: Make withdrawal from Savings account");
            System.out.println("5: View balance of Savings account");
            System.out.println("6: Log Out");
            menuSelection=getSelection(0,9);
            if (menuSelection == 1 ) {
                //shoppingCart.addItemToCart(menuSelection);
                String firstName=getUserInput("Please enter FIRST NAME:");
                if(quit){
                    continue;
                }
                String lastName=getUserInput("Please enter LAST NAME:");
                if(quit){
                    continue;
                }
                java.sql.Date customerDOB=getUserDOB("Please enter DOB:");
                if(quit){
                    continue;
                }
                String address=getUserInput("Please enter ADDRESS:");
                 if(quit){
                    continue;
                }
                 
                if(customerBean.createCustomer(firstName, lastName, customerDOB, address)){
                    System.out.println("SERVER:  "+"Customer [ FIRSTNAME="+firstName+" LASTNAME="+lastName+" DOB="+customerDOB+" ] has inserted into ACME_BANK.CUSTOMER Database successful.");
                }else{
                    System.out.println("SERVER:  "+"Customer [ FIRSTNAME="+firstName+" LASTNAME="+lastName+" DOB="+customerDOB+" ] has inserted into ACME_BANK.CUSTOMER Database failure.");
                }

            }
            if (menuSelection == 2) {
                
                customerCollections= customerBean.getAllCustomer();
                printCollection(customerCollections,"NO CUSTOMERS RECORDS ON ACME_BANK_CUSTOMER DATABASE. ","RECORDS ON ACME_BANK_CUSTOMER DATABASE: ");
                
              
                System.out.println();
                System.out.println("Please select a Customer to open savings account: ");
                int customer_C_ID=getSelection(0,99);
                if(quit){
                    continue;
                }
                if(savingsBean.createSavingsAccount(customer_C_ID)){
                    System.out.println("SERVER:  "+"Savings Account on Customer [ CUSTOMER ID="+customer_C_ID+" ] has inserted into ACME_BANK.CUSTOMER Database successful.");
                }else{
                    System.out.println("SERVER:  "+"Savings Account on Customer [ CUSTOMER ID="+customer_C_ID+" ] has inserted into ACME_BANK.CUSTOMER Database failure.");
                }
                
               // C_ID=customerBean.readCustomer("wilkins", "leung", new Date(1988,7,12), "unit 5, 9-11 kent road boxhill, VICTORIA");
               // 
               // System.out.println("Customer: [ID: "+C_ID);
               
            }
            if (menuSelection == 3) {
                accountCollections= savingsBean.getAllSavingsAccount();
                printCollection(accountCollections,"NO SAVINGS ACCOUNTS RECORDS ON ACME_BANK_SAVINGS DATABASE. ","RECORDS ON ACME_BANK_CUSTOMER DATABASE: ");
                //printTransactionsByAccount(accountCollections,"NO TRANSACTIONS HISTORY .","TRANSACTIONS HISTROY TABLE");
                 
                System.out.println();
                System.out.println("Please select a customer : ");
                int C_ID=getSelection(0,99);
                if(quit){
                    break;
                }
                System.out.println("Please select an account  :[Customer ID="+C_ID+"]");
                printCollection(savingsBean.getSavingsAccountByCId(C_ID),"NO SAVINGS ACCOUNTS RECORDS ON ACME_BANK_SAVINGS DATABASE. ","AccNumber \t Balance \t Cutomer_ID \t Type");
                
                System.out.println();
                System.out.println("Please select an Account : ");
                int AccNum=getSelection(0,99); 
                if(quit){
                    break;
                }
                System.out.println();
                
                System.out.println("Please enter amount to deposit on Account : ");
                int amount=getSelection(0,10000); 
                if(quit){
                    break;
                }
                transactionCollections=transactionsBean.getTranscationsByAccNum(AccNum);
                printCollection(transactionCollections,"NO HISTORY TRANSACTIONS RECORDS ON ACME_BANK_TRANSACTIONS DATABASE. ","RECORDS ON ACME_BANK_TRANSACTIONS DATABASE: ");
                
                
                System.out.println("Please insert an amount to deposit from the selected account : ");
                //int amount=getAmount(0,100000);
                
                boolean result=savingsBean.deposit(AccNum, amount);
                if(result){
                    System.out.println("SERVER:  "+"Savings Account on Customer [ CUSTOMER ID="+C_ID+" with AMOUNT="+amount+" ] has perform DEPOSITE on ACME_BANK.SAVINGS Database successful.");
                }else{
                    System.out.println("SERVER:  "+"Savings Account on Customer [ CUSTOMER ID="+C_ID+" with AMOUNT="+amount+" ] has perform DEPOSITE on ACME_BANK.SAVINGS Database failure.");
                }
              
                
            }
            if (menuSelection == 4) {
                accountCollections= savingsBean.getAllSavingsAccount();
                printCollection(accountCollections,"NO SAVINGS ACCOUNTS RECORDS ON ACME_BANK_SAVINGS DATABASE. ","RECORDS ON ACME_BANK_CUSTOMER DATABASE: ");
                //printTransactionsByAccount(accountCollections,"NO TRANSACTIONS HISTORY .","TRANSACTIONS HISTROY TABLE");
                System.out.println();
                System.out.println("Please select a customer : ");
                int C_ID=getSelection(0,99);
                if(quit){
                    break;
                }
                
                System.out.println("Please select an account  :[Customer ID="+C_ID+"]");
                printCollection(savingsBean.getSavingsAccountByCId(C_ID),"NO SAVINGS ACCOUNTS RECORDS ON ACME_BANK_SAVINGS DATABASE. ","RECORDS ON ACME_BANK_CUSTOMER DATABASE: ");
                System.out.println();
                System.out.println("Please select an Account : ");
                int AccNum=getSelection(0,99); 
                if(quit){
                    break;
                }
                System.out.println();
                System.out.println("Please enter amount to withdraw on Account : ");
                int amount=getSelection(0,10000); 
                if(quit){
                    break;
                }
                
                transactionCollections=transactionsBean.getTranscationsByAccNum(AccNum);
                printCollection(transactionCollections,"NO HISTORY TRANSACTIONS RECORDS ON ACME_BANK_TRANSACTIONS DATABASE. ","RECORDS ON ACME_BANK_TRANSACTIONS DATABASE: ");
                
                
                boolean result=savingsBean.withdraw(AccNum, amount);
                if(result){
                    System.out.println("SERVER:  "+"Savings Account on Customer [ CUSTOMER ID="+C_ID+" with AMOUNT="+amount+" ] has perform DEPOSITE on ACME_BANK.SAVINGS Database successful.");
                }else{
                    System.out.println("SERVER:  "+"Savings Account on Customer [ CUSTOMER ID="+C_ID+" with AMOUNT="+amount+" ] has perform DEPOSITE on ACME_BANK.SAVINGS Database failure.");
                }
              
              
              
            }
            if (menuSelection == 5) {
               accountCollections=savingsBean.getAllSavingsAccount();
               printTransactionsByAccount(accountCollections,"NO SAVINGS ACCOUNT RECORDS ON ACME_BANK_SAVINGS DATABASE. ","RECORDS ON ACME_BANK_SAVINGS DATABASE: ");
               
            
            }

        }while(menuSelection!=6);
       
    }
    
    
    /*
    print out all transactions by an account
    */
    private static void printTransactionsByAccount(Collection<String> collection,String stringToPrintIfEmpty,String stringToPrintIfNOTEmpty){
         //System.out.println("------------------------------------------------------");
         if(collection.isEmpty()){
                    System.out.println(stringToPrintIfEmpty);
                }else{
                   System.out.println(stringToPrintIfNOTEmpty);
                   
                   for(String s:collection){
                       
                        System.out.println("===========================================================");
                        System.out.println(s);
                        String[] parts = s.split("\t");
                        //Collection<String> transactions=transactionsBean.getTranscationsByAccNum(Integer.valueOf(parts[0]));
                         //for(String ss:transactions){
                           //  System.out.println(ss);
                         //}
                    }
                    System.out.println("===========================================================");
                    
                }
        System.out.println("------------------------------------------------------");
    }
    
    /*
    print collection description string
    */
    private static void printCollection(Collection<String> collection,String stringToPrintIfEmpty,String stringToPrintIfNOTEmpty){
         System.out.println("------------------------------------------------------");
         if(collection.isEmpty()){
                    System.out.println(stringToPrintIfEmpty);
                }else{
                   System.out.println(stringToPrintIfNOTEmpty);
                   for(String s:collection){
                      System.out.println(s);
                     } 
                }
        System.out.println("------------------------------------------------------");
    }
    
    
    private static int getAmount( int MINIMUN_SELECTION,int MAXIMUM_SELECTION) {
        quit=false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        int output = 0;
        boolean validInput = false;
        do {
            
             try {
                    input = br.readLine();
                    if (input.length() > 5) {
                         continue;
                     }
                    if(input.equalsIgnoreCase("Q")){
                        quit=true;
                        break;
                    }
                    output = Integer.parseInt(input);
                    if (output < MINIMUN_SELECTION || output > MAXIMUM_SELECTION) {
                        System.out.println("Please only select from range: "+MINIMUN_SELECTION+" to "+MAXIMUM_SELECTION);
                        continue;
                    }
                    validInput = true;
                 } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                    ioe.printStackTrace();
                    validInput = false;
                 } catch (NumberFormatException nfe) {
                     validInput = false;
                }
         } while (validInput == false);
         return output;
    }
    
    
    private static int getSelection( int MINIMUN_SELECTION,int MAXIMUM_SELECTION) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        int output = -1;
        boolean validInput = false;
        quit=false;
        do {
            
             try {
                    input = br.readLine();
                    if(input.equalsIgnoreCase("Q")){
                        quit=true;
                        return 0;
                    }
                    if (input.length() > 3) {
                         continue;
                     }
                    output = Integer.parseInt(input);
                    if (output < MINIMUN_SELECTION || output > MAXIMUM_SELECTION) {
                        System.out.println("Please only select from range: "+MINIMUN_SELECTION+" to "+MAXIMUM_SELECTION);
                        continue;
                    }
                    validInput = true;
                 } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                    ioe.printStackTrace();
                    validInput = false;
                 } catch (NumberFormatException nfe) {
                     validInput = false;
                }
         } while (validInput == false);
         return output;
    }
    
    
    private static String getUserInput(String prompt){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        quit=false;
        boolean validInput = false;
        do {
            System.out.println(prompt);
             try {
                    input = br.readLine();
                    if(input.equalsIgnoreCase("q")){
                        quit=true;
                        break;
                    }
                    if (input.trim().length() < 1) {
                         continue;
                     }
                    
                    validInput = true;
                 } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                    ioe.printStackTrace();
                    validInput = false;
                 } catch (NumberFormatException nfe) {
                     validInput = false;
                }
         } while (validInput == false);
        return input;
    }
    
    private static java.sql.Date getUserDOB(String prompt){
         String dob=getUserInput(prompt);
         SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
         java.util.Date date = null;
         java.sql.Date sqlStartDate=null;
         try {
                    date = sdf1.parse(dob);
                    sqlStartDate = new Date(date.getTime()); 
                } catch (ParseException ex) {
                   System.out.println("Invalidate Date Format, please follow (dd-mm-yyyy)");
                   getUserDOB(prompt);
                }
         return sqlStartDate;
    }
    
    
    
}
