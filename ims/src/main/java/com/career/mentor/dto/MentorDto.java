package com.career.mentor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorDto {
	
	private Long mentorId;
	private String firstName;
	private String lastName;
	private Long studentId;

}
