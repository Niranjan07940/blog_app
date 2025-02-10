package com.niran.demo.Service;

import com.niran.demo.Beans.ForgotPassword;
import com.niran.demo.Beans.User;
import com.niran.demo.CustomUserDetails;
import com.niran.demo.Repository.UserRepo;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

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
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromMail;
    @Autowired
    private ForgotPassword fp1;

    public String userRegister(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        String status = userRepo.addUser(user);
        return status;
    }
    public Map<String,Object> userVerify(User user){
        Map<String,Object> map= new HashMap<>();
        try{
            Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUname(),user.getPassword()));
            if(authentication.isAuthenticated()){
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                String status=jwtService.generateToken(user.getUname());
//                String username = customUserDetails.getUsername();
//                String email = customUserDetails.getEmail();
//                String password=customUserDetails.getPassword();
//                String role= customUserDetails.getRole();
//                System.out.println(email+" "+username+" "+password+" "+role);

                if (status != null) {
                    map.put("status",status);
                    map.put("role",customUserDetails.getRole());
                }
//                return (status == null || status.isEmpty()) ? "user does not exist" : status;
                return map;
            }
        }
        catch (Exception e){
            System.out.println("user does not exist");
        }
        return  map;
    }

    public String getOtp(String email)throws Exception {
        User u=userRepo.verifyforOtp(email);
        if(u==null){
            return "failure";
        }
        int otp=generateOtp();
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setSentDate(new Date());
        helper.setFrom(fromMail);
        helper.setTo(email);
        helper.setSubject("otp verification");
        helper.setText("otp for verification :"+otp+" "+"this otp is valid only for "+new Date(System.currentTimeMillis()+ 30 * 1000));
        mailSender.send(message);
        fp1.setStoreOtp(otp);
        fp1.setExpirationTime(new Date(System.currentTimeMillis()+ 33 * 1000));
        return "success";
    }
    private int generateOtp(){
        Random random= new Random();
        return random.nextInt(100000,999999);
    }
    public String verify(int otp) {
        int verifyOtp=fp1.getStoreOtp();
        if(fp1.getExpirationTime().before(Date.from(Instant.now()))){
            return "time expired";
        }
        else if(verifyOtp!=otp){
            return "otp mismatched";
        }
        return "verified successfully";
    }
    public String updatePwd(User u) {
        u.setPassword(encoder.encode(u.getPassword()));
        System.out.println(u.getPassword());
        String status =userRepo.updateuserPwd(u);
        return status;
    }
    public String  updateUserDetails(User u) {
        return userRepo.updateUserDetailsRepo(u);
    }
    public List<User> getUserDetails(String uname) {
        List<User> u=userRepo.getUserDataRepo(uname);
        return u;
    }
}

    // Access the username and email
//            The method authentication.getPrincipal() is a part of the Spring Security Authentication interface and is used to retrieve the principal
//            (the currently authenticated user) from the Authentication object.

    //            getPrincipal() returns the principal of the current authentication, which is typically an object representing the authenticated user.
//            The returned object is often an instance of a class implementing the UserDetails interface, such as your CustomUserDetails class.
