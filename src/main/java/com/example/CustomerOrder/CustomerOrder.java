package com.example.CustomerOrder;

/**
 * Created by Павел on 22.04.2017.
 */

public class CustomerOrder{
    private Long id_order;
    private String description, client_order,date_creation,date_complete,price,status;


    protected CustomerOrder(Long id_order, String description,
                            String client_order, String date_creation, String date_complete, String price, String status) {
            this.id_order = id_order;
            this.description = description;
            this.client_order = client_order;
            this.date_creation = date_creation;
            this.date_complete = date_complete;
            this.price = price;
            this.status = status;


    }


    public Long getId_order() {
        return id_order;
    }

    public String getDescription() {
        return description;
    }

    public String getClient_order() {
        return client_order;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getDate_complete() {
        return date_complete;
    }

    public String getDate_creation() {

        return date_creation;
    }

    public void setId_order(Long id_order) {
        this.id_order = id_order;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClient_order(String client_order) {
        this.client_order = client_order;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public void setDate_complete(String date_complete) {
        this.date_complete = date_complete;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}