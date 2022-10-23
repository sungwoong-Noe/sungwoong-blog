package com.swnoe.blog.app.controller.category;

import com.swnoe.blog.app.service.category.CategoryService;
import com.swnoe.blog.domain.category.Category;
import com.swnoe.blog.dto.request.category.CategoryRegistForm;
import com.swnoe.blog.dto.request.category.ParentCategoryForm;
import com.swnoe.blog.dto.response.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category/edit")
    public String editForm(Model model) {
        model.addAttribute("parentCategoryList", categoryService.parentCategoryList());
        return "category/categoryEdit";
    }


    @PostMapping("/category/regist")
    @ResponseBody
    public CategoryResponse registCategory(@RequestBody CategoryRegistForm registForm) {

        CategoryResponse response = categoryService.regist(registForm);
        return response;
    }

}