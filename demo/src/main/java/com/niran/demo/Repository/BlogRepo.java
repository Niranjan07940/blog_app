package com.niran.demo.Repository;

import com.niran.demo.Beans.Blog;
import com.niran.demo.Beans.LikeComment;
import com.niran.demo.Beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.Like;
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
                b.setBlogId(rs.getInt(2));
                b.setBlogTitle(rs.getString(3));
                b.setBlog(rs.getString(4));
                b.setImglocation(rs.getString(5));
                b.setPostedOn(rs.getTimestamp(6));
                b.setUpdatedOn(rs.getTimestamp(7));
                return b;
            });
            return blog.isEmpty()?null:blog;
        }

    public String deleteBlogByIdRepo(int blogId) {
        String status="";
        String query="delete from blogpost where blog_id=?";
        Object arr[]={blogId};
        int x=jdbcTemplate.update(query,arr);
        if(x==1){
            status="success";
        }
        else{
            status="no blog is there with given id!";
        }
        return status;
    }

    @Transactional
    public String getUpdated(Blog b) {
        String status="";
        String query="update blogpost set blog_title=?,blog_text=?,image_path=?,update_on=? where blog_id=?";
        Object arr[]={b.getBlogTitle(),b.getBlog(),b.getImglocation(),b.getUpdatedOn(),b.getBlogId()};
        try{
            int x=jdbcTemplate.update(query,arr);
            if(x==1){
                status="success";
            }
        }
        catch(Exception e){
            status="blog not updated";
            e.printStackTrace();
        }
        return status;
    }

    @Transactional
    public String commentRepo(int blogId, String comment, String postedBy) {
        String status="";
        String query="INSERT INTO comment(blog_id,comments,posted_by) VALUES (?,?,?)";
        Object arr[]={blogId,(comment == null || comment.trim().isEmpty())? "no-comment" : comment,postedBy};
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
    public String addLikeRepo(int blogId,String postedBy) {
        String status="";
        String query1="select *from LikeBy where blog_id=? and posted_by =?";
        Object arr1[]={blogId,postedBy};
        List<LikeComment> lk= jdbcTemplate.query(query1,arr1,(rs,rowNum)->{
            LikeComment likeComment = new LikeComment();
            likeComment.setUname(rs.getString(3));
            likeComment.setBlogId(rs.getInt(1));
            likeComment.setLike(rs.getInt(2));
            return likeComment;
        });
        System.out.println(lk);
        if(lk!=null){
            status="liked";
            return status;
        }
        String query="INSERT INTO  LikeBy(blog_id,likes,posted_by) VALUES (?,?,?)";
        Object arr[]={blogId,1,postedBy};
        int x=jdbcTemplate.update(query,arr);
        if(x==1){
            status="success";
        }
        else{
            status="failure";
        }
        return status;
    }
    public LikeComment getLikeComment(int blogId) {
        String query1="SELECT SUM(likes) AS total_likes from LikeBy WHERE blog_id =?";
        String query2="SELECT COUNT(comment) FROM comment WHERE blog_id = ?";
        Object arr[]={blogId};
        LikeComment lk= new LikeComment();
        try{
            int x=jdbcTemplate.queryForObject(query1,arr,Integer.class);
            int x1=jdbcTemplate.queryForObject(query2,arr,Integer.class);
            lk.setLike(x);
            lk.setNoComments(x1);
        }
        catch(Exception e){
            e.getMessage();
        }
        return lk;

    }

    public List<LikeComment> getComments(int blogId) {
        String query="select *from comment where blog_id=?";
        Object arr[]={blogId};
        List<LikeComment> comments=jdbcTemplate.query(query,arr,(rs,rowNum)->{
            LikeComment lk= new LikeComment();
            lk.setBlogId(rs.getInt(1));
            lk.setComment(rs.getString(2));
            lk.setUname(rs.getString(3));
            return lk;
        });
        return comments.isEmpty()?null:comments;
    }

    @Transactional
    public String deleteLikeRepo(int blogId, String uname) {
        String status="";
        String query="delete from LikeBy where blog_id=? and posted_by=?";
        Object arr[] ={blogId,uname};
        try{
            jdbcTemplate.update(query,arr);
            status="success";
        }
        catch(Exception e){
            status="failure to deslike";
            e.printStackTrace();
        }
        return status;
    }
}
