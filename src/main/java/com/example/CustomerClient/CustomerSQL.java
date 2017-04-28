package com.example.CustomerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerSQL {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public  List<Customer> findAll()  {
        return jdbcTemplate.query(
                "SELECT id, first_name, last_name, patronymic, telephone_number FROM customers",
                (rs, rowNum) -> new Customer(rs.getLong("id"),
                        rs.getString("first_name"), rs.getString("last_name"), rs.getString("patronymic"),
                        rs.getString("telephone_number")));
    }
    public void updateLine(Customer customer) {
        jdbcTemplate.update(
                "UPDATE customers SET first_name=?, last_name=?, patronymic=?, telephone_number=? WHERE id=?",
                customer.getFirstName(), customer.getLastName(),customer.getPatronymic(),customer.getTelephoneNumber(), customer.getId());
    }
    public void uploadLine(Customer customer){
            jdbcTemplate.update("INSERT INTO customers(first_name,last_name,patronymic, telephone_number) VALUES(? ,? ,? ,? )",
                customer.getFirstName(), customer.getLastName(),customer.getPatronymic(),customer.getTelephoneNumber());

        }
    public void deleteLine(Customer customer){
        jdbcTemplate.update("DELETE FROM customers WHERE id=?",
                customer.getId());

    }

}
