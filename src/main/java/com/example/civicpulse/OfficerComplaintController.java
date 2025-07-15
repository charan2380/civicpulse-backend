package com.example.civicpulse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:5173") // Also allow our React app to call this controller
public class OfficerComplaintController {

    // 1. Inject the corresponding repository
    @Autowired
    private OfficerComplaintRepository officerComplaintRepository;

    // 2. Create an endpoint to POST a new complaint
    @PostMapping
    public OfficerComplaint createComplaint(@RequestBody OfficerComplaint newComplaint) {
        // Spring Boot deserializes the incoming JSON into an OfficerComplaint object
        // and we save it directly.
        return officerComplaintRepository.save(newComplaint);
    }

    // 3. Create a SECURE endpoint to GET all complaints (for admins)
    // Note: For now this is open, but we will secure it later with Spring Security.
    @GetMapping
    public List<OfficerComplaint> getAllComplaints() {
        // This endpoint will be called by the admin dashboard.
        return officerComplaintRepository.findAll();
    }
}