package com.zsj.reggie.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsj.reggie.common.CustomException;
import com.zsj.reggie.entry.Category;
import com.zsj.reggie.entry.Dish;
import com.zsj.reggie.entry.DishFlavor;
import com.zsj.reggie.entry.Setmeal;
import com.zsj.reggie.mapper.CategoryMapper;
import com.zsj.reggie.mapper.DishFlavorMapper;
import com.zsj.reggie.serivce.CategoryService;
import com.zsj.reggie.serivce.DishFlavorService;
import com.zsj.reggie.serivce.DishService;
import com.zsj.reggie.serivce.SetmealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {

}
