package com.niran.demo.Service;

import com.niran.demo.Beans.Blog;
import com.niran.demo.Beans.LikeComment;
import com.niran.demo.Repository.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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


    public List<Blog> getAllBlogs() {
        List<Blog> blog=blogRepo.getAllBlogsFormrepo();
        return blog;
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

    public String updateBlogData(Blog b) {
        Date utilDate= new Date();
        Timestamp ts= new Timestamp(utilDate.getTime());
        b.setUpdatedOn(ts);
        return blogRepo.getUpdated(b);
    }

    public String comments(LikeComment likecomment) {
        String status=blogRepo.commentRepo(likecomment.getBlogId(),likecomment.getComment(),likecomment.getPostedBy());
        return status;
    }
    public String addLikes(LikeComment likecomment) {
        String status=blogRepo.addLikeRepo(likecomment.getBlogId(),likecomment.getLike(),likecomment.getPostedBy());
        return status;

    }
}
