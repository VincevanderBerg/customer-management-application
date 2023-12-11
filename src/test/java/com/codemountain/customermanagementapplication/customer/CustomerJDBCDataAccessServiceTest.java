package com.codemountain.customermanagementapplication.customer;

import com.codemountain.customermanagementapplication.AbstractTestcontainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainer {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper rowMapper = new CustomerRowMapper();
    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                rowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        // Given
        final String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.name().lastName(), email, 34);

        underTest.insertCustomer(customer);

        // When
        List<Customer> actual = underTest.selectAllCustomers();

        // Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // Given
        final String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.name().lastName(), email, 34);

        underTest.insertCustomer(customer);

        // When
        Long id = underTest.selectAllCustomers().stream().filter(c -> c.getEmail().equals(email)).findFirst().map(Customer::getCustomerId).orElseThrow();
        Optional<Customer> actual = underTest.selectCustomerById(id);

        // Then
        assertThat(actual).isPresent();
        assertThat(actual).hasValueSatisfying(c -> {
            assertThat(c.getCustomerId().equals(id)).isTrue();
            assertThat(c.getFirstName().equals(customer.getFirstName())).isTrue();
            assertThat(c.getLastName().equals(customer.getLastName())).isTrue();
            assertThat(c.getEmail().equals(email)).isTrue();
            assertThat(c.getAge().equals(customer.getAge())).isTrue();
        });
    }

    @Test
    void existsCustomerWithId() {
        // Given
        final String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.name().lastName(), email, 34);

        underTest.insertCustomer(customer);

        // When
        Long id = underTest.selectAllCustomers().stream().filter(c -> c.getEmail().equals(email)).findFirst().map(Customer::getCustomerId).orElseThrow();
        boolean actual = underTest.existsCustomerWithId(id);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerWithEmail() {
        // Given
        final String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.name().lastName(), email, 34);

        underTest.insertCustomer(customer);

        // When
        boolean actual = underTest.existsCustomerWithEmail(email);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void insertCustomer() {
        // Given
        final String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.name().lastName(), email, 34);

        underTest.insertCustomer(customer);

        // When
        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email)).findFirst()
                .map(Customer::getCustomerId)
                .orElseThrow();

        Optional<Customer> actual = underTest.selectCustomerById(id);

        // Then
        assertThat(actual).isPresent();
        assertThat(actual).hasValueSatisfying(c -> {
            assertThat(c.getCustomerId().equals(id)).isTrue();
            assertThat(c.getFirstName().equals(customer.getFirstName())).isTrue();
            assertThat(c.getLastName().equals(customer.getLastName())).isTrue();
            assertThat(c.getEmail().equals(email)).isTrue();
            assertThat(c.getAge().equals(customer.getAge())).isTrue();
        });
    }
    
    @Test
    void updateCustomerFirstName() {
        // Given
        final String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.name().lastName(), email, 34);

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .map(Customer::getCustomerId)
                .orElseThrow();

        final String newFirstName = "foo";

        // When
        Customer update = underTest.selectCustomerById(id).orElseThrow();
        update.setFirstName(newFirstName);

        underTest.updateCustomer(update);

        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getCustomerId().equals(id)).isTrue();
            assertThat(c.getFirstName().equals(newFirstName)).isTrue();
            assertThat(c.getLastName().equals(customer.getLastName())).isTrue();
            assertThat(c.getEmail().equals(email)).isTrue();
            assertThat(c.getAge().equals(customer.getAge())).isTrue();
        });
    }

    @Test
    void updateCustomerLastName() {
        // Given
        final String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.name().lastName(), email, 34);

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .map(Customer::getCustomerId)
                .orElseThrow();

        final String newLastName = "fighter";

        // When
        Customer update = underTest.selectCustomerById(id).orElseThrow();
        update.setLastName(newLastName);

        underTest.updateCustomer(update);

        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getCustomerId().equals(id)).isTrue();
            assertThat(c.getFirstName().equals(customer.getFirstName())).isTrue();
            assertThat(c.getLastName().equals(newLastName)).isTrue();
            assertThat(c.getEmail().equals(email)).isTrue();
            assertThat(c.getAge().equals(customer.getAge())).isTrue();
        });
    }

    @Test
    void updateCustomerEmail() {
        // Given
        final String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.name().lastName(), email, 34);

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .map(Customer::getCustomerId)
                .orElseThrow();

        final String newEmail = FAKER.internet().safeEmailAddress() + UUID.randomUUID();

        // When
        Customer update = underTest.selectCustomerById(id).orElseThrow();
        update.setEmail(newEmail);

        underTest.updateCustomer(update);

        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getCustomerId().equals(id)).isTrue();
            assertThat(c.getFirstName().equals(customer.getFirstName())).isTrue();
            assertThat(c.getLastName().equals(customer.getLastName())).isTrue();
            assertThat(c.getEmail().equals(newEmail)).isTrue();
            assertThat(c.getAge().equals(customer.getAge())).isTrue();
        });
    }

    @Test
    void updateCustomerAge() {
        // Given
        final String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.name().lastName(), email, 34);

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .map(Customer::getCustomerId)
                .orElseThrow();

        final Integer newAge = 99;

        // When
        Customer update = underTest.selectCustomerById(id).orElseThrow();
        update.setAge(newAge);

        underTest.updateCustomer(update);

        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getCustomerId().equals(id)).isTrue();
            assertThat(c.getFirstName().equals(customer.getFirstName())).isTrue();
            assertThat(c.getLastName().equals(customer.getLastName())).isTrue();
            assertThat(c.getEmail().equals(email)).isTrue();
            assertThat(c.getAge().equals(newAge)).isTrue();
        });
    }

    @Test
    void deleteCustomerWithId() {
        // Given
        final String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().firstName(), FAKER.name().lastName(), email, 34);

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .map(Customer::getCustomerId)
                .orElseThrow();

        // When
        underTest.deleteCustomerWithId(id);

        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isNotPresent();
    }
}
