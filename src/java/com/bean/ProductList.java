/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.database.DatabaseConnection;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author c0644881
 */
@Singleton
public class ProductList {
    @EJB
    Product product;

    private List<Product> productList;
    

    public ProductList() {
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM products";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                product.setProductID(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setQuantity(rs.getInt("quantity"));
                productList.add(product);
            }

        } catch (SQLException ex) {
            System.out.println("Exception in getting database connection: " + ex.getMessage());
        }
    }

    public void add(Product product) {
        productList.add(product);
    }

    public void remove(Product product) {
        productList.remove(product);
    }

    public void remove(int id) {
        int index = 0;
        for(int i=0; i<productList.size();i++){
            if(productList.get(i).getProductID() == id){
               index = i; 
            }
        }
        productList.remove(index);
        
    }

    public void set(int id, Product product) {

    }

    public Product get(int id) {
        return null;
    }

    public JsonArray toJSON() {
        return null;
    }
}
