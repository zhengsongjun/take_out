package com.zsj.reggie.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsj.reggie.dto.SetmealDto;
import com.zsj.reggie.entry.Dish;
import com.zsj.reggie.entry.Setmeal;
import com.zsj.reggie.entry.SetmealDish;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);
    void updateWithDish(SetmealDto setmealDto);
    void deleteByIds(List<Long> ids);

    void updateStatus(Integer status,List<Long> ids);
}
