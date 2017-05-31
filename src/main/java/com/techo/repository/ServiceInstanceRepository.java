package com.techo.repository;

import org.springframework.data.repository.CrudRepository;

import com.techo.model.ServiceInstance;

public interface ServiceInstanceRepository extends CrudRepository<ServiceInstance, String> {
}
