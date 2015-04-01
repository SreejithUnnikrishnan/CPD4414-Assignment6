/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean;

import javax.ejb.Stateful;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author c0644881
 */
@Stateful
public class Product {
    private int ProductID;
    private String Name;
    private String Description;
    private int Quantity;

    public Product() {
    }

    Product(JsonObject obj) {
      this.setProductID(obj.getInt("id"));
      this.setName(obj.getString("name"));
      this.setQuantity(obj.getInt("quantity"));
      this.setDescription(obj.getString("description"));
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
    
    public Object toJSON(){
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("productId", ProductID)
                .add("name", Name)
                .add("description", Description)
                .add("quantity", Quantity);
        return obj.build();
    }

    
    
}
