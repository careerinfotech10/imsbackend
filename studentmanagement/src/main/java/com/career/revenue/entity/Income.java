package com.career.revenue.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Income {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long incomeId;
	private Long studentId;
	private Long amount;
	private Long categoryId;
	private String transactionId;
	private String courierId;
	
	// audit columns
	private LocalDateTime createdOn = LocalDateTime.now();
	private LocalDateTime updatedOn = LocalDateTime.now();
	private String createdBy;
	private String updatedBy;
	private boolean active = true;

}
