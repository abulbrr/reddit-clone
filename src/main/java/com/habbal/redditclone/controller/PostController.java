package com.habbal.redditclone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/${api.version}/posts")
public class PostController {

    @GetMapping
    public String getPosts() {
        return "success";
    }
}

