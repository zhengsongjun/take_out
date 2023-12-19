package com.zsj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zsj.reggie.common.R;
import com.zsj.reggie.entry.ShoppingCart;
import com.zsj.reggie.entry.User;
import com.zsj.reggie.serivce.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartController {

    private final UserService userService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> register(){
        return R.success(null);
    };


}
