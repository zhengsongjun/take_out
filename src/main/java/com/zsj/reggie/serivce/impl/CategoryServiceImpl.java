package com.zsj.reggie.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsj.reggie.common.CustomException;
import com.zsj.reggie.entry.Category;
import com.zsj.reggie.entry.Dish;
import com.zsj.reggie.entry.Setmeal;
import com.zsj.reggie.mapper.CategoryMapper;
import com.zsj.reggie.serivce.CategoryService;
import com.zsj.reggie.serivce.DishService;
import com.zsj.reggie.serivce.SetmealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    private final  DishService dishService;
    private final SetmealService setmealService;
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<Dish>().eq(Dish::getCategoryId,id);
        // 查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        int count = dishService.count(dishLambdaQueryWrapper);
        log.info("dish count {}",count);
        if(count > 0){
            throw new CustomException("当前关联下关联了菜品，不能删除");
        }
        // 查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        log.info("setMeal count {}",count1);
        if(count1 > 0){
            throw new CustomException("当前关联下关联了套餐，不能删除");
        }
        // 正常删除
        super.removeById(id);
    }
}
