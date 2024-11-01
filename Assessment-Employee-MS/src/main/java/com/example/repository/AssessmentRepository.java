package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Assessment;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

		List<Assessment> findByType(String assessmentType);

		Optional<Assessment> findByName(String assessmentName);

}
