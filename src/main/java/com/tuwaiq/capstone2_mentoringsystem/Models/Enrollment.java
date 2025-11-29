package com.tuwaiq.capstone2_mentoringsystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 15, message = "Sorry, the enrollment status can't be longer than 15 characters, please try again")
    @Pattern(regexp = "pending|approved|in progress|finished", message = "Sorry, the enrollment status must be 'pending', 'approved', in 'progress', or 'finished' please try again")
    @Column(columnDefinition = "varchar(15) not null default 'pending'")
    private String status;
    @Column(columnDefinition = "datetime")
    private LocalDateTime enrollmentDate;
    @NotNull(message = "Sorry, the user id can't be empty, please try again")
    @Column(columnDefinition = "int not null")
    private Integer userId;
    @NotNull(message = "Sorry, the course id can't be empty, please try again")
    @Column(columnDefinition = "int not null")
    private Integer courseId;
    @NotNull(message = "Sorry, the course session id can't be empty, please try again")
    @Column(columnDefinition = "int not null")
    private Integer courseSessionId;
}
