package com.techo.repository;

import org.springframework.data.repository.CrudRepository;

import com.techo.model.Service;

public interface ServiceRepository extends CrudRepository<Service, String> {
}
