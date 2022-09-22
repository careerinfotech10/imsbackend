package com.career.mentor.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.career.mentor.dto.MentorDto;
import com.career.mentor.entity.Mentor;
import com.career.mentor.repo.MentorRepo;

@Service
public class MentorService {
	
	@Autowired
	private MentorRepo mentorRepo;
	
	
	private ModelMapper mapper=new ModelMapper();

	public ResponseEntity<MentorDto> saveMentor(MentorDto mentorDto) {
		Mentor entity = mapper.map(mentorDto, Mentor.class);
		return new ResponseEntity<MentorDto>(mapper.map(mentorRepo.save(entity), MentorDto.class), HttpStatus.OK);
	}

	public ResponseEntity<MentorDto> updateMentor(MentorDto mentorDto) {
		Mentor entity = mapper.map(mentorDto, Mentor.class);
		return new ResponseEntity<MentorDto>(mapper.map(mentorRepo.save(entity), MentorDto.class), HttpStatus.OK);
	}

	public ResponseEntity<List<MentorDto>> getAllMentors() {
		List<Mentor> allMentors = mentorRepo.findAll();
		List<MentorDto> mentorDtos = new ArrayList<>();
		for (Mentor mentor : allMentors) {
			mentorDtos.add(mapper.map(mentor, MentorDto.class));
		};
		return new ResponseEntity<List<MentorDto>>(mentorDtos,HttpStatus.OK);
	}

	public ResponseEntity<MentorDto> getMentorById(Long mentorId) {
		return new ResponseEntity<MentorDto>(mapper.map(mentorRepo.findById(mentorId).get(), MentorDto.class), HttpStatus.OK);
	}

	public ResponseEntity<String> deleteMentorById(Long mentorId) {
		String response = "MENTOR DELETING FAILED";
		if(mentorRepo.existsById(mentorId)) {
			mentorRepo.deleteById(mentorId);
			response = "MENTOR DELETED SUCCESSFULLY";
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
