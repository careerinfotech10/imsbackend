package com.career.mentor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.career.mentor.entity.Mentor;

@Repository
public interface MentorRepo extends JpaRepository<Mentor, Long>{

}
