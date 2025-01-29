package com.niran.demo.Controller;

import com.niran.demo.Beans.User;
import com.niran.demo.Repository.UserRepo;
import com.niran.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/register",method= RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody User u){
        String status="";
//        u.setPassword(password);
//        u.setUname(name);
//        u.setEmail(email);
//        u.setFname(fname);
//        u.setLname(lname);
//        u.setDateofbirth(date);
//        u.setGender(gender);
        status=userService.userRegister(u);
        Map<String,Object> map= new HashMap<>();
        map.put("message",status);
        return ResponseEntity.ok(map);
    }
    @RequestMapping(value="/sign",method=RequestMethod.POST)
    public ResponseEntity<?> sign(@RequestBody User u){
        String status="";
//        User u = new User();
//        u.setPassword(password);
//        u.setUname(username);
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
    public ResponseEntity<?> updatePassword(@RequestBody User u){
//        User u = new User();
//        u.setPassword(password);
//        u.setEmail(email);
        Map<String,Object> map= new HashMap<>();
        String status=userService.updatePwd(u);
        map.put("message",status);
        return ResponseEntity.ok(map);
    }

}
