package com.niran.demo.Repository;

import com.niran.demo.Beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public class AdminRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public String addAdmin(User u) {
        String status="";
        try{
            String query1="insert into register1 values(?,?,?,?)";
            String query2="insert into register2 values(?,?,?,?,?)";
            Object arr1[]={u.getUname(),u.getEmail(),u.getPassword(),u.getRole()};
            Object arr2[]={u.getUname(),u.getFname(),u.getLname(), Date.valueOf(u.getDateofbirth()),u.getGender()};
            int x1=jdbcTemplate.update(query1,arr1);
            int x2=jdbcTemplate.update(query2,arr2);
            if(x1==1 && x2==1){
                status="success";
            }
        }
        catch(Exception e){
            status="username or email is already existed";

        }
        return status;
    }
}
