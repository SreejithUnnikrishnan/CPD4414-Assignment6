/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author c0644881
 */
@MessageDriven(mappedName = "jms/Queue")
public class ProductListener implements MessageListener {
    @Inject
    private ProductList productList;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String json = ((TextMessage) message).getText();
                JsonObject obj = Json.createReader(new StringReader(json)).readObject();
                productList.add(new Product(obj));
                
            }
        } catch (JMSException ex) {
            System.out.println("Exceoption in JMS: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exceoption in adding product: " + ex.getMessage());
        }

    }

}
