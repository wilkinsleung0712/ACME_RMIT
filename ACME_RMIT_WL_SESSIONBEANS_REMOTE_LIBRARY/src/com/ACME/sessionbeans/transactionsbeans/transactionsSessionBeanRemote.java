/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.sessionbeans.transactionsbeans;

import java.util.Collection;
import javax.ejb.Remote;

/**
 *
 * @author WEIQIANGLIANG
 */
@Remote
public interface transactionsSessionBeanRemote {
    public boolean createTranscations(int accNum, int amount, String description, String type, boolean result);
    public Collection getAllTranscations();
    //public boolean updateTransactions(int T_ID);
    public boolean deleteTransactions(int T_ID);
    public Collection getTranscationsByAccNum(int AccNum);
}
