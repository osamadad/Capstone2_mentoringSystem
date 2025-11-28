package com.tuwaiq.capstone2_mentoringsystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the instructor profile username can't be empty, please try again")
    @Size(min = 3, max = 15, message = "Sorry, the instructor profile username can't be less than 3 or longer than 15 characters, please try again")
    @Column(columnDefinition = "varchar(15) not null")
    private String username;
    @NotNull(message = "Sorry, the instructor profile age can't be empty, please try again")
    @Positive(message = "Sorry, the instructor profile age must be a positive number, please try again")
    @Column(columnDefinition = "int not null")
    private Integer age;
    @NotEmpty(message = "Sorry, the instructor profile phone number can't be empty, please try again")
    @Size(min = 10, max = 10, message = "Sorry, the instructor profile phone number must be 10 digits, please try again")
    @Pattern(regexp = "^05[0-9].*$", message = "Sorry, the instructor profile phone number must start with 05 and contain only numbers, please try again")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;
    @NotEmpty(message = "Sorry, the instructor profile gender can't be empty, please try again")
    @Pattern(regexp = "male|female", message = "Sorry, the instructor profile gender must be either 'male' or 'female', please try again")
    @Column(columnDefinition = "varchar(10) not null")
    private String gender;
    @NotEmpty(message = "Sorry, the category name can't be empty, please try again")
    @Size(max = 35, message = "Sorry, the category name can't be longer than 35 characters, please try again")
    @Column(columnDefinition = "varchar(35) not null")
    private String categoryName;
    @Column(columnDefinition = "float default 0.0")
    private Double rating;
    @NotEmpty(message = "Sorry, the instructor profile country can't be empty, please try again")
    @Size(max = 60, message = "Sorry, the instructor profile country can't be longer than 60 characters, please try again")
    @Column(columnDefinition = "varchar(60) not null")
    private String country;
    @NotEmpty(message = "Sorry, the instructor profile city can't be empty, please try again")
    @Size(max = 60, message = "Sorry, the instructor profile city can't be longer than 60 characters, please try again")
    @Column(columnDefinition = "varchar(60) not null")
    private String city;
    @NotNull(message = "Sorry, the years of experience can't be empty, please try again")
    @Positive(message = "Sorry, the years of experience must be a positive number, please try again")
    @Column(columnDefinition = "int not null")
    private Integer yearsOfExperience;
    @NotNull(message = "Sorry, the instructor id can't be empty, please try again")
    @Column(columnDefinition = "int not null")
    private Integer instructorId;
}
