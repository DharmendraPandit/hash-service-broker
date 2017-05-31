package com.techo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techo.service.HashingService;

@RestController
public class HashController {

	private HashingService hashService;

	@Autowired
	public void setHashService(HashingService hashService) {
		this.hashService = hashService;
	}

	@RequestMapping(value = "/v2/{instanceId}/{key}", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@PathVariable("instanceId") String instanceId, @PathVariable("key") String key,
			@RequestBody String value) {
		hashService.put(instanceId, key, value);
		return new ResponseEntity<>("{}", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/v2/{instanceId}/{key}", method = RequestMethod.GET)
	public ResponseEntity<Object> get(@PathVariable("instanceId") String instanceId, @PathVariable("key") String key) {
		Object result = hashService.get(instanceId, key);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("{}", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/v2/{instanceId}/{key}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("instanceId") String instanceId,
			@PathVariable("key") String key) {
		Object result = hashService.get(instanceId, key);
		if (result != null) {
			hashService.delete(instanceId, key);
			return new ResponseEntity<>("{}", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("{}", HttpStatus.GONE);
		}
	}
}
