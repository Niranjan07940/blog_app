package com.niran.demo.Beans;

public class LikeComment {
    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }





    private int blogId;
    private int like;
    private String comment;
    private String uname;
    private int noComments;

    public int getNoComments() {
        return noComments;
    }

    public void setNoComments(int noComments) {
        this.noComments = noComments;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
