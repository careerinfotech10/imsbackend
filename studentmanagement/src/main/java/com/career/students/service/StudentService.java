package com.career.students.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.career.students.entity.Student;
import com.career.students.repo.StudentRepo;

@Service
public class StudentService {
	@Autowired
	private StudentRepo studentRepo;

	public Student createStudent(Student student) {
		return studentRepo.save(student);
	}

	public List<Student> getStudents() {
		return studentRepo.findAll();
	}

	public Student getStuentById(Long id) {
		return studentRepo.findById(id).orElse(new Student());
	}

	public List<Student> saveStuents(List<Student> students) {
		// TODO Auto-generated method stub
		return studentRepo.saveAll(students);
	}

	List<String> failedList = new ArrayList<String>();
	List<String> successList = new ArrayList<String>();
	Map<String, List<String>> data = new HashMap<String, List<String>>();

	public Map<String, List<String>> saveUniqueStudent(List<Student> students) {

		for (Student student : students) {
			boolean isExists = studentRepo.existsByEmail(student.getEmail());
			if (!isExists) {
				studentRepo.save(student);
				successList.add(student.getEmail());
			} else {
				failedList.add("Data Already Exists: " + student.getEmail());
			}
		}
		data.put("failed", failedList);
		data.put("success", successList);
		return data;
	}

}
