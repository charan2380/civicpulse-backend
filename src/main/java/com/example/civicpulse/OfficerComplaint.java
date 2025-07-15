package com.example.civicpulse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@Entity
public class OfficerComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String officeName;
    private String officerName; // This can be left empty by the user.
    private String description;
    private boolean isAnonymous; // To track if the report is anonymous.

    @CreationTimestamp
    private Instant createdAt;
}