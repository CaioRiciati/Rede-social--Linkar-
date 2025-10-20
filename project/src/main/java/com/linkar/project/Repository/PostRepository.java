package com.linkar.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkar.project.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
