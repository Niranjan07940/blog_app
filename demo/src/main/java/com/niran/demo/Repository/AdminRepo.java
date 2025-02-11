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
        String query = "DELETE FROM LikeBy WHERE posted_by = ?";
        String query1 = "DELETE FROM comment WHERE posted_by = ?";
        String query2 = "DELETE FROM blogpost WHERE posted_by = ?";
        String query3 = "DELETE FROM register2 WHERE uname = ?";
        String query4 = "DELETE FROM register1 WHERE uname = ?";
        int x = jdbcTemplate.update(query, uname);
        int x1 = jdbcTemplate.update(query1, uname);
        int x2 = jdbcTemplate.update(query2, uname);
        int x3 = jdbcTemplate.update(query3, uname);
        int x4 = jdbcTemplate.update(query4, uname);
        if(x!=0 && x1!=0 && x2!=0 && x3!=0 && x4!=0){
            status="success";
        }
        else{
            status="failure";
        }
        return status;
    }
}
