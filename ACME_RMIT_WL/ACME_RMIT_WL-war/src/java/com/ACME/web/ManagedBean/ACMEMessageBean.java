/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.web.ManagedBean;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author WEIQIANGLIANG
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/myACME_BANK_Queue")
})
public class ACMEMessageBean implements MessageListener {
    
    public ACMEMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage tm=(TextMessage) message;
            try {
                System.out.println("Recived Message from web.Message : "+tm.getText());
            } catch (JMSException ex) {
                Logger.getLogger(ACMEMessageBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
