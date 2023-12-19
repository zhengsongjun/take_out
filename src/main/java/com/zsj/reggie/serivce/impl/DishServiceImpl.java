package com.zsj.reggie.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsj.reggie.common.R;
import com.zsj.reggie.dto.DishDto;
import com.zsj.reggie.entry.Dish;
import com.zsj.reggie.entry.DishFlavor;
import com.zsj.reggie.mapper.DishMapper;
import com.zsj.reggie.serivce.DishFlavorService;
import com.zsj.reggie.serivce.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    private final DishFlavorService dishFlavorService;
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        List<DishFlavor> collect = flavors.stream().map(item -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(collect);
    }

    @Override
    @Transactional
    public DishDto getByIdWithFlavor(Long id) {
        // dish 菜品基本信息
        Dish dish = this.getById(id);
        // 查询对应口味
        LambdaQueryWrapper<DishFlavor> dishFlavor = new LambdaQueryWrapper<DishFlavor>()
                                                    .eq(DishFlavor::getDishId, id);
        List<DishFlavor> list = dishFlavorService.list(dishFlavor);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        // 更新dish表
        this.updateById(dishDto);
        // 更新dishFlavor 先删除
        LambdaQueryWrapper<DishFlavor> eq = new LambdaQueryWrapper<DishFlavor>()
                                                .eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(eq);

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(item -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
}
