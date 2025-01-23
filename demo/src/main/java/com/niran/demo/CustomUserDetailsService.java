package com.niran.demo;


import com.niran.demo.Beans.User;
import com.niran.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.verify(username);

        if(user==null){
            System.out.println("user not exist");
            throw new UsernameNotFoundException("user not found");
        }
        else{
            return new CustomUserDetails(user);
        }
    }
}
