package com.niran.demo.Controller;

import com.niran.demo.Beans.ForgotPassword;
import com.niran.demo.Beans.User;
import com.niran.demo.Service.UserService;
import com.niran.demo.Validation.*;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/register",method= RequestMethod.POST)
    public ResponseEntity<?> register(@Validated(RegistrationValidationGroup.class) @RequestBody User u, BindingResult result){
        String status="";
        Map<String,Object> map= new HashMap<>();
        if(result.hasErrors()){
            for(FieldError error:result.getFieldErrors()){
                map.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
        }
        status=userService.userRegister(u);
        if(status.equals("success")){
            status="user registered successfully";
            map.put("message",status);
            return new ResponseEntity<>(map,HttpStatus.valueOf(200));
        }
        map.put("message",status);
        return new ResponseEntity<>(map,HttpStatus.valueOf(400));
    }
    @RequestMapping(value="/sign",method=RequestMethod.POST)
    public ResponseEntity<?> sign(@Validated(LoginValidationGroup.class) @RequestBody User u, BindingResult result){
        String status="";
        Map<String,Object> map=new HashMap<>();
        if(result.hasErrors()){
            for(FieldError error:result.getFieldErrors()){
                map.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
        }
        status=userService.userVerify(u);

        if(status.equals(null)){
            status="user does not exist";
        }
        map.put("message",status);
        return ResponseEntity.ok(map);
    }
    @RequestMapping(value="/sendOtp",method=RequestMethod.POST)
    public ResponseEntity<?> sendOtp(@Validated(ForgotPasswordValidation.class) @RequestBody User u, BindingResult result) throws Exception{
        Map<String,Object> map=new HashMap<>();
        if(result.hasErrors()){
            for(FieldError error:result.getFieldErrors()){
                map.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
        }
        String message=userService.getOtp(u.getEmail());
        if(message.equals("success")){
            map.put("message","user exist otp sent successfully");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        map.put("message","user not found");
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(404));
    }
    @RequestMapping(value="/verifyOtp",method=RequestMethod.POST)
    public ResponseEntity<?> verifyOtp(@Validated(OtpValidation.class) @RequestBody ForgotPassword fp, BindingResult result){
        Map<String,Object> map=new HashMap<>();
        if(result.hasErrors()){
            for(FieldError error:result.getFieldErrors()){
                map.put(error.getField(),error.getDefaultMessage());
            }
            return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
        }
        String status=userService.verify(fp.getStoreOtp());
        if(status.equals("time expired")){
            map.put("message",status);
            return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
        }
        else if(status.equals("otp mismatched")){
            map.put("message",status);
            return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
        }
        map.put("message",status);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
    @RequestMapping(value="/updatePwd",method=RequestMethod.POST)
    public ResponseEntity<?> updatePassword(@Validated(UpdatePasswordValidation.class) @RequestBody User u, BindingResult result){
        Map<String,Object> map= new HashMap<>();
        if(result.hasErrors()){
            for(FieldError error:result.getFieldErrors()){
                map.put(error.getField(),error.getDefaultMessage());
            }
            return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
        }
        String status=userService.updatePwd(u);
        if(status.equals("success")){
            map.put("message","password updated Successfully");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        map.put("message","password not updated due to some reason");
        return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
    }

    @RequestMapping(value="/getUname",method=RequestMethod.POST)
    public ResponseEntity<?> userDetails(@RequestBody User u){
        List<User> user=userService.getUserDetails(u.getUname());
        if(user==null){
            return  new ResponseEntity<>("no user exist",HttpStatus.valueOf(400));
        }
        return new ResponseEntity<>(user,HttpStatus.OK);

    }
    @RequestMapping(value="/updateUser",method=RequestMethod.GET)
    public ResponseEntity<?> updateUser(@RequestBody User u){
        String status=userService.updateUserDetails(u);
        Map<String,Object> map=new HashMap<>();
        if(status.equals("success")){
            status="user successfully updated";
            map.put("message",status);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        map.put("message",status);
        return new ResponseEntity<>(map,HttpStatus.valueOf(400));
    }
}
