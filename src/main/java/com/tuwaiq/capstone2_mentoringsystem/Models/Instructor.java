package com.tuwaiq.capstone2_mentoringsystem.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the instructor username can't be empty, please try again")
    @Size(min = 3, max = 15, message = "Sorry, the instructor username can't be less than 3 or longer than 15 characters, please try again")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;
    @NotEmpty(message = "Sorry, the instructor password can't be empty, please try again")
    @Size(min = 8, max = 16, message = "Sorry, the instructor password can't be less than 8 or longer than 16, please try again")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,16}$", message = "Sorry, the instructor password must have at least 1 uppercase, 1 lowercase, 1 number, and at least 8 and at most 16 characters please try again")
    @Column(columnDefinition = "varchar(16) not null")
    private String password;
    @Email(message = "Sorry, the instructor email must follow a valid email format, please try again")
    @Size(min = 5, max = 20, message = "Sorry, the instructor email can't less than 5 or longer than 20 characters, please try again")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String email;
    @NotEmpty(message = "Sorry, the instructor phone number can't be empty, please try again")
    @Size(min = 10, max = 10, message = "Sorry, the instructor phone number must be 10 digits, please try again")
    @Pattern(regexp = "^05[0-9].*$", message = "Sorry, the instructor phone number must start with 05 and contain only numbers, please try again")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String phoneNumber;
    @NotEmpty(message = "Sorry, the instructor country can't be empty, please try again")
    @Size(max = 60, message = "Sorry, the instructor country can't be longer than 60 characters, please try again")
    @Column(columnDefinition = "varchar(60) not null")
    private String country;
    @NotEmpty(message = "Sorry, the instructor city can't be empty, please try again")
    @Size(max = 60, message = "Sorry, the instructor city can't be longer than 60 characters, please try again")
    @Column(columnDefinition = "varchar(60) not null")
    private String city;
    @NotNull(message = "Sorry, the instructor date of birth can't be empty, please try again")
    @Column(columnDefinition = "date not null")
    private LocalDate dateOfBirth;
    @NotEmpty(message = "Sorry, the instructor gender can't be empty, please try again")
    @Pattern(regexp = "male|female", message = "Sorry, the instructor gender must be either 'male' or 'female', please try again")
    @Column(columnDefinition = "varchar(10) not null")
    private String gender;
    @Column(columnDefinition = "float default 0.0")
    private Double rating;
    @NotNull(message = "Sorry, the instructor years of experience can't be empty, please try again")
    @Positive(message = "Sorry, the instructor years of experience must be a positive number, please try again")
    @Column(columnDefinition = "int not null")
    private Integer yearsOfExperience;
    @Pattern(regexp = "pending|approved", message = "Sorry, the instructor status must be 'pending' or 'approved', please try again")
    @Size(max = 10, message = "Sorry, the instructor status can't be longer than 10 characters, please try again")
    @Column(columnDefinition = "varchar(10) not null default 'pending'")
    private String status;
    @Column(columnDefinition = "datetime")
    private LocalDateTime registerDate;
    @NotNull(message = "Sorry, the category id can't be empty, please try again")
    @Column(columnDefinition = "int not null")
    private Integer categoryId;
}
