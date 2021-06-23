package com.example.StockMarketCharting.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.StockMarketCharting.entities.UserEntity;
import com.example.StockMarketCharting.services.UserEntityService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserEntityService service;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, String> createAuthenticationToken(@RequestBody JsonNode request) throws Exception {
		Map<String, String> res = new HashMap<>();
		if (request.get("userName") == null || request.get("password") == null) {
			res.put("ERROR", "BAD DATA!");
		}
		String userName = request.get("userName").asText();
		String rawPassword = request.get("password").asText();
		UserEntity userEntity = service.findByUserName(userName);

		if (userEntity == null || bcryptEncoder.matches(rawPassword, userEntity.getPassword())) {
			res.put("ERROR", "Username Password Does Not Match ");
			return res;
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
		final String token = jwtTokenUtil.generateToken(userDetails);
		UserEntity usr = service.findByUserName(userName);
		if (usr.isAdmin()) {
			res.put("IS_ADMIN", "YES");
		}
		res.put("TOKEN", token);
		return res;
	}

}
