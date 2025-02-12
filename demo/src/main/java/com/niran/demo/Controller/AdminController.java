package com.niran.demo.Controller;

import com.niran.demo.Beans.User;
import com.niran.demo.Service.AdminService;
import com.niran.demo.Validation.RegistrationValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;
    @RequestMapping(value="/adminRegister",method= RequestMethod.POST)
    public ResponseEntity<?> registerAdmin(@Validated(RegistrationValidationGroup.class) @RequestBody User u, BindingResult result){
        String status="";
        Map<String,Object> map= new HashMap<>();
        if(result.hasErrors()){
            for(FieldError error:result.getFieldErrors()){
                map.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(map, HttpStatusCode.valueOf(400));
        }
        status=adminService.addAdmin(u);
        if(status.equals("success")){
            map.put("message","Admin added successfully");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        map.put("message",status);
        return new ResponseEntity<>(map,HttpStatus.valueOf(400));
    }
    @RequestMapping(value="/allUsers",method=RequestMethod.GET)
    public ResponseEntity<?> getAdmins(){
        Map<String,Object> map = new HashMap<>();
        List<User> users= adminService.getAdmins();
        if(users==null){
            map.put("message","no users or admins  found");
            return new ResponseEntity<>(map,HttpStatus.valueOf(400));
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @RequestMapping(value="/deleteUser",method=RequestMethod.POST)
    public ResponseEntity<?> deleteUser(@RequestBody User u){
        Map<String,Object> map=new HashMap<>();
        String status=adminService.deleteUsers(u);
        if(status.equals("success")){
            map.put("message","user deleted successfully");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        map.put("message",status);
        return new ResponseEntity<>(map,HttpStatus.valueOf(400));
    }
}
