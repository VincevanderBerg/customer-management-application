package com.codemountain.customermanagementapplication.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerJDBCDataAccessService implements CustomerDao {
    private final JdbcTemplate template;
    private final CustomerRowMapper rowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate template, CustomerRowMapper rowMapper) {
        this.template = template;
        this.rowMapper = rowMapper;
    }

    static List<Customer> customers;

    static {
        customers = new ArrayList<>();

        Customer customer1 = new Customer(
                1L,
                "Vince",
                "van der Berg",
                "vincevanderberg@gmail.com",
                34
        );
        customers.add(customer1);

        Customer customer2 = new Customer(
                        2L,
                        "Lanchi",
                        "van der Berg",
                        "lanchivanderberg@gmail.com",
                        34
                );
        customers.add(customer2);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        final String query = """
                SELECT customerId, firstName, lastName, email, age
                FROM customers;
                """;

        return template.query(query, rowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Long customerId) {
        final String query = """
                SELECT customerId, firstName, lastName, email, age
                FROM customers
                WHERE customerId = (?);
                """;

        return template.query(query, rowMapper, customerId)
                .stream()
                .findFirst();
    }

    @Override
    public boolean existsCustomerWithId(Long customerId) {
        final String query = """
                SELECT EXISTS (
                SELECT * FROM customers
                WHERE customerId = (?)
                );
                """;
        return Boolean.TRUE.equals(template.queryForObject(query, Boolean.class, customerId));
    }

    @Override
    public boolean existsCustomerWithEmail(String customerEmail) {
        String query = """
                SELECT EXISTS (
                SELECT * FROM customers
                WHERE email = (?)
                );
                """;
        return Boolean.TRUE.equals(template.queryForObject(query, Boolean.class, customerEmail));
    }

    @Override
    public void insertCustomerIntoCustomers(Customer customer) {
        final String query = """
                INSERT INTO customers(firstName, lastName, email, age)
                VALUES (?, ?, ?, ?);
                """;

        template.update(
                query,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAge()
        );
    }
}
