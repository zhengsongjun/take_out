package com.zsj.reggie.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsj.reggie.common.ForSaleException;
import com.zsj.reggie.dto.SetmealDto;
import com.zsj.reggie.entry.Dish;
import com.zsj.reggie.entry.Setmeal;
import com.zsj.reggie.entry.SetmealDish;
import com.zsj.reggie.mapper.DishMapper;
import com.zsj.reggie.mapper.SetmealMapper;
import com.zsj.reggie.serivce.DishService;
import com.zsj.reggie.serivce.SetmealDishService;
import com.zsj.reggie.serivce.SetmealService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    private final SetmealDishService setmealDish;
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        setmealDishList = setmealDishList.stream().map(item -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDish.saveBatch(setmealDishList);
    }

    @Transactional
    public void updateWithDish(SetmealDto setmealDto){
        Long id = setmealDto.getId();
        LambdaQueryWrapper<SetmealDish> eq = new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, id);
        setmealDish.remove(eq);
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes().stream().map(item -> {
            SetmealDish setmealDish1 = new SetmealDish();
            BeanUtils.copyProperties(item,setmealDish1);
            setmealDish1.setSetmealId(id);
            return setmealDish1;
        }).collect(Collectors.toList());
        setmealDish.saveBatch(setmealDishList);
        Setmeal setmeal1 = new Setmeal();
        BeanUtils.copyProperties(setmealDto,setmeal1);
        this.updateById(setmeal1);
    }

    @Transactional
    public void deleteByIds(List<Long> ids) {
        // 判断是否停售，如果不是停售就不能删除
        ids.stream().forEach(id -> {
            if(this.getById(id).getStatus()  == 1) {
                try {
                    throw new ForSaleException("不能删除，有正在出售的套餐！");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            this.removeById(id);
        });
        LambdaQueryWrapper<SetmealDish> in = new LambdaQueryWrapper<SetmealDish>().in(SetmealDish::getSetmealId, ids);
        setmealDish.remove(in);
    }

    @Transactional
    public void updateStatus(Integer status, List<Long> ids) {
        ids.stream().forEach(id -> {
            Setmeal byId = this.getById(id);
            byId.setStatus(status);
            this.updateById(byId);
        });
    }

}
