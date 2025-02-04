package com.niran.demo.Repository;

import com.niran.demo.Beans.Blog;
import com.niran.demo.Beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class BlogRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String postBlog(Blog b) {
        String status="";
        try{
            String query="insert into blogpost(posted_by,blog_title,blog_text,image_path,posted_on,update_on) values(?,?,?,?,?,?)";
            Object arr[]={b.getUname(),b.getBlogTitle(),b.getBlog(),b.getImglocation(),b.getPostedOn(),b.getUpdatedOn()};
            int x=jdbcTemplate.update(query,arr);
            if(x==1){
                status="blog successfully added";
            }
        }
        catch(Exception e){
            status="blog not added";
        }
        return status;
    }

    public Blog getBImg(int id) {
        String query = "select *from blogpost where blog_id=?";
        Object arr[] = {id};
        List<Blog> blog = jdbcTemplate.query(query, arr, (rs, rowNum) -> {
            Blog b1 = new Blog();
            b1.setImglocation(rs.getString(5));
            return b1;
        });
        return blog.isEmpty()?null:blog.get(0);
    }


    @Transactional
    public List<Blog> getAllBlogsFormrepo() {
        String status="";
        String query="select *from blogpost";
        List<Blog> blogs=jdbcTemplate.query(query,(rs,rowNum)->{
            Blog b= new Blog();
            b.setUname(rs.getString(1));
            b.setBlogId(rs.getInt(2));
            b.setBlogTitle(rs.getString(3));
            b.setBlog(rs.getString(4));
            b.setImglocation(rs.getString(5));
            b.setPostedOn(rs.getTimestamp(6));
            b.setUpdatedOn(rs.getTimestamp(7));
            return b;
        });
        return blogs.isEmpty()?null:blogs;
    }
    public Blog getBlogDataById(int blogId) {
        String query="select *from blogpost where blog_id=?";
        Object arr[]={blogId};
        List<Blog> list1=jdbcTemplate.query(query,arr,(rs,rowNum)->{
            Blog b = new Blog();
            b.setBlogTitle(rs.getString(3));
            b.setBlog(rs.getString(4));
            b.setImglocation(rs.getString(5));
            b.setPostedOn(rs.getTimestamp(6));
            b.setUpdatedOn(rs.getTimestamp(7));
            return b;
        });
        return list1.isEmpty()?null:list1.get(0);
    }

    public List<Blog> getAllBlogsByUname(String uname) {
        String query="select *from blogpost where posted_by=?";
        Object arr[]={uname};
        List<Blog> blog=jdbcTemplate.query(query,arr,(rs,rowNum)->{
            Blog b=new Blog();
            b.setUname(rs.getString(1));
            b.setBlogTitle(rs.getString(3));
            b.setBlog(rs.getString(4));
            b.setImglocation(rs.getString(5));
            b.setPostedOn(rs.getTimestamp(6));
            b.setUpdatedOn(rs.getTimestamp(7));
            return b;
        });
        return blog.isEmpty()?null:blog;

    }
}
