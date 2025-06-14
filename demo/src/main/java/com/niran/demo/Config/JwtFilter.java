package com.niran.demo.Config;

import com.niran.demo.CustomUserDetails;
import com.niran.demo.CustomUserDetailsService;
import com.niran.demo.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private ApplicationContext ap;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        String JwtToken=authHeader.substring(7);
        String username=jwtService.extractUserName(JwtToken);

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(username!=null && authentication==null){
            //Authenticate
            UserDetails userDetails=ap.getBean(CustomUserDetailsService.class).loadUserByUsername(username);
            if(jwtService.validateToken(JwtToken,userDetails)){
                UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
                CustomUserDetails customDetails = (CustomUserDetails) userDetails;

            }
        }
        filterChain.doFilter(request,response);

    }
}
