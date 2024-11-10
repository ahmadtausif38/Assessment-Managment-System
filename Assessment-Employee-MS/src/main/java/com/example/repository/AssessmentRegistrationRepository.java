package com.example.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Assessment;
import com.example.entity.AssessmentRegistration;

@Repository
public interface AssessmentRegistrationRepository extends JpaRepository<AssessmentRegistration, Long>{


	Optional<AssessmentRegistration> findByuserIdAndAssessment(Long userId, Assessment assessment);

	boolean existsByUserIdAndAssessmentDate(Long userId, LocalDate assessmentDate);

	List<AssessmentRegistration> findByUserId(Long id);
}