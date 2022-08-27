package com.career.students.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.career.students.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {

	List<Student> findAllEmailByIsContractSentFalse();
	List<Student> findAllEmailByformalContractGeneratedFalse();


	boolean existsByEmail(String email);

}
