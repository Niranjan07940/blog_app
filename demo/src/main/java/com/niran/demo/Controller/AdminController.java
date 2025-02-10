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
}
