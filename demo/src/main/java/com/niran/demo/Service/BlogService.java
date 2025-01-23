package com.niran.demo.Service;

import com.niran.demo.Beans.Blog;
import com.niran.demo.Repository.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class BlogService {
    @Autowired
    private BlogRepo blogRepo;
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
}
