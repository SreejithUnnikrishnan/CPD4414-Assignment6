/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean;

import java.util.List;
import javax.ejb.Singleton;
import javax.json.JsonArray;

/**
 *
 * @author c0644881
 */
@Singleton
public class ProductList {
    private List<Product> productList;
    ProductList(){
        
    }
    public void add(Product product){
        
    }
    public void remove(Product product){
        
    }
     public void remove(int id){
         
     }
    public void set(int id,Product product){
        
    }
    public Product get(int id){
        return null;
    }
    public JsonArray  toJSON(){
        return null;
    } 
}
