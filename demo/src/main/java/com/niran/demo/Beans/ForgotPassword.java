package com.niran.demo.Beans;

import com.niran.demo.Validation.ForgotPasswordValidation;
import com.niran.demo.Validation.OtpValidation;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ForgotPassword {
    private int fpid;
    @NotNull(message="Otp is required",groups={OtpValidation.class})
    @Min(value=6,groups = {OtpValidation.class})
//    @Max(value = 6,groups = {OtpValidation.class})
    private int storeOtp;

    public int getFpid() {
        return fpid;
    }

    public void setFpid(int fpid) {
        this.fpid = fpid;
    }

    public int getStoreOtp() {
        return storeOtp;
    }

    public void setStoreOtp(int storeOtp) {
        this.storeOtp = storeOtp;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    private Date expirationTime;
}
