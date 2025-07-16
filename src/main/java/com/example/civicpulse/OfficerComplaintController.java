// OfficerComplaintController.java
package com.example.civicpulse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/complaints")
public class OfficerComplaintController {

    @Autowired
    private OfficerComplaintRepository officerComplaintRepository;

    @PostMapping
    public OfficerComplaint createComplaint(@RequestBody OfficerComplaint newComplaint) {
        newComplaint.setAnonymous(true);
        return officerComplaintRepository.save(newComplaint);
    }

    @GetMapping
    public List<OfficerComplaint> getAllComplaints() {
        return officerComplaintRepository.findAll();
    }
    
    // This endpoint correctly provides locations for UNRESOLVED complaints.
    @GetMapping("/locations")
    public List<ComplaintLocationDTO> getComplaintLocations() {
        return officerComplaintRepository.findAll().stream()
                .filter(c -> "Submitted".equals(c.getStatus()) && c.getLatitude() != null)
                .map(c -> new ComplaintLocationDTO(c.getLatitude(), c.getLongitude()))
                .collect(Collectors.toList());
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<OfficerComplaint> updateComplaintStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest statusUpdate) {
        OfficerComplaint complaint = officerComplaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + id));

        complaint.setStatus(statusUpdate.getStatus());
        final OfficerComplaint updatedComplaint = officerComplaintRepository.save(complaint);
        return ResponseEntity.ok(updatedComplaint);
    }
}