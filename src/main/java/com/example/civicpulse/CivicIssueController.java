package com.example.civicpulse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "http://localhost:5173") // Allows our React app to call these endpoints
public class CivicIssueController {

    // 1. Inject the repository
    @Autowired
    private CivicIssueRepository civicIssueRepository;

    // 2. Create an endpoint to GET all issues
    @GetMapping
    public List<CivicIssue> getAllIssues() {
        // Use the repository to find all issues in the database and return them
        return civicIssueRepository.findAll();
    }

    // 3. Create an endpoint to POST a new issue
    @PostMapping
    public CivicIssue createIssue(@RequestBody CivicIssue newIssue) {
        // The @RequestBody annotation automatically converts the incoming JSON into a CivicIssue object.
        // We then save this object to the database using the repository.
        // The saved object (with its new ID) is returned as the response.
        return civicIssueRepository.save(newIssue);
    }
}