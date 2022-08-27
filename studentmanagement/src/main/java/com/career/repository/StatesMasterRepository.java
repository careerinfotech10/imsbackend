package com.career.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.career.entity.StateMasterEntity;

public interface StatesMasterRepository extends JpaRepository<StateMasterEntity, Integer> {

	public List<StateMasterEntity> findByCountryId(Integer countryId);

}
