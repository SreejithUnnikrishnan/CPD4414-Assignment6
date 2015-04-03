/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author c0644881
 */
@ApplicationScoped
public class ProductList {

    private List<Product> productList;

    public ProductList() {
        productList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM products";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
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

    public void add(Product product) throws Exception {
        int changes = 0;
        changes = doInsert("INSERT INTO products (product_id, name, description, quantity) VALUES (?, ?, ?, ?)", product.getProductID(), product.getName(), product.getDescription(), product.getQuantity());
        if (changes > 0) {
            productList.add(product);
        } else {
            throw new Exception("InsertException");
        }
    }

    public void remove(Product product) {
        productList.remove(product);
    }

    public void remove(int id) throws Exception {
        int changes = 0;
        changes = doRemove("delete from products where product_id = ?", id);
        if (changes > 0) {
            int index = -1;
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getProductID() == id) {
                    index = i;
                }
            }
            productList.remove(index);
        }
        else{
            throw new Exception("DeleteException");
        }

    }

    public void set(int id, Product product) throws Exception {
        int changes = 0;
        changes = doUpdate("update products set product_id = ?, name = ?, description = ?, quantity = ? where product_id = ?", product.getProductID(), product.getName(), product.getDescription(), product.getQuantity(), product.getProductID());
        if (changes > 0) {
            Product old = get(id);
            old.setName(product.getName());
            old.setDescription(product.getDescription());
            old.setQuantity(product.getQuantity());
        } else {
            throw new Exception("UpdateException");
        }
    }

    public Product get(int id) {
        int index = -1;
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductID() == id) {
                index = i;
            }
        }
        if (index == -1) {
            return null;
        } else {
            return productList.get(index);
        }
    }

    public JsonArray toJSON() {
        JsonArrayBuilder jarray = Json.createArrayBuilder();
        for (int i = 0; i < productList.size(); i++) {
            JsonObjectBuilder obj = Json.createObjectBuilder()
                    .add("productId", productList.get(i).getProductID())
                    .add("name", productList.get(i).getName())
                    .add("description", productList.get(i).getDescription())
                    .add("quantity", productList.get(i).getQuantity());
            jarray.add(obj);

        }
        return jarray.build();
    }

    private int doUpdate(String query, int id, String name, String description, int quantity, int pid) {
        int numChanges = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, description);
            pstmt.setInt(4, quantity);
            pstmt.setInt(5, pid);
            numChanges = pstmt.executeUpdate();
            return numChanges;
        } catch (SQLException ex) {
            System.out.println("Sql Exception: " + ex.getMessage());
            return numChanges;
        }

    }

    private int doInsert(String query, int id, String name, String description, int quantity) {
        int numChanges = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, description);
            pstmt.setInt(4, quantity);
            numChanges = pstmt.executeUpdate();
            return numChanges;
        } catch (SQLException ex) {
            System.out.println("Sql Exception: " + ex.getMessage());
            return numChanges;
        }

    }

    private int doRemove(String query, int id) {
        int numChanges = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            numChanges = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Sql Exception: " + ex.getMessage());

        }
        return numChanges;
    }
}
