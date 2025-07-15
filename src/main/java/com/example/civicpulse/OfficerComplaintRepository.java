package com.example.civicpulse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficerComplaintRepository extends JpaRepository<OfficerComplaint, Long> {
    // This interface is also complete.
}