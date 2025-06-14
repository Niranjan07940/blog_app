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

    public List<Blog> getAllBlogs(Blog blogs) {
        List<Blog> blog=blogRepo.getAllBlogsFormrepo();
        for(Blog b:blog){
            LikeComment lk=blogRepo.getLikeComment(b.getBlogId());
            b.setLikes(lk.getLike());
            b.setComments(lk.getNoComments());
            String status=blogRepo.checkLikes(b.getBlogId(),blogs.getUname());
            if(status.equals("Liked")){
                b.setLiked(true);
            }
            else{
                b.setLiked(false);
            }
        }
        return blog;
    }

    public Blog getBlogData(Blog blog) {
        Blog b=blogRepo.getBlogDataById(blog.getBlogId());
        LikeComment lk=blogRepo.getLikeComment(b.getBlogId());
        b.setLikes(lk.getLike());
        b.setComments(lk.getNoComments());
        String status=blogRepo.checkLikes(b.getBlogId(),blog.getUname());
        if(status.equals("Liked")){
            b.setLiked(true);
        }
        else{
            b.setLiked(false);
        }
        return b;
    }

    public List<Blog> getBlogsByUname(String uname) {
        List<Blog> blog=blogRepo.getAllBlogsByUname(uname);
//        List<Blog> blog=blogRepo.getAllBlogsFormrepo();
        for(Blog b:blog){
            LikeComment lk=blogRepo.getLikeComment(b.getBlogId());
            b.setLikes(lk.getLike());
            b.setComments(lk.getNoComments());
            String status=blogRepo.checkLikes(b.getBlogId(),uname);
            if(status.equals("Liked")){
                b.setLiked(true);
            }
            else{
                b.setLiked(false);
            }
        }
        return blog;

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
        String status=blogRepo.commentRepo(likecomment.getBlogId(),likecomment.getComment(),likecomment.getUname());
        return status;
    }
    public String addLikes(Blog b) {
        String status=blogRepo.addLikeRepo(b.getBlogId(),b.getUname());
        return status;

    }

    public LikeComment getlikescomments(Blog b) {
        LikeComment lk=blogRepo.getLikeComment(b.getBlogId());
        return lk;

    }
    public List<LikeComment> getComments(Blog b) {
        List<LikeComment> likeComment=blogRepo.getComments(b.getBlogId());
        return likeComment;
    }
    public String deleteLikeService(Blog b) {
        String status=blogRepo.deleteLikeRepo(b.getBlogId(),b.getUname());
        return status;
    }

    public List<LikeComment> getLikes(Blog b) {
        List<LikeComment> ls= blogRepo.getLiked(b.getBlogId());
        return ls;
    }
}
