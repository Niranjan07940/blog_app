package com.niran.demo.Repository;

import com.niran.demo.Beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

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

    public List<User> getAdminByRepo() {
        String query="select register1.uname,register1.email,register1.register_role,register2.fname,register2.lname,register2.dob from " +
                "register1 INNER JOIN register2 ON register1.uname=register2.uname";
            List<User> user=jdbcTemplate.query(query,(rs,rowNum)->{
                User u = new User();
                u.setRole(rs.getString("register_role"));
                u.setDateofbirth(String.valueOf(rs.getDate("dob")));
                u.setFname(rs.getString("fname"));
                u.setUname(rs.getString("uname"));
                u.setEmail(rs.getString("email"));
                u.setLname(rs.getString("lname"));
                return u;
            });
           return user.isEmpty()?null:user;
    }
    @Transactional
    public String delete(String uname) {
        String status="";
        String query="delete from register1 where uname=?";
        try{
            jdbcTemplate.update(query, uname);
            status="success";
        }
        catch(Exception e){
            status="failure";
            e.printStackTrace();
        }
        return status;
    }
}
