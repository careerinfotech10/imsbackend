package com.career.students.resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.career.students.entity.Student;
import com.career.students.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentResource {

	@Autowired
	private StudentService studentService;

	@PostMapping("/students")
	public Map<String, List<String>> saveStudents(@RequestBody List<Student> students) {
		return studentService.saveUniqueStudent(students);
	}

	@PostMapping
	public Student createStudent(@RequestBody Student student) {
		return studentService.createStudent(student);
	}

	@GetMapping()
	public List<Student> getStudents() {
		return studentService.getStudents();
	}

	@GetMapping("id/{id}")
	public Student getStudentById(@PathVariable("id") Long id) {
		return studentService.getStuentById(id);
	}
}
