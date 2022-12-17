package com.bankingsolution.customer.mapper;

import com.bankingsolution.customer.model.Customer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CustomerMapper {

    @Insert("INSERT INTO customer(country) VALUES" + "(#{country})")
    @Options(useGeneratedKeys = true, keyProperty = "customerId", keyColumn = "customer_id")
    void insertCustomer(Customer customer);

    @Select("SELECT customer_id as customerId, country as country FROM customer")
    List<Customer> getAllCustomers();

    @Select("SELECT customer_id as customerId, country as country FROM customer as cust" +
            " WHERE customer_id = #{customerId}")
    Customer getCustomerById(Long customerId);

}
