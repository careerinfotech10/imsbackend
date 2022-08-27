package com.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.career.entity.UserAccountEntity;

public interface UserAccountsRepository extends JpaRepository<UserAccountEntity, String> {

	public UserAccountEntity findByEmailAndPazzword(String email, String pazzword);

}
