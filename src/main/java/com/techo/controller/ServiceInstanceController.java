package com.techo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techo.model.ServiceInstance;
import com.techo.repository.ServiceInstanceRepository;
import com.techo.repository.ServiceRepository;
import com.techo.service.HashingService;

@RestController
public class ServiceInstanceController {

	private ServiceRepository serviceRepository;

	private ServiceInstanceRepository serviceInstanceRepository;
	private HashingService hashService;

	@Autowired
	public void setServiceRepository(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}

	@Autowired
	public void setHashService(HashingService hashService) {
		this.hashService = hashService;
	}

	@Autowired
	public void setServiceInstanceRepository(ServiceInstanceRepository serviceInstanceRepository) {
		this.serviceInstanceRepository = serviceInstanceRepository;
	}

	@RequestMapping(value = "/v2/service_instances/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> createServiceInstance(@PathVariable("id") String id,
			@RequestBody ServiceInstance serviceInstance) {
		serviceInstance.setId(id);

		boolean exists = serviceInstanceRepository.exists(id);

		if (exists) {
			ServiceInstance existing = serviceInstanceRepository.findOne(id);
			if (existing.equals(serviceInstance)) {
				return new ResponseEntity<>("{}", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("{}", HttpStatus.CONFLICT);
			}
		} else {
			serviceInstanceRepository.save(serviceInstance);
			hashService.create(id);
			return new ResponseEntity<>("{}", HttpStatus.CREATED);
		}
	}

	@RequestMapping(value = "/hashingBroker/service_instances/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") String id, @RequestParam("service_id") String serviceId,
			@RequestParam("plan_id") String planId) {
		boolean exists = serviceRepository.exists(id);

		if (exists) {
			serviceRepository.delete(id);
			hashService.delete(id);
			return new ResponseEntity<>("{}", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("{}", HttpStatus.GONE);
		}
	}

}
