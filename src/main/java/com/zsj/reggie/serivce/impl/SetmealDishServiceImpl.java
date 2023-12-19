package com.zsj.reggie.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsj.reggie.dto.DishDto;
import com.zsj.reggie.entry.Dish;
import com.zsj.reggie.entry.DishFlavor;
import com.zsj.reggie.entry.SetmealDish;
import com.zsj.reggie.mapper.DishMapper;
import com.zsj.reggie.mapper.SetmealDishMapper;
import com.zsj.reggie.serivce.DishFlavorService;
import com.zsj.reggie.serivce.DishService;
import com.zsj.reggie.serivce.SetmealDishService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {

}
