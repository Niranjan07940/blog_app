package com.niran.demo.Service;

import com.niran.demo.Beans.User;
import com.niran.demo.CustomUserDetails;
import com.niran.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
   private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authManager;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTService jwtService;

    public String userRegister(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        String status = userRepo.addUser(user);
        return status;
    }
    public ResponseEntity<?> userVerify(User user){
        Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUname(),user.getPassword()));
        if(authentication.isAuthenticated()){
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String username = customUserDetails.getUsername();
            String email = customUserDetails.getEmail();
            String password=customUserDetails.getPassword();
            return new ResponseEntity<>(jwtService.generateToken(user.getUname()), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("not authenticated",HttpStatus.NOT_FOUND);
    }
}

    // Access the username and email
//            The method authentication.getPrincipal() is a part of the Spring Security Authentication interface and is used to retrieve the principal
//            (the currently authenticated user) from the Authentication object.

    //            getPrincipal() returns the principal of the current authentication, which is typically an object representing the authenticated user.
//            The returned object is often an instance of a class implementing the UserDetails interface, such as your CustomUserDetails class.
