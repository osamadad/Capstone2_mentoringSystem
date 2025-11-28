package com.tuwaiq.capstone2_mentoringsystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the admin username can't be empty, please try again")
    @Size(min = 3, max = 15, message = "Sorry, the admin username can't be less than 3 or longer than 15 characters, please try again")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;
    @NotEmpty(message = "Sorry, the admin password can't be empty, please try again")
    @Size(min = 8, max = 16, message = "Sorry, the admin password can't be less than 8 or longer than 16, please try again")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,16}$", message = "Sorry, the admin password must have at least 1 uppercase, 1 lowercase, 1 number, and at least 8 and at most 16 characters please try again")
    @Column(columnDefinition = "varchar(16) not null")
    private String password;
    @Column(columnDefinition = "datetime")
    private LocalDateTime registerDate;
}
