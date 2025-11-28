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
public class userProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the user profile username can't be empty, please try again")
    @Size(min = 3, max = 15, message = "Sorry, the user profile username can't be less than 3 or longer than 15 characters, please try again")
    @Column(columnDefinition = "varchar(15) not null")
    private String username;
    @NotEmpty(message = "Sorry, the user profile phone can't be empty, please try again")
    @Size(min = 10, max = 10, message = "Sorry, the user profile phone must be 10 digits, please try again")
    @Pattern(regexp = "^05[0-9].*$", message = "Sorry, the user profile phone must start with 05 and contain only numbers, please try again")
    @Column(columnDefinition = "varchar(10) not null")
    private String phone;
    @NotNull(message = "Sorry, the user profile age can't be empty, please try again")
    @Positive(message = "Sorry, the user profile age must be a positive number, please try again")
    @Column(columnDefinition = "int not null")
    private Integer age;
    @NotEmpty(message = "Sorry, the user profile gender can't be empty, please try again")
    @Pattern(regexp = "male|female", message = "Sorry, the user profile gender must be either 'male' or 'female', please try again")
    @Column(columnDefinition = "varchar(10) not null")
    private String gender;
    @NotEmpty(message = "Sorry, the user profile country can't be empty, please try again")
    @Size(max = 60, message = "Sorry, the user profile country can't be longer than 60 characters, please try again")
    @Column(columnDefinition = "varchar(60) not null")
    private String country;
    @NotEmpty(message = "Sorry, the user profile city can't be empty, please try again")
    @Size(max = 60, message = "Sorry, the user profile city can't be longer than 60 characters, please try again")
    @Column(columnDefinition = "varchar(60) not null")
    private String city;
    @NotNull(message = "Sorry, the user id can't be empty, please try again")
    @Column(columnDefinition = "int not null")
    private Integer userId;
}
