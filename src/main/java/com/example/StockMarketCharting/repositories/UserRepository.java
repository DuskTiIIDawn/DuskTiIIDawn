package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StockMarketCharting.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

	UserEntity findByUserName(String username);

	UserEntity findByMobileNumber(String mobileNumber);

	Boolean existsByEmail(String email);

	Boolean existsByUserName(String userName);

	Boolean existsByMobileNumber(String mobileNo);

	Boolean existsByUserNameAndPassword(String username, String password);
}
