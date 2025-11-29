package com.tuwaiq.capstone2_mentoringsystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the category name can't be empty, please try again")
    @Size(min = 3, max = 35, message = "Sorry, the category name can't be less than 3 or longer than 35 characters, please try again")
    @Column(columnDefinition = "varchar(35) not null unique")
    private String name;
    @NotEmpty(message = "Sorry, the category description can't be empty, please try again")
    @Size(max = 200, message = "Sorry, the category description can't be longer than 200 characters, please try again")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;
}
