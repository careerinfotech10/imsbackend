package com.career.revenue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDTO {
	private Long studentId;
	private Long amount;
	private Long categoryId;
	private String transactionId;

}
