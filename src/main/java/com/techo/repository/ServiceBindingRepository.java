package com.techo.repository;

import org.springframework.data.repository.CrudRepository;

import com.techo.model.ServiceBinding;

public interface ServiceBindingRepository extends CrudRepository<ServiceBinding, String> {
}
