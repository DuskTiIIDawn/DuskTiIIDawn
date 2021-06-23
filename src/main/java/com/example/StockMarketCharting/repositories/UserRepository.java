package com.example.StockMarketCharting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StockMarketCharting.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	List<UserEntity> findByUserName(String userName);

	Boolean existsByEmail(String email);

	Boolean existsByUserName(String userName);

	Boolean existsByMobileNumber(String mobileNumber);

	Boolean existsByUserNameAndPassword(String userName, String password);
}
