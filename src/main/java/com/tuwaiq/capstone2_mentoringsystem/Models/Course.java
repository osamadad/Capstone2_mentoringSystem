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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the course title can't be empty, please try again")
    @Size(min = 3, max = 75, message = "Sorry, the course title can't be less than 3 or longer than 75 characters, please try again")
    @Column(columnDefinition = "varchar(75) not null")
    private String title;
    @NotEmpty(message = "Sorry, the course description can't be empty, please try again")
    @Size(max = 200, message = "Sorry, the course description can't be longer than 200 characters, please try again")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;
    @NotEmpty(message = "Sorry, the course type can't be empty, please try again")
    @Pattern(regexp = "one-to-one|group", message = "Sorry, the course type must be 'one-to-one' or 'group', please try again")
    @Column(columnDefinition = "varchar(10) not null")
    private String type;
    @Positive(message = "Sorry, the course capacity must be a positive number, please try again")
    @Column(columnDefinition = "int not null default 1")
    private Integer capacity;
    @NotNull(message = "Sorry, the course price can't be empty, please try again")
    @Positive(message = "Sorry, the course price must be a positive number, please try again")
    @Column(columnDefinition = "float not null")
    private Double price;
    @NotEmpty(message = "Sorry, the course level can't be empty, please try again")
    @Pattern(regexp = "beginner|intermediate|advanced", message = "Sorry, the course level must be 'beginner', 'intermediate', or 'advanced', please try again")
    @Size(max = 15, message = "Sorry, the course level can't be longer than 15 characters, please try again")
    @Column(columnDefinition = "varchar(15) not null")
    private String level;
    @NotEmpty(message = "Sorry, the course location can't be empty, please try again")
    @Size(max = 100, message = "Sorry, the course location can't be longer than 100 characters, please try again")
    @Column(columnDefinition = "varchar(100) not null")
    private String location;
    @NotEmpty(message = "Sorry, the course time can't be empty, please try again")
    @Size(max = 50, message = "Sorry, the course time can't be longer than 50 characters, please try again")
    @Column(columnDefinition = "varchar(50) not null")
    private String time;
    @Size(max = 10, message = "Sorry, the course admin status can't be longer than 10 characters, please try again")
    @Pattern(regexp = "pending|approved", message = "Sorry, the course admin status must be 'pending' or 'approved', please try again")
    @Column(columnDefinition = "varchar(10) not null default 'pending'")
    private String adminStatus;
    @Size(max = 20, message = "Sorry, the course level can't be longer than 20 characters, please try again")
    @Pattern(regexp = "waiting for members|ready to start", message = "Sorry, the course group status must be 'waiting for members' or 'ready to start', please try again")
    @Column(columnDefinition = "varchar(20) not null")
    private String groupStatus;
    @Column(columnDefinition = "float default 0.0")
    private Double rating;
    @Column(columnDefinition = "datetime")
    private LocalDateTime creationDate;
    @NotNull(message = "Sorry, the instructor id can't be empty, please try again")
    @Column(columnDefinition = "int not null")
    private Integer instructorId;
    @NotNull(message = "Sorry, the category id can't be empty, please try again")
    @Column(columnDefinition = "int not null")
    private Integer categoryId;
}
