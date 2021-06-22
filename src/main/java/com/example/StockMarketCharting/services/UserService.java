package com.example.StockMarketCharting.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.UserEntity;
import com.example.StockMarketCharting.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public UserEntity createNewUser(UserEntity user) {
		return userRepository.save(user);
	}

	public UserEntity findByUserId(Long id) {
		Optional<UserEntity> user = userRepository.findById(id);
		if (user.isPresent())
			return user.get();
		else
			return null;
	}

	public UserEntity findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	public UserEntity findByMobileNo(String mobileNo) {
		return userRepository.findByMobileNumber(mobileNo);
	}

	public Boolean existsByUserNameAndPassword(String username, String password) {
		return userRepository.existsByUserNameAndPassword(username, password);
	}

	public Boolean existsByUserName(String username) {
		return userRepository.existsByUserName(username);
	}

	public Boolean existsByMobileNo(String mobileNo) {
		return userRepository.existsByMobileNumber(mobileNo);
	}

	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public boolean isAdmin(Long userId) {
		Optional<UserEntity> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return userRepository.findById(userId).get().isAdmin();
		} else {
			return false;
		}
	}

	public boolean isConfirmed(Long userId) {
		Optional<UserEntity> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return userRepository.findById(userId).get().isConfirmed();
		} else {
			return false;
		}
	}

}
