package com.tuwaiq.capstone2_mentoringsystem.Service;

import com.tuwaiq.capstone2_mentoringsystem.Models.*;
import com.tuwaiq.capstone2_mentoringsystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ReviewService reviewService;

    public void addUser(User user) {
        user.setBalance(0.0);
        user.setRegistrationDate(LocalDateTime.now());
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Boolean updateUser(Integer id, User user) {
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null) {
            return false;
        } else {
            oldUser.setUsername(user.getUsername());
            oldUser.setPassword(user.getPassword());
            oldUser.setEmail(user.getEmail());
            oldUser.setPhoneNumber(user.getPhoneNumber());
            oldUser.setCountry(user.getCountry());
            oldUser.setCity(user.getCity());
            oldUser.setDateOfBirth(user.getDateOfBirth());
            oldUser.setGender(user.getGender());
            oldUser.setBalance(user.getBalance());
            userRepository.save(oldUser);
            return true;
        }
    }

    public Boolean deleteUser(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            return false;
        } else {
            userRepository.delete(user);
            return true;
        }
    }

    public UserProfile getUserInfo(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return null;
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(user.getUsername());
        userProfile.setPhone(userProfile.getPhone());
        Integer age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
        userProfile.setAge(age);
        userProfile.setGender(user.getGender());
        userProfile.setCountry(userProfile.getCountry());
        userProfile.setCity(user.getCity());
        userProfile.setUserId(user.getId());
        return userProfile;
    }
}
