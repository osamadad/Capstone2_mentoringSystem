package com.tuwaiq.capstone2_mentoringsystem.Models;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the user name can't be empty, please try again")
    @Size(min = 3, max = 15, message = "Sorry, the user name can't be less than 3 or longer than 15 characters, please try again")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;
    @NotEmpty(message = "Sorry, the user password can't be empty, please try again")
    @Size(min = 8, max = 16, message = "Sorry, the user password can't be less than 8 or longer than 16, please try again")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$$", message = "Sorry, the user password must have at least 1 uppercase, 1 lowercase, 1 number, please try again")
    @Column(columnDefinition = "varchar(16) not null")
    private String password;
    @Email(message = "Sorry, the user email must follow a valid email format, please try again")
    @Size(min = 5, max = 20, message = "Sorry, the user email can't less than 5 or longer than 20 characters, please try again")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String email;
    @NotEmpty(message = "Sorry, the user phone number can't be empty, please try again")
    @Size(min = 10, max = 10, message = "Sorry, the user phone number must be 10 digits, please try again")
    @Pattern(regexp = "^05[0-9].*$", message = "Sorry, the user phone number must start with 05 and contain only numbers, please try again")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String phoneNumber;
    @NotEmpty(message = "Sorry, the user country can't be empty, please try again")
    @Size(max = 60, message = "Sorry, the user country can't be longer than 60 characters, please try again")
    @Column(columnDefinition = "varchar(60) not null")
    private String country;
    @NotEmpty(message = "Sorry, the user city can't be empty, please try again")
    @Size(max = 60, message = "Sorry, the user city can't be empty, please try again")
    @Column(columnDefinition = "varchar(60) not null")
    private String city;
    @NotEmpty(message = "Sorry, the user gender can't be empty, please try again")
    @Pattern(regexp = "male|female", message = "Sorry, the user gender must be either 'male' or 'female', please try again")
    @Size(max = 6, message = "Sorry, the user gender can't be longer than 6 character, please try again")
    @Column(columnDefinition = "varchar(6) not null")
    private String gender;
    @Column(columnDefinition = "float default 0")
    private Double balance;
    @NotNull(message = "Sorry, the user date of birth can't be empty, please try again")
    @Column(columnDefinition = "date not null")
    private LocalDate dateOfBirth;
    @Column(columnDefinition = "datetime")
    private LocalDateTime registrationDate;
}
