package com.niran.demo.Controller;

import com.niran.demo.Beans.Blog;
import com.niran.demo.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    @RequestMapping(value="/blogpost",method= RequestMethod.POST)
    public ResponseEntity<?> addBlog(@RequestParam("uname")String uname, @RequestParam("btitle")String btitle,
                                  @RequestParam("blog") String blog, @RequestPart("file")MultipartFile file){
        String status="";
        String fileName=file.getOriginalFilename();
        String location="src/main/resources/static/"+fileName;
        try{
            FileOutputStream fos = new FileOutputStream(location);
            byte[] bfile = file.getBytes();
            fos.write(bfile);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Blog b=new Blog();
        b.setBlog(blog);
        b.setBlogTitle(btitle);
        b.setUname(uname);
        b.setImglocation(location);
        status=blogService.setBlog(b);
        return new ResponseEntity<>(status,HttpStatus.CREATED);
    }
    @RequestMapping(value="/getImg",method=RequestMethod.POST)
    public ResponseEntity<?> getImg(@RequestParam("id") int id){
        Blog b=blogService.getbimg(id);
        Map<String,Object> response=new HashMap<>();
        if (b == null) {
            response.put("location","Not found");
            return null;

        }

        return  ResponseEntity.status(HttpStatus.FOUND).body(response.put("location",b.getImglocation()));
    }
}
