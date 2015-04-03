package services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.bean.Product;
import com.bean.ProductList;
import com.database.DatabaseConnection;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author c0644881
 */
@Path("/products")
@RequestScoped
public class ProductServlet {

    @Inject
    ProductList productList;

    @GET
    @Produces("application/json")
    public Response doGet() {
        //String result = getResults("SELECT * FROM products");
        //return result;
        return Response.ok(productList.toJSON()).build();

    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response doGet(@PathParam("id") String id) {
//        String result = getProduct("SELECT * FROM products WHERE product_id = ?", id);
//        return result;
        return Response.ok(productList.get(Integer.parseInt(id)).toJSON()).build();

    }

//    private String getResults(String query) {
//
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            StringWriter out = new StringWriter();
//            JsonArrayBuilder jarray = Json.createArrayBuilder();
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                JsonObjectBuilder obj = Json.createObjectBuilder()
//                        .add("productId", rs.getInt("product_id"))
//                        .add("name", rs.getString("name"))
//                        .add("description", rs.getString("description"))
//                        .add("quantity", rs.getInt("quantity"));
//                jarray.add(obj);
//
//            }
//            return jarray.build().toString();
//
//        } catch (SQLException ex) {
//            System.out.println("Exception in getting database connection: " + ex.getMessage());
//            return "Sorry... Something went wrong";
//        }
//
//    }
//    private String getProduct(String query, String... params) {
//        StringBuilder stringBuilder = new StringBuilder();
//        StringWriter out = new StringWriter();
//        int count = 0;
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            for (int i = 1; i <= params.length; i++) {
//                pstmt.setString(i, params[i - 1]);
//            }
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                JsonGeneratorFactory factory = Json.createGeneratorFactory(null);
//                JsonGenerator gen = factory.createGenerator(out);
//                gen.writeStartObject()
//                        .write("productId", rs.getInt("product_id"))
//                        .write("name", rs.getString("name"))
//                        .write("description", rs.getString("description"))
//                        .write("quantity", rs.getInt("quantity"))
//                        .writeEnd();
//
//                gen.close();
//            }
//
//        } catch (SQLException ex) {
//            System.out.println("Exception in getting database connection: " + ex.getMessage());
//        }
//        return out.toString();
//    }
    @POST
    @Consumes("application/json")
    public Response doPost(@Context UriInfo uri, String str) {
        Product product = null;
        JsonParser parser = Json.createParser(new StringReader(str));
        Map<String, String> map = new HashMap<>();
        String name = "", value;
        while (parser.hasNext()) {
            JsonParser.Event evt = parser.next();
            switch (evt) {
                case KEY_NAME:
                    name = parser.getString();
                    break;
                case VALUE_STRING:
                    value = parser.getString();
                    map.put(name, value);
                    break;
                case VALUE_NUMBER:
                    value = Integer.toString(parser.getInt());
                    map.put(name, value);
                    break;
            }
        }
        product.setProductID(Integer.parseInt(map.get("id")));
        product.setName(map.get("name"));
        product.setDescription(map.get("description"));
        product.setQuantity(Integer.parseInt(map.get("quantity")));
        try {
            productList.add(product);
            //return Response.ok().build();
            return Response.ok(uri.getAbsolutePath().toString() + "/" + map.get("id")).build();
        } catch (Exception ex) {
            return Response.status(500).build();
        }
//        int changes = 0;
//
//        String product_name = map.get("name");
//        String description = map.get("description");
//        String quantity = map.get("quantity");
//        changes = doInsert("INSERT INTO products (name, description, quantity) VALUES (?, ?, ?)", product_name, description, quantity);
//        if (changes > 0) {
//            int id = getId("select max(product_id) from products");
//            //String res = "http://localhost:8080/CPD4414-Assignment5/webresources/products/" + id;
//            return Response.ok(uri.getAbsolutePath().toString() + "/" + id).build();
//        } else {
//            return Response.status(500).build();
//        }
    }

    

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response doPut(@Context UriInfo uri, @PathParam("id") String id, String str) {
        Product product = null;
        int changes = 0;
        JsonParser parser = Json.createParser(new StringReader(str));
        Map<String, String> map = new HashMap<>();
        String name = "", value;
        while (parser.hasNext()) {
            JsonParser.Event evt = parser.next();
            switch (evt) {
                case KEY_NAME:
                    name = parser.getString();
                    break;
                case VALUE_STRING:
                    value = parser.getString();
                    map.put(name, value);
                    break;
                case VALUE_NUMBER:
                    value = Integer.toString(parser.getInt());
                    map.put(name, value);
                    break;
            }
        }
        product.setProductID(Integer.parseInt(id));
        product.setName(map.get("name"));
        product.setDescription(map.get("description"));
        product.setQuantity(Integer.parseInt(map.get("quantity")));
        try {
            productList.set(Integer.parseInt(id), product);
            //return Response.ok().build();
            return Response.ok(uri.getAbsolutePath().toString() + "/" + id).build();
        } catch (Exception ex) {
            return Response.status(500).build();
        }

    }

//    private int doUpdate(String query, String id, String name, String description, String quantity, String pid) {
//        int numChanges = 0;
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, id);
//            pstmt.setString(2, name);
//            pstmt.setString(3, description);
//            pstmt.setString(4, quantity);
//            pstmt.setString(5, pid);
//            numChanges = pstmt.executeUpdate();
//            return numChanges;
//        } catch (SQLException ex) {
//            System.out.println("Sql Exception: " + ex.getMessage());
//            return numChanges;
//        }
//
//    }
    @DELETE
    @Path("{id}")
    public Response doDelete(@Context UriInfo uri, @PathParam("id") String id) {
        int changes = 0;

        changes = doRemove("delete from products where product_id = ?", id);
        if (changes > 0) {
            return Response.ok().build();
        } else {
            return Response.status(500).build();
        }

    }

    private int getId(String query) {
        int id = 0;
        //String jsonArray = null;      
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException ex) {
            System.out.println("Exception in getting database connection: " + ex.getMessage());
        }
        return id;
    }

    private int doRemove(String query, String id) {
        int numChanges = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, id);
            numChanges = pstmt.executeUpdate();
            return numChanges;
        } catch (SQLException ex) {
            System.out.println("Sql Exception: " + ex.getMessage());
            return numChanges;
        }

    }

}
