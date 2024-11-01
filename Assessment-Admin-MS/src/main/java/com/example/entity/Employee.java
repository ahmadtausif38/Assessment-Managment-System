package com.example.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String userId;
    private String assessment;
    private LocalDate date;
    private String type; // Can be TECH or BEH


}

