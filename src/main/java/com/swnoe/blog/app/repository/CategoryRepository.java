package com.swnoe.blog.app.repository;

import com.swnoe.blog.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category> findCategoryByParentIsNull();
}
