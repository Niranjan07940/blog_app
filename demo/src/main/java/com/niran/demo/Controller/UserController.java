package com.niran.demo.Controller;

import com.niran.demo.Beans.User;
import com.niran.demo.Repository.UserRepo;
import com.niran.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/register",method= RequestMethod.POST)
    public String register(@RequestParam("uname") String name,@RequestParam("mail")String email,@RequestParam("pwd") String password,
                           @RequestParam("fname")String fname,@RequestParam("lname")String lname,@RequestParam("date")String date,
                           @RequestParam("gen")String gender){
        User u = new User();
        String status="";
        u.setPassword(password);
        u.setUname(name);
        u.setEmail(email);
        u.setFname(fname);
        u.setLname(lname);
        u.setDateofbirth(date);
        u.setGender(gender);
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
}
