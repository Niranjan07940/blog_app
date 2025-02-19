package com.niran.demo.Repository;

import com.niran.demo.Beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Transactional
    public String addUser(User u) {
        String status="";
        try{
            String query1="insert into register1 values(?,?,?,?)";
            String query2="insert into register2 values(?,?,?,?,?)";
            Object arr1[]={u.getUname(),u.getEmail(),u.getPassword(),u.getRole()};
            Object arr2[]={u.getUname(),u.getFname(),u.getLname(),Date.valueOf(u.getDateofbirth()),u.getGender()};
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
    public User verify(String username) {
        String query="select *from register1 where uname=?";
        Object arr[]={username};
        List<User> us=jdbcTemplate.query(query,arr,(rs,rowNum)->{
            User user1=new User();
            user1.setUname(rs.getString(1));
            user1.setEmail(rs.getString(2));
            user1.setPassword(rs.getString(3));
            user1.setRole(rs.getString(4));
            return user1;
        });
        return us.isEmpty()?null:us.get(0);
    }

    public User  verifyforOtp(String email) {
        String query="select *from register1 where email=?";
        Object arr[]={email};
        List<User> user=jdbcTemplate.query(query,arr,(rs,rowNum)->{
            User u = new User();
            u.setEmail(rs.getString(3));
            return u;
        });

        return user.isEmpty()?null:user.get(0);
    }

    public String updateuserPwd(User u) {
        String query="update register1 set password=? where email=?";
        Object arr[]={u.getPassword(),u.getEmail()};
        String status="";
        int x=jdbcTemplate.update(query,arr);
        if(x==1){
            status="success";
        }
        else{
            status="failure";
        }
        return status;
    }

    @Transactional
    public String updateUserDetailsRepo(User u) {
        String query1="update register1 set email=? where uname=?";
        String query2="update register2 set fname=?,lname=?,dob=? where uname=?";
        String status="";
        try{
            Object arr1[]={u.getEmail(),u.getUname()};
            Object arr2[]={u.getFname(),u.getLname(),Date.valueOf(u.getDateofbirth()),u.getUname()};
            int x1=jdbcTemplate.update(query1,arr1);
            int x2=jdbcTemplate.update(query2,arr2);
            if(x1==1 && x2==1){
                status="success";
            }
        }
        catch(Exception e){
            status="email already exist!";
            e.getMessage();
        }
        return status;
    }

    public List<User> getUserDataRepo(String uname) {
        String query="select register1.email,register2.fname,register2.lname,register2.dob from " +
                "register1 INNER JOIN register2 ON register1.uname=register2.uname where register1.uname=?";
        Object arr[]={uname};
            List<User> user=jdbcTemplate.query(query,arr,(rs,rowNum)->{
                User u = new User();
                u.setEmail(rs.getString("email"));
                u.setFname(rs.getString("fname"));
                u.setLname(rs.getString("lname"));
                u.setLname(rs.getString("uname"));
                u.setLname(rs.getString("gender"));
                u.setDateofbirth(String.valueOf(rs.getDate("dob")));
                return u;
            });
            return user.isEmpty()?null:user;
    }
}
