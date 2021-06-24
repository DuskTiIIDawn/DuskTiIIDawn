package com.example.StockMarketCharting.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.UserEntity;
import com.example.StockMarketCharting.repositories.UserEntityRepository;

@Service
public class UserEntityService {
	@Autowired
	UserEntityRepository userEntityRepository;

	public UserEntity saveUser(UserEntity user) {
		return userEntityRepository.save(user);
	}

	public UserEntity findByUserId(Long id) {
		Optional<UserEntity> user = userEntityRepository.findById(id);
		if (user.isPresent())
			return user.get();
		else
			return null;
	}

	public UserEntity findByUserName(String username) {
		List<UserEntity> users = userEntityRepository.findByUserName(username);
		if (users.size() > 0) {
			return users.get(0);
		} else
			return null;

	}

	public Boolean existsByUserNameAndPassword(String username, String password) {
		return userEntityRepository.existsByUserNameAndPassword(username, password);
	}

	public Boolean existsByUserName(String username) {
		return userEntityRepository.existsByUserName(username);
	}

	public Boolean existsByMobileNo(String mobileNo) {
		return userEntityRepository.existsByMobileNumber(mobileNo);
	}

	public Boolean existsByEmail(String email) {
		return userEntityRepository.existsByEmail(email);
	}

	public boolean isAdmin(Long userId) {
		Optional<UserEntity> user = userEntityRepository.findById(userId);
		if (user.isPresent()) {
			return userEntityRepository.findById(userId).get().isAdmin();
		} else {
			return false;
		}
	}

	public boolean isConfirmed(Long userId) {
		Optional<UserEntity> user = userEntityRepository.findById(userId);
		if (user.isPresent()) {
			return userEntityRepository.findById(userId).get().isConfirmed();
		} else {
			return false;
		}
	}

}
