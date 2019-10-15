package com.tristan.store;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements  com.tristan.store.IDataAdapter {

    Connection conn = null;

    public int connect(String database_address) {
        try {
            // db parameters
            String url = database_address;
            // create a connection to the database
            conn = DriverManager.getConnection(url);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }
        System.out.println("Connection to SQLite has been established.");
        return CONNECTION_OPEN_OK;
    }

    public int disconnect() {
        try {
            conn.close();
            return CONNECTION_CLOSE_OK;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
    }
    //Product Methods
    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();

        try {
            String sql = "SELECT ProductId, Name, Price, Quantity, Vendor, Description FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");
            product.mVendor = rs.getString("Vendor");
            product.mDescription = rs.getString("Description");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }
    public int saveProduct(ProductModel product) {
        try {
            String sql = "INSERT INTO Products(ProductId, Name, Price, Quantity, Vendor, Description) VALUES " + product;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PRODUCT_DUPLICATE_ERROR;
        }

        return PRODUCT_SAVED_OK;
    }

    //Customer Methods
    public CustomerModel loadCustomer(int customerID) {
       CustomerModel customer = new CustomerModel();

        try {
            String sql = "SELECT CustomerId, Name, Address, Phone, PaymentInfo FROM Customers WHERE CustomerId = " + customerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customer.mCustomerID = rs.getInt("CustomerId");
            customer.mName = rs.getString("Name");
            customer.mAddress = rs.getString("Address");
            customer.mPhone = rs.getString("Phone");
            customer.mPaymentInfo = rs.getString("PaymentInfo");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }
    public int saveCustomer(CustomerModel customer) {
        try {
            String sql = "INSERT INTO Customers(CustomerId, Name, Address, Phone, PaymentInfo) VALUES " + customer;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return CUSTOMER_DUPLICATE_ERROR;
        }

        return CUSTOMER_SAVED_OK;
    }

    @Override
    public int savePurchase(PurchaseModel purchase) {
        try {
            String sql = "INSERT INTO Purchases(PurchaseID, CustomerID, ProductID, DateTime, Quantity, Price, Tax, Total) VALUES " + purchase;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_DUPLICATE_ERROR;
        }

        return PURCHASE_SAVED_OK;

    }


}
