package com.niran.demo.Service;

import com.niran.demo.Beans.Blog;
import com.niran.demo.Beans.User;
import com.niran.demo.Repository.BlogRepo;
import com.niran.demo.Repository.BlogRepo1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepo blogRepo;
    @Autowired
    private BlogRepo1 blogrepo1;
    public String setBlog(Blog b) {
        Date utilDate= new Date();
        Timestamp ts= new Timestamp(utilDate.getTime());
        b.setPostedOn(ts);
        b.setUpdatedOn(ts);
        String status= blogRepo.postBlog(b);
        return status;
    }


    public Blog getbimg(int id) {
        return blogRepo.getBImg(id);
    }


//    public List<Blog> getAllBlogs() {
//        List<Blog> blog=blogRepo.getAllBlogsFormrepo();
//        return blog;
//    }
    public List<Blog> getAllBlogsFromJpa(){
        List<Blog> blog=blogrepo1.findAll();
        return blog.isEmpty()?null:blog;
    }

    public Blog getBlogData(int blogId) {
        Blog b=blogRepo.getBlogDataById(blogId);
        return b;

    }

    public List<Blog> getBlogsByUname(String uname) {
        List<Blog> b=blogRepo.getAllBlogsByUname(uname);
        return b;
    }

    public String deleteBlogById(int blogId) {
        return blogRepo.deleteBlogByIdRepo(blogId);
    }
}
