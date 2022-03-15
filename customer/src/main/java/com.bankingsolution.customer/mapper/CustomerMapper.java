package com.bankingsolution.customer.mapper;

import com.bankingsolution.customer.model.Customer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface CustomerMapper {

    @Insert("INSERT INTO customer(country) VALUES" + "(#{country})")
    @Options(useGeneratedKeys = true, keyProperty = "customerId", keyColumn = "customer_id")
    public void insertCustomer(Customer customer);

    @Select("SELECT * FROM customer")
    public Customer getAllCustomers();

    @Select("SELECT * FROM customer WHERE customer_id = #{customerId}")
    public Customer getCustomerById(Long customerId);
}
