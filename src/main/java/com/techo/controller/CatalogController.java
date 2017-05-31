package com.techo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techo.model.Service;
import com.techo.repository.ServiceRepository;

@RestController
public class CatalogController {

	@Autowired
    ServiceRepository serviceRepository;
	
	@RequestMapping("/v2/catalog")
	public Map<String, Iterable<Service>> catalog() {
		Map<String, Iterable<Service>> serviceDetails = new HashMap<>();
		serviceDetails.put("services", serviceRepository.findAll());
		return serviceDetails;
	}
}
