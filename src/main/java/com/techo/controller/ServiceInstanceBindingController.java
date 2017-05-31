package com.techo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techo.model.Credentials;
import com.techo.model.ServiceBinding;
import com.techo.repository.ServiceBindingRepository;
import com.techo.repository.ServiceInstanceRepository;

@RestController
public class ServiceInstanceBindingController {

	@Autowired
	ServiceInstanceRepository serviceInstanceRepository;

	@Autowired
	ServiceBindingRepository serviceBindingRepository;

	@Autowired
	Cloud cloud;

	@RequestMapping(value = "/v2/service_instances/{instanceId}/service_bindings/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> bindServiceInstance(@PathVariable("instanceId") String instanceId,
			@PathVariable("id") String id, @RequestBody ServiceBinding serviceBinding) {
		
		if (!serviceInstanceRepository.exists(instanceId)) {
			return new ResponseEntity<Object>(
					"{\"description\":\"Service instance " + instanceId + " does not exist!\"", HttpStatus.BAD_REQUEST);
		}

		serviceBinding.setId(id);
		serviceBinding.setInstanceId(instanceId);

		boolean exists = serviceBindingRepository.exists(id);

		if (exists) {
			ServiceBinding existing = serviceBindingRepository.findOne(id);
			if (existing.equals(serviceBinding)) {
				return new ResponseEntity<Object>(wrapCredentials(existing.getCredentials()), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>("{}", HttpStatus.CONFLICT);
			}
		} else {
			Credentials credentials = new Credentials();
			credentials.setId(UUID.randomUUID().toString());
			credentials.setUri("http://" + myUri() + "/v2/" + instanceId);
			credentials.setUsername("dharam");
			credentials.setPassword("test123");
			serviceBinding.setCredentials(credentials);
			serviceBindingRepository.save(serviceBinding);
			return new ResponseEntity<Object>(wrapCredentials(credentials), HttpStatus.CREATED);
		}
	}

	@RequestMapping(value = "/hashingBroker/service_instances/{instanceId}/service_bindings/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBinding(@PathVariable("instanceId") String instanceId,
			@PathVariable("id") String id, @RequestParam("service_id") String serviceId,
			@RequestParam("plan_id") String planId) {
		boolean exists = serviceBindingRepository.exists(id);

		if (exists) {
			serviceBindingRepository.delete(id);
			return new ResponseEntity<>("{}", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("{}", HttpStatus.GONE);
		}
	}

	private String myUri() {
		ApplicationInstanceInfo applicationInstanceInfo = cloud.getApplicationInstanceInfo();
		List<Object> uris = (List<Object>) applicationInstanceInfo.getProperties().get("uris");
		return uris.get(0).toString();
	}

	private Map<String, Object> wrapCredentials(Credentials credentials) {
		Map<String, Object> wrapper = new HashMap<>();
		wrapper.put("credentials", credentials);
		return wrapper;
	}
}
