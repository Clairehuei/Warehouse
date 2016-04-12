package com.fabric.warehouse.Model;

import java.io.Serializable;

/**
 * Created by 6193 on 2016/4/12.
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String emailId;
    private String quantity;

    public Product() {

    }
    public Product(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
