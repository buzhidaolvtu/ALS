package com.als.framework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lvtu on 2017/6/28.
 */
@Controller
public class ALSController {

    private final static Logger logger = LoggerFactory.getLogger(ALSController.class);

    @RequestMapping("/init")
    public String init() {
        return "ALS";
    }

    @RequestMapping("/helloEncrypt")
    @ResponseBody
    public String encrypt(String username, String password) {
        logger.info("username:{}", username);
        logger.info("password:{}", password);
        return "main_role";
    }

}
