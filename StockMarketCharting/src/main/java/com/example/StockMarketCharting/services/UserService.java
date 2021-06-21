package com.example.StockMarketCharting.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.User;
import com.example.StockMarketCharting.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public User createNewUser(User user) {
		return userRepository.save(user);
	}

	public User findByUserNameAndPassword(String username, String password) {
		return userRepository.findByUserNameAndPassword(username, password);
	}

	public boolean isAdmin(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return userRepository.findById(userId).get().isAdmin();
		} else {
			return false;
		}
	}

	public boolean isConfirmed(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return userRepository.findById(userId).get().isConfirmed();
		} else {
			return false;
		}
	}

}
