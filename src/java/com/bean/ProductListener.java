/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author c0644881
 */
public class ProductListener implements MessageListener {
    private ProductList productList;
    
    @Override
    public void onMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
