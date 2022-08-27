package com.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.career.entity.CountryMasterEntity;

public interface CountryMasterRepository extends JpaRepository<CountryMasterEntity, Integer> {

}
