package com.niran.demo.Controller;

import com.niran.demo.Beans.Blog;
import com.niran.demo.Beans.LikeComment;
import com.niran.demo.Beans.User;
import com.niran.demo.Service.BlogService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.*;


@RestController
@CrossOrigin
public class BlogController {

    @Value("${image.storage.path}")
    private String dirpath;
    @Value("${spring.mail.username}")
    private String frommail;
    @Autowired
    private BlogService blogService;
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value="/blogpost",method= RequestMethod.POST)
    public ResponseEntity<?> addBlog(@RequestParam("uname")String uname, @RequestParam("btitle")String btitle,
                                     @RequestParam("blog") String blog, @RequestPart("file")MultipartFile file){
        String status="";
        Map<String,Object> map=new HashMap<>();
        if(uname=="" || uname.trim().isEmpty()){
            map.put("uname","uname is required");
        }
        else if(btitle=="" || btitle.trim().isEmpty()){
            map.put("btitle","blog title is required");
        }
        else if(blog =="" || blog.trim().isEmpty()){
            map.put("blog","blog text is required");
        }
        else if(file.isEmpty()){
            map.put("file","file is required");
        }
        if(!map.isEmpty()){
            return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
        }
        String fileName=file.getOriginalFilename();
        try{
            FileOutputStream fos = new FileOutputStream(dirpath+"/"+fileName);
            byte[] bfile = file.getBytes();
            fos.write(bfile);
        }
        catch(Exception e){
            map.put("message","file not uploaded! try with lower size");
//            e.printStackTrace();
            return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
        }
        Blog b=new Blog();
        b.setBlog(blog);
        b.setBlogTitle(btitle);
        b.setUname(uname);
        b.setImglocation(fileName);
        status=blogService.setBlog(b);
        map.put("message",status);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
    @RequestMapping(value="/getImg",method=RequestMethod.POST)
    public ResponseEntity<?> getImg(@RequestParam("id") int id){
        Blog b=blogService.getbimg(id);
        String directoryPath=dirpath+"/"+b.getImglocation();
        File f = new File(directoryPath);
        if(!f.exists()){
            return ResponseEntity.ok("file not exist");
        }
        try{
            FileInputStream fis = new FileInputStream(f);
            byte [] imagebytes=fis.readAllBytes();
            String contentType = Files.probeContentType(f.toPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + directoryPath + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imagebytes);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while reading file");
        }
    }
    @RequestMapping(value="/getBlogs",method=RequestMethod.POST)
    public ResponseEntity<?> getBlogs(@RequestBody Blog b){
        List<Blog> list=blogService.getAllBlogs(b);
        if(list==null){
            return new ResponseEntity<>("not existed",HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @RequestMapping(value="/getById",method=RequestMethod.POST)
    public ResponseEntity<?> getBlogById(@RequestBody Blog b){
        Blog blogs=blogService.getBlogData(b);
        List<Blog> list=new ArrayList<>();
        list.add(blogs);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @RequestMapping(value="/getByUname",method=RequestMethod.POST)
    public ResponseEntity<?> getByUname(@RequestBody User u){
        List<Blog> lst=blogService.getBlogsByUname(u.getUname());
        if(lst==null){
            Map<String,Object> ls=new HashMap<>();
            ls.put("message","no blogs posted by you yet!");
            return new ResponseEntity<>(ls,HttpStatus.valueOf(400));
        }
        return new ResponseEntity<>(lst,HttpStatus.OK);
    }
    @RequestMapping(value="/deleteBlog",method=RequestMethod.POST)
    public ResponseEntity<?> deteteBlog(@RequestBody Blog b){
        Map<String,Object> map=new HashMap<>();
        Integer integerNum = Integer.valueOf(b.getBlogId());
        if(integerNum==null){
            map.put("message","blogId is required");
            return new ResponseEntity<>(map,HttpStatus.valueOf(400));
        }
        String status=blogService.deleteBlogById(b.getBlogId());
        if(status.equals("success")){
            map.put("message","blog deleted successfully");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        map.put("message",status);
        return new ResponseEntity<>(map,HttpStatus.valueOf(400));
    }
    @RequestMapping(value="/updateBlog",method=RequestMethod.POST)
    public ResponseEntity<?> updateBlog(@RequestParam("blogId") int id,@RequestParam("btitle") String btitle,
                                        @RequestParam("blog") String blog,@RequestPart("file")MultipartFile file){
        String status="";
        Map<String,Object> map=new HashMap<>();
        Integer integerNum = Integer.valueOf(id);
        if(integerNum==null){
            map.put("blog_id","blog id is required");
        }
        else if(btitle=="" || btitle.trim().isEmpty()){
            map.put("btitle","blog title is required");
        }
        else if(blog =="" || blog.trim().isEmpty()){
            map.put("blog","blog text is required");
        }
        else if(file.isEmpty()){
            map.put("file","file is required");
        }
        if(!map.isEmpty()){
            return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
        }
        String fileName=file.getOriginalFilename();
        try{
            FileOutputStream fos= new FileOutputStream(dirpath+"/"+fileName);
            byte [] bytes=file.getBytes();
            fos.write(bytes);
        }
        catch(Exception e){
            map.put("message","file not uploaded! try with lower size");
//            e.printStackTrace();
            return new ResponseEntity<>(map,HttpStatusCode.valueOf(400));
        }
        Blog b = new Blog();
        b.setBlogId(id);
        b.setBlogTitle(btitle);
        b.setImglocation(fileName);
        b.setBlog(blog);
        status=blogService.updateBlogData(b);
        if(status.equals("success")){
            map.put("message","blog updated successfully");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        map.put("message",status);
        return new ResponseEntity<>(map,HttpStatus.valueOf(400));
    }

    @RequestMapping(value="/comment",method=RequestMethod.POST)
    public ResponseEntity<?> comments(@RequestBody LikeComment likecomment){
        String status=blogService.comments(likecomment);
        Map<String,Object> map= new HashMap<>();
        if(status.equals("success")){
            map.put("message","successfully inserted");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        map.put("message","not inserted");
        return new ResponseEntity<>(map,HttpStatus.valueOf(400));
    }
    @RequestMapping(value="/like",method=RequestMethod.POST)
    public ResponseEntity<?> like(@RequestBody Blog b){
        String status=blogService.addLikes(b);
        Map<String,Object> map= new HashMap<>();
        if(status.equals("success")){
            map.put("message","successfully inserted");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        else if(status.equals("Liked")){
            map.put("message","user liked already");
            return new ResponseEntity<>(map,HttpStatus.valueOf(400));
        }
        map.put("message","not inserted");
        return new ResponseEntity<>(map,HttpStatus.valueOf(400));
    }
    @RequestMapping(value="/getLikesComments",method=RequestMethod.POST)
    public ResponseEntity<?> getLikeComment(@RequestBody Blog b){
        LikeComment lk=blogService.getlikescomments(b);
        Map<String,Object> map=new HashMap<>();
        map.put("likes",lk.getLike());
        map.put("comments",lk.getNoComments());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
    @RequestMapping(value="/getLikedBy",method=RequestMethod.POST)
    public ResponseEntity<?> getLikedBy(@RequestBody Blog b){
        Map<String,Object> map= new HashMap<>();
        List<LikeComment> lk= blogService.getLikes(b);
        if(lk==null){
            map.put("message","no likes avaliable");
            return new ResponseEntity<>(map,HttpStatus.valueOf(400));
        }
        return new ResponseEntity<>(lk,HttpStatus.OK);
    }
    @RequestMapping(value="/getComments",method =RequestMethod.POST)
    public ResponseEntity<?> getComments(@RequestBody Blog b){
        List<LikeComment> lk=blogService.getComments(b);
        Map<String,Object> map= new HashMap<>();
        if(lk==null){
            map.put("message","no comments found");
            return new ResponseEntity<>(map,HttpStatus.valueOf(400));
        }
        return new ResponseEntity<>(lk,HttpStatus.OK);
    }
    @RequestMapping(value="/deleteLike",method=RequestMethod.POST)
    public ResponseEntity<?> deleteLike(@RequestBody Blog b){
        String status=blogService.deleteLikeService(b);
        Map<String,Object> map= new HashMap<>();
        if(status.equals("success")){
            map.put("message","successfully desliked");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        map.put("message",status);
        return new ResponseEntity<>(map,HttpStatus.valueOf(400));
    }

    @RequestMapping(value="/test",method=RequestMethod.POST)
    public String sendEmial() throws Exception{
        String toEmail="niranjanreddyp769697@gmail.com";
        MimeMessage message= mailSender.createMimeMessage(); //Just to send message
        MimeMessageHelper helper=new MimeMessageHelper(message,true); //represents email message with attachment
        helper.setSentDate(new Date());
        helper.setFrom(frommail);
        helper.setTo(toEmail);
        helper.setSubject("open it to know it");
        helper.setText("Testing mail");
        File f = new File(dirpath+"/"+"varnasi.jpg");
        helper.addAttachment(f.getName(),f);
        mailSender.send(message);
        return "mail sent successfully";
    }
}
