package com.forestnewark.Library.Manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.forestnewark.Library.Manager.Repository.LibraryUserRepository;
import com.forestnewark.Library.Manager.bean.LibraryUser;

@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/api/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	LibraryUserRepository userRepository;

	// GET ALL
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCatagories() {
		return new ResponseEntity<Object>(userRepository.findAll(), HttpStatus.OK);

	}

    /**
     * Add User
     * @param user
     * @return
     */
    @RequestMapping(value="/addUser",method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody LibraryUser user){

        if(userRepository.findByUserName(user.getUserName()) == null){
            userRepository.save(user);
            return new ResponseEntity<LibraryUser>(user,HttpStatus.CREATED);

        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
	

    /**
     * Validate User Login
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value="/validateLogin",method= RequestMethod.POST)
    public boolean validateLogin(@RequestParam("userName") String userName,@RequestParam("password") String password){

        System.out.println("username::" + userName);
        System.out.println("password::" + password);
        LibraryUser checkUser = userRepository.findByUserName(userName);
        System.out.println("checkuser::" + checkUser);

        if(checkUser == null){
            return false;
        }
        else{
            return (checkUser.getPassword().equals(password));
        }


    }

    /**
     * Create Super User
     * @return
     */
    @RequestMapping(value="/enableSuperUser",method=RequestMethod.GET)
    public ResponseEntity<?> createSuperUser(){
        if(userRepository.findByUserName("SuperUser") == null){
            userRepository.save(new LibraryUser("SuperUser","Super","User","MASTER","superUser"));

        }
        return new ResponseEntity<LibraryUser>(userRepository.findByUserName("SuperUser"), HttpStatus.OK);
    }
}
