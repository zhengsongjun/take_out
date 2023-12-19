package com.zsj.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsj.reggie.entry.ShoppingCart;
import com.zsj.reggie.entry.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingMapper extends BaseMapper<ShoppingCart> {
}
