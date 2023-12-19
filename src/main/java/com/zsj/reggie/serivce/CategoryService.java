package com.zsj.reggie.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsj.reggie.entry.Category;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
