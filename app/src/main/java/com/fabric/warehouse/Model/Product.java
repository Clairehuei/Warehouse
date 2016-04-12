package com.fabric.warehouse.Model;

import java.io.Serializable;

/**
 * Created by 6193 on 2016/4/12.
 */
public class Product {

    private static final long serialVersionUID = 1L;

    private String name;

    private String emailId;

    public Product() {

    }
    public Product(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
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


}
