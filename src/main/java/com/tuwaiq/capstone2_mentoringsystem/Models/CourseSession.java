package com.tuwaiq.capstone2_mentoringsystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Sorry, the session start date can't be empty, please try again")
    @Column(columnDefinition = "date not null")
    private LocalDate startDate;
    @NotNull(message = "Sorry, the session end date can't be empty, please try again")
    @Column(columnDefinition = "date not null")
    private LocalDate endDate;
    @NotNull(message = "Sorry, the session start time can't be empty, please try again")
    @Column(columnDefinition = "time not null")
    private LocalTime startTime;
    @NotNull(message = "Sorry, the session end time can't be empty, please try again")
    @Column(columnDefinition = "time not null")
    private LocalTime endTime;
    @AssertFalse(message = "Sorry, the session occupation can't be true, please try again")
    @Column(columnDefinition = "boolean default 'false'")
    private Boolean occupied;
    @NotNull(message = "Sorry, the course id can't be empty, please try again")
    @Column(columnDefinition = "int not null")
    private Integer courseId;
}
