package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StockMarketCharting.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByUserName(String username);

	User findByUserNameAndPassword(String username, String password);

	User findByMobileNumber(Long mobileNumber);

}
