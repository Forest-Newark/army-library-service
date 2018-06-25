package com.forestnewark.Library.Manager.controller;

import com.forestnewark.Library.Manager.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Forest on 11/25/17.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/api")
@CrossOrigin(origins = "*")
public class RestController {

    @Autowired
    FileService fileService;

    

    /**
     * Check service is up and running
     * Loading for Heroku Free Servers
     * @return status code
     */
    @RequestMapping("/servicetest")
    public ResponseEntity serviceTest(){
        return new ResponseEntity(HttpStatus.OK);
    }




   

}
