package com.career.revenue.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expences {

	private LocalDateTime createdOn=LocalDateTime.now();
	private LocalDateTime updatedOn=LocalDateTime.now();
	private String createdBy;
	private String updatedBy;
	private boolean active=true;

}
