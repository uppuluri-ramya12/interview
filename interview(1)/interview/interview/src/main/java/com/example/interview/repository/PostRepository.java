package com.example.interview.repository;

import com.example.interview.model.Post;
import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
	 List<Post> findByCompanyNameAndRole(String companyName, String role);

	    List<Post> findByCompanyName(String companyName);

	    List<Post> findByRole(String role);
}
