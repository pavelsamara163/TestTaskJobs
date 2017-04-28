package com.example.CustomerOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Павел on 22.04.2017.
 */
@Component
public class CustomerOrderSQL {

    @Autowired
    private JdbcTemplate jdbcTemplateOrder;


    public  List<CustomerOrder> findAllOrders(){
        return jdbcTemplateOrder.query(
                "SELECT id_order,description,client_order,date_creation,date_complete,price,status FROM order_customers",
                (rs, rowNum) -> new CustomerOrder(rs.getLong("id_order"),
                        rs.getString("description"), rs.getString("client_order"), rs.getString("date_creation"), rs.getString("date_complete"),
                        rs.getString("price"),rs.getString("status")));

    }

    public void updateLineOrder(CustomerOrder customerOrder) {
        jdbcTemplateOrder.update(
                "UPDATE order_customers SET description=?, client_order=?, date_creation=?, date_complete=? ,price=?,status=? WHERE id_order=?",
                customerOrder.getDescription(),customerOrder.getClient_order(), customerOrder.getDate_creation(),customerOrder.getDate_complete(),customerOrder.getPrice(),customerOrder.getStatus(),customerOrder.getId_order());

    }
    public void uploadLineOrder(CustomerOrder customerOrder){
        jdbcTemplateOrder.update("INSERT INTO order_customers(description, client_order, date_creation, date_complete ,price,status) VALUES(?, ?, ?, ?, ?, ?)",
                customerOrder.getDescription(), customerOrder.getClient_order(), customerOrder.getDate_creation(), customerOrder.getDate_complete(), customerOrder.getPrice(),
                customerOrder.getStatus());

    }
    public void deleteLineOrder(CustomerOrder customerOrder){
        jdbcTemplateOrder.update("DELETE FROM order_customers WHERE id_order=?",
                customerOrder.getId_order());

    }

    public List<CustomerOrder> filterText(String valueChec) {
                    return  jdbcTemplateOrder.query("SELECT * FROM order_customers WHERE (description,status,client_order) LIKE '%"+valueChec+"%'",
                        (rs, rowNum) -> new CustomerOrder(rs.getLong("id_order"),
                                rs.getString("description"), rs.getString("client_order"), rs.getString("date_creation"), rs.getString("date_complete"),
                                rs.getString("price"),rs.getString("status")));
    }
}
