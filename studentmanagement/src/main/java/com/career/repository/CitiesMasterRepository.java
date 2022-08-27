package com.career.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.career.entity.CitiesMasterEntity;

public interface CitiesMasterRepository extends JpaRepository<CitiesMasterEntity, Integer> {

	public List<CitiesMasterEntity> findByStateId(Integer stateId);
}
