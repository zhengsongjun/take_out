package com.zsj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsj.reggie.common.R;
import com.zsj.reggie.entry.Category;
import com.zsj.reggie.serivce.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> getCategoryPage(int page,int pageSize){
        Page page1 = new Page(page,pageSize);
        Page page2 = categoryService.page(page1);
        return R.success(page2);
    }


    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("添加成功");
    }


    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<Category>> getCategoryList(Category category){
        LambdaQueryWrapper<Category> eq = new LambdaQueryWrapper<Category>()
                .eq(category.getType() != null,Category::getType, category.getType())
                .orderByAsc(Category::getSort)
                .orderByAsc(Category::getUpdateTime);
        List<Category> list = categoryService.list(eq);
        return R.success(list);
    }
}
