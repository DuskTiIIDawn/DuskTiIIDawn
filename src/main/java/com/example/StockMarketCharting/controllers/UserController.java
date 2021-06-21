package com.example.StockMarketCharting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.StockMarketCharting.entities.User;
import com.example.StockMarketCharting.services.UserService;

public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> createUser(@RequestBody User user, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			User userRepo = userService.createNewUser(user);
			if (userRepo == null) {
				return new ResponseEntity<>(userRepo, HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>(userRepo, HttpStatus.OK);
			}
		}
	}

}
