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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Sorry, the review rating can't be empty, please try again")
    @Positive(message = "Sorry, the review rating must be a positive number, please try again")
    @Min(value = 1, message = "Sorry, the review rating must be at least 1, please try again")
    @Max(value = 5, message = "Sorry, the review rating must be at most 5, please try again")
    @Column(columnDefinition = "float not null")
    private Double rating;
    @NotEmpty(message = "Sorry, the review content can't be empty, please try again")
    @Size(max = 150, message = "Sorry, the review content can't be longer than 150 characters, please try again")
    @Column(columnDefinition = "varchar(150) not null")
    private String content;
    @Column(columnDefinition = "datetime")
    private LocalDateTime reviewDate;
    @NotNull(message = "Sorry, the user id can't be empty, please try again")
    @Column(columnDefinition = "int not null")
    private Integer userId;
    @NotNull(message = "Sorry, the enrollment id can't be empty, please try again")
    @Column(columnDefinition = "int not null")
    private Integer EnrollmentId;
}
