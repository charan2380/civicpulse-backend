// CivicIssueController.java
package com.example.civicpulse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
// --- REMOVED: @CrossOrigin annotation is no longer needed here ---
public class CivicIssueController {

    @Autowired
    private CivicIssueRepository civicIssueRepository;

    @GetMapping
    public List<CivicIssue> getAllIssues() {
        return civicIssueRepository.findAll();
    }

    @PostMapping
    public CivicIssue createIssue(@RequestBody CivicIssue newIssue) {
        return civicIssueRepository.save(newIssue);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CivicIssue> updateIssueStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest statusUpdate) {
        CivicIssue issue = civicIssueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found with id: " + id));

        issue.setStatus(statusUpdate.getStatus());
        final CivicIssue updatedIssue = civicIssueRepository.save(issue);
        return ResponseEntity.ok(updatedIssue);
    }
}