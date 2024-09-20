package com.example.interview.controller;

import com.example.interview.model.Post;
import com.example.interview.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/search")
    public String searchPosts(@RequestParam(required = false) String company, 
                              @RequestParam(required = false) String role, 
                              Model model) {
        List<Post> posts = postService.searchPosts(company, role);
        model.addAttribute("posts", posts);
        return "index"; // Return the view for displaying posts based on search
    }

    @GetMapping("/post")
    public String postForm() {
        return "post_form"; // Return the form for submitting posts
    }

    @PostMapping("/post")
    public String postSubmit(@RequestParam String companyName, 
                             @RequestParam String role, 
                             @RequestParam String question, 
                             Model model) {
        Post post = new Post();
        post.setCompanyName(companyName);
        post.setRole(role);
        post.setQuestion(question);

        boolean saved = postService.save(post);
        if (saved) {
            return "redirect:/posts"; // Redirect to the list of posts
        } else {
            model.addAttribute("error", "Post can't be created. It already exists.");
            return "post_form"; // Stay on the form page with error message
        }
    }

    @GetMapping("/posts")
    public String viewPosts(Model model,
                            @RequestParam(required = false) String company,
                            @RequestParam(required = false) String role) {
        // Fetch all posts if no company or role specified, otherwise filter based on criteria
        List<Post> posts;
        if (company != null || role != null) {
            posts = postService.searchPosts(company, role);
        } else {
            posts = postService.findAll();
        }
        model.addAttribute("posts", posts);
        return "posts"; // Return the view for displaying all posts or filtered posts
    }
}
