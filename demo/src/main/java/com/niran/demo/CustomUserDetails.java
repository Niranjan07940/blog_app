package com.niran.demo;

import com.niran.demo.Beans.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;


public class CustomUserDetails implements UserDetails {
    private User user;
    public CustomUserDetails(User user)
    {
        this.user=user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public String getEmail(){
        return user.getEmail();
    }
   public String getRole(){
        return user.getRole();
   }

}
