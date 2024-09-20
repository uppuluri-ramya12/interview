package com.example.interview.service;

import com.example.interview.model.Post;
import com.example.interview.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> searchPosts(String company, String role) {
        if (company != null && role != null) {
            return postRepository.findByCompanyNameAndRole(company, role);
        } else if (company != null) {
            return postRepository.findByCompanyName(company);
        } else if (role != null) {
            return postRepository.findByRole(role);
        } else {
            return postRepository.findAll();
        }
    }
    public boolean save(Post post) {
        // Check for duplicates by companyName, role, and question
        List<Post> existingPosts = postRepository.findByCompanyNameAndRole(
            post.getCompanyName(), post.getRole());
        if (!existingPosts.isEmpty()) {
            return false; // Indicate that the post already exists
        }
        postRepository.save(post);
        return true; // Indicate successful save
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    private boolean isDuplicate(Post post) {
        List<Post> existingPosts = postRepository.findByCompanyNameAndRole(post.getCompanyName(), post.getRole());
        for (Post existingPost : existingPosts) {
            if (existingPost.getQuestion().equalsIgnoreCase(post.getQuestion())) {
                return true;
            }
        }
        return false;
    }
}
