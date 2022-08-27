package com.career.students.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private Long mentorId;
	@Column(name = "isContractSent", columnDefinition = "boolean default true")
	private boolean isContractSent = true;
	@CreationTimestamp
	private LocalDateTime createdOn = LocalDateTime.now();
	@UpdateTimestamp
	private LocalDateTime updateOn = LocalDateTime.now();
	@Column(name = "active", columnDefinition = "boolean default true")
	private boolean active = true;
	private String education;
	private String passingYear;
	private String mentor;
	@Column(name = "isMentorOpted", columnDefinition = "boolean default false")
	private boolean isMentorOpted = false;
	private Long incomeId;
	private Long basePackage;
	@Column(name = "formalContractRecived", columnDefinition = "boolean default false")

	private boolean formalContractRecived = false;
	@Column(name = "formalContractGenerated", columnDefinition = "boolean default false")
	private boolean formalContractGenerated = false;
	
	@Column(name = "batchCode", columnDefinition = "varchar default false")
	private String batchCode = "SDE13";

}
