package com.niran.demo.Controller;

import ch.qos.logback.core.joran.spi.HttpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {
    @RequestMapping(value="/welcome",method= RequestMethod.GET)
    public String welcome(){
        return "welcome to spring security";
    }

}
