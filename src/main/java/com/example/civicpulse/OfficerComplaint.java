// OfficerComplaint.java
package com.example.civicpulse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Data
@Entity
// --- FIX: Add constructors for robustness with Spring Data JPA ---
@NoArgsConstructor
@AllArgsConstructor
public class OfficerComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String officeName;
    private String officerName;
    private String description;
    private boolean isAnonymous;

    private Double latitude;
    private Double longitude;
    private String address;

    private String status = "Submitted";

    @CreationTimestamp
    private Instant createdAt;
}