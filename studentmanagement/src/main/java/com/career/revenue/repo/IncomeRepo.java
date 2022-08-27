package com.career.revenue.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.career.revenue.entity.Income;

public interface IncomeRepo  extends JpaRepository<Income, Long>{
	
	

}
