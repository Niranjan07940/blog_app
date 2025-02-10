package com.niran.demo.Service;

import com.niran.demo.Beans.User;
import com.niran.demo.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public String addAdmin(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return adminRepo.addAdmin(user);

    }
}
