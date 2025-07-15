package com.example.civicpulse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marks this as a Spring Data repository bean.
public interface CivicIssueRepository extends JpaRepository<CivicIssue, Long> {
    // That's it!
}