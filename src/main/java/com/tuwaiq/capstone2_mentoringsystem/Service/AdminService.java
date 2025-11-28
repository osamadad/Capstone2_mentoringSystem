package com.tuwaiq.capstone2_mentoringsystem.Service;

import com.tuwaiq.capstone2_mentoringsystem.Models.Admin;
import com.tuwaiq.capstone2_mentoringsystem.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public void addAdmin(Admin admin){
        admin.setRegisterDate(LocalDateTime.now());
        adminRepository.save(admin);
    }

    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }

    public Boolean updateAdmin(Integer id, Admin admin){
        Admin oldAdmin = adminRepository.findAdminById(id);
        if (oldAdmin==null){
            return false;
        }else {
            oldAdmin.setUsername(admin.getUsername());
            oldAdmin.setPassword(admin.getPassword());
            adminRepository.save(oldAdmin);
            return true;
        }
    }

    public Boolean deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminById(id);
        if (admin==null){
            return false;
        }else {
            adminRepository.delete(admin);
            return true;
        }
    }
}
