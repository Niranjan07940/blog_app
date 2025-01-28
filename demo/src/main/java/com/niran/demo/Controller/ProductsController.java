package com.niran.demo.Controller;

import com.niran.demo.Beans.Product;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.sql.*;
import java.util.*;
import java.util.Date;

@RestController
public class ProductsController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Product> products=new ArrayList<>(List.of(
            new Product(7,"iphone","100000"),
            new Product(8,"poco","30000")
    ));
    @RequestMapping(value="/products",method= RequestMethod.GET)
    public List<Product> getProducts(){
        return products;
    }
    @RequestMapping(value="/addproducts",method=RequestMethod.POST)
    public Product addProduct(@RequestBody Product product){
        products.add(product);
        return product;
    }
    @RequestMapping(value="/csrf",method=RequestMethod.GET)
    public CsrfToken getToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
    @RequestMapping(value="/test1",method=RequestMethod.GET)
    public ResponseEntity<?> getData(){
//        return ResponseEntity.ok("is testing");
//        return new ResponseEntity<>(HttpStatus.CREATED);  // 201 created

//        return new ResponseEntity<>("is testing",HttpStatus.ACCEPTED);
//        return ResponseEntity.status(HttpStatus.FOUND).body("is testing");   these both are similar
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + "Mainpath" + "\"")
//                .contentType(MediaType.parseMediaType("image/png"))
//                .body("is testing");
        Map<String,Object> map= new HashMap<>();
        map.put("v1","Niranjan");
        map.put("v2","Laddu");
        return ResponseEntity.status(HttpStatus.FOUND).body(map);
    }

}
