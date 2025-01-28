package com.niran.demo.Beans;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ForgotPassword {
    private int fpid;
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
