package com.career.mentor.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.career.mentor.dto.MentorDto;
import com.career.mentor.service.MentorService;

@RestController
@RequestMapping("/mentor")
public class MentorResource {

	@Autowired
	private MentorService mentorService;
	
	@PostMapping("/save")
	public ResponseEntity<MentorDto> saveMentor(@RequestBody MentorDto mentorDto){
		return mentorService.saveMentor(mentorDto);
	}
	
	@PutMapping("/update")
	public ResponseEntity<MentorDto> updateMentor(@RequestBody MentorDto mentorDto){
		return mentorService.updateMentor(mentorDto);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<MentorDto>> getAllMentors(){
		return mentorService.getAllMentors();
	}
	
	@GetMapping("/{mentorId}")
	public ResponseEntity<MentorDto> getMentorById(@PathVariable Long mentorId){
		return mentorService.getMentorById(mentorId);
	}
	
	@DeleteMapping("/{mentorId}")
	public ResponseEntity<String> deleteMentorById(@PathVariable Long mentorId){
		return mentorService.deleteMentorById(mentorId);
	}
}
