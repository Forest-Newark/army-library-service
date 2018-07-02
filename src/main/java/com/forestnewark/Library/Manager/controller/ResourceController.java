package com.forestnewark.Library.Manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forestnewark.Library.Manager.Repository.ResourceRepository;
import com.forestnewark.Library.Manager.bean.Resource;

@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/resource")
@CrossOrigin(origins = "*")
@org.springframework.web.bind.annotation.RestController
public class ResourceController {
	
	
	@Autowired
	ResourceRepository resourceRepository;
	
	
    /**
     * Get all resources
     * @return all compositions (unsorted)
     */
    @RequestMapping(value= "/getAll", produces = "application/json")
    public ResponseEntity<?> getAllCompositions(){
    	return new ResponseEntity<>(resourceRepository.findAll(),HttpStatus.OK);
     
    }
    
    @RequestMapping("/createOrUpdate")
    public ResponseEntity<?> createOrUpdateResource(@RequestBody Resource resource){
    	resourceRepository.save(resource);
    	return new ResponseEntity<>(resource, HttpStatus.OK);
    }
    
    @RequestMapping("/delete")
    public ResponseEntity<?> deleteResource(@RequestBody Resource resource){
    	resourceRepository.delete(resource);
    	return new ResponseEntity<>(resource, HttpStatus.OK);
    }


}
