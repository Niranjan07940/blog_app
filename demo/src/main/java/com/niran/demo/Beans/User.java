package com.niran.demo.Beans;

import com.niran.demo.Validation.ForgotPasswordValidation;
import com.niran.demo.Validation.LoginValidationGroup;
import com.niran.demo.Validation.RegistrationValidationGroup;
import com.niran.demo.Validation.UpdatePasswordValidation;
import jakarta.validation.constraints.*;

public class User {
    @NotBlank(message="Uname cannot be empty",groups = {RegistrationValidationGroup.class})
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters",groups = {RegistrationValidationGroup.class,LoginValidationGroup.class})
    private String uname;
    @NotBlank(message="Password cannot be empty",groups = {RegistrationValidationGroup.class,LoginValidationGroup.class, UpdatePasswordValidation.class})
    @Size(min = 6,message="password must be minimum of 6 characters",groups = {RegistrationValidationGroup.class,LoginValidationGroup.class,UpdatePasswordValidation.class})
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message="Password must contain at least one uppercase letter, one lowercase letter, " +
                    "one number, and one special character",groups = {RegistrationValidationGroup.class,LoginValidationGroup.class,UpdatePasswordValidation.class})
    private String password;
    @NotBlank(message="Email is required",groups = {RegistrationValidationGroup.class,UpdatePasswordValidation.class, ForgotPasswordValidation.class})
    @Email(message="Invalid email format",groups = {UpdatePasswordValidation.class,RegistrationValidationGroup.class, ForgotPasswordValidation.class})
    private String email;
    @NotBlank(message="Gender is required",groups = {RegistrationValidationGroup.class})
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    @NotBlank(message = "Date of Birth is required",groups = {RegistrationValidationGroup.class})
    private String dateofbirth;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @NotBlank(message="First Name is required",groups = {RegistrationValidationGroup.class})
    private String fname;
    @NotBlank(message="Last Name is required",groups = {RegistrationValidationGroup.class})
    private String lname;

    public String getUname() {

        return uname;
    }
    public void setUname(String uname) {

        this.uname = uname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
