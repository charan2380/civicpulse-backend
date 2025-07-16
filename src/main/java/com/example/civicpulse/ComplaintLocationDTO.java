// ComplaintLocationDTO.java
package com.example.civicpulse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// This DTO will only contain location data for public viewing on the map.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintLocationDTO {
    private double latitude;
    private double longitude;
}