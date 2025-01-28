package com.niran.demo.Controller;

import com.niran.demo.Beans.User;
import com.niran.demo.Repository.UserRepo;
import com.niran.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/register",method= RequestMethod.POST)
    public String register(@RequestBody User u){
        String status="";
//        u.setPassword(password);
//        u.setUname(name);
//        u.setEmail(email);
//        u.setFname(fname);
//        u.setLname(lname);
//        u.setDateofbirth(date);
//        u.setGender(gender);
        status=userService.userRegister(u);
        return status;
    }
    @RequestMapping(value="/sign",method=RequestMethod.POST)
    public ResponseEntity<?> sign(@RequestParam("uname")String username, @RequestParam("pwd") String password){
        String status="";
        User u = new User();
        u.setPassword(password);
        u.setUname(username);
        ResponseEntity<?> response=userService.userVerify(u);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @RequestMapping(value="/sendOtp",method=RequestMethod.POST)
    public ResponseEntity<?> sendOtp(@RequestParam("email") String email) throws Exception{
        String message=userService.getOtp(email);
        return ResponseEntity.ok(message);
    }
    @RequestMapping(value="/verifyOtp",method=RequestMethod.POST)
    public ResponseEntity<?> verifyOtp(@RequestParam("otp") int Otp){
        String status=userService.verify(Otp);
        return ResponseEntity.ok(status);
    }
    @RequestMapping(value="/updatePwd",method=RequestMethod.POST)
    public ResponseEntity<?> updatePassword(@RequestParam("email") String email,@RequestParam("pwd")String password){
        User u = new User();
        u.setPassword(password);
        u.setEmail(email);
        String status=userService.updatePwd(u);
        return ResponseEntity.ok(status);
    }

}
