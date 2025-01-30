package com.niran.demo.Beans;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;


public class Blog {
    private int blogId;
    @NotBlank(message="Image is required")
    private String imglocation;

    public String getImglocation() {
        return imglocation;
    }

    public void setImglocation(String imglocation) {
        this.imglocation = imglocation;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }



    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    @NotBlank(message="Username is required")
    @Size(min=4,max=20,message="Username must be between 4 and 20 characters")
    private String uname;
    @NotBlank(message="Blog text is required")
    private String blog;
    @NotBlank(message="blog title is required")
    private String blogTitle;

    private Timestamp postedOn;

    private Timestamp updatedOn;

    public Timestamp getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Timestamp postedOn) {
        this.postedOn = postedOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }
}