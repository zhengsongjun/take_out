package com.zsj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsj.reggie.common.R;
import com.zsj.reggie.dto.DishDto;
import com.zsj.reggie.entry.Category;
import com.zsj.reggie.entry.Dish;
import com.zsj.reggie.serivce.CategoryService;
import com.zsj.reggie.serivce.DishFlavorService;
import com.zsj.reggie.serivce.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DIshController {

    private final DishService dishService;

    private final DishFlavorService dishFlavorService;

    private final CategoryService categoryService;
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page> getPage(String name,int pageSize,int page){
        Page pageWrapper = new Page(page, pageSize);
        Page dishDtoList = new Page();
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<Dish>()
                .like(StringUtils.isNotEmpty(name), Dish::getName, name);
        dishService.page(pageWrapper, dishLambdaQueryWrapper);
        BeanUtils.copyProperties(pageWrapper,dishDtoList,"records");
        List<Dish> dishListRecord = pageWrapper.getRecords();
        List<Object> collect = dishListRecord.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoList.setRecords(collect);
        return R.success(dishDtoList);
    };

    @GetMapping("/{id}")
    public R<DishDto> getDish(@PathVariable Long id){
        return R.success(dishService.getByIdWithFlavor(id));
    }

    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    @GetMapping("list")
    public R<List<Dish>> toCategoryIdGetDish(Dish dish){
        LambdaQueryWrapper<Dish> eq = new LambdaQueryWrapper<Dish>()
                                        .eq(dish.getCategoryId() != null,Dish::getCategoryId, dish.getCategoryId());
        List<Dish> list = dishService.list(eq);
        return R.success(list);
    }
}
