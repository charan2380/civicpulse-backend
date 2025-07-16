// StatusUpdateRequest.java
package com.example.civicpulse;

import lombok.Data;

// This class is a Data Transfer Object (DTO). Its only purpose is to
// carry data for the status update request.
@Data
public class StatusUpdateRequest {
    private String status;
}