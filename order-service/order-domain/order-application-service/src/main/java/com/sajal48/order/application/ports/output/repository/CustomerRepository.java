package com.sajal48.order.application.ports.output.repository;

import com.sajal48.order.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
	Optional<Customer> findCustomer(UUID customerId);
}
