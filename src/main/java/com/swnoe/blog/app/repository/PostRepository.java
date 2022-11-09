package com.swnoe.blog.app.repository;

import com.swnoe.blog.domain.post.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {

    List<Posts> findPostsByCategoryId(Long categoryId);
}
