package com.zsj.reggie.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsj.reggie.dto.DishDto;
import com.zsj.reggie.entry.Category;
import com.zsj.reggie.entry.Dish;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
