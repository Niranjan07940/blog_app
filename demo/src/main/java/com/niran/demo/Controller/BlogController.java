package com.niran.demo.Controller;

import com.niran.demo.Beans.Blog;
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
    public ResponseEntity<?> getBlogs(){
        List<Blog> list=blogService.getAllBlogs();
        if(list==null){
            return new ResponseEntity<>("not existed",HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @RequestMapping(value="/getById/{id}",method=RequestMethod.GET)
    public ResponseEntity<?> getBlogById(@PathVariable("id") int id){
        Blog blogs=blogService.getBlogData(id);
        List<Blog> list=new ArrayList<>();
        list.add(blogs);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @RequestMapping(value="/getByUname",method=RequestMethod.POST)
    public ResponseEntity<?> getByUname(@RequestBody User u){
        List<Blog> lst=blogService.getBlogsByUname(u.getUname());
        if(lst==null){
            Map<String,Object> ls=new HashMap<>();
            ls.put("message","no blogs posted by yoy yet!");
            return new ResponseEntity<>(ls,HttpStatus.valueOf(400));
        }
        return new ResponseEntity<>(lst,HttpStatus.OK);
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
