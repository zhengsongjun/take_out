package com.zsj.reggie.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsj.reggie.entry.ShoppingCart;
import com.zsj.reggie.entry.User;
import com.zsj.reggie.mapper.ShoppingMapper;
import com.zsj.reggie.mapper.UserMapper;
import com.zsj.reggie.serivce.ShoppingCartService;
import com.zsj.reggie.serivce.UserService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingMapper, ShoppingCart> implements ShoppingCartService {


}
