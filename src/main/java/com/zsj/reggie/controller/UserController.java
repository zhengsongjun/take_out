package com.zsj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsj.reggie.common.R;
import com.zsj.reggie.dto.SetmealDto;
import com.zsj.reggie.entry.Category;
import com.zsj.reggie.entry.Setmeal;
import com.zsj.reggie.entry.SetmealDish;
import com.zsj.reggie.entry.User;
import com.zsj.reggie.serivce.CategoryService;
import com.zsj.reggie.serivce.SetmealDishService;
import com.zsj.reggie.serivce.SetmealService;
import com.zsj.reggie.serivce.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public R<String> register(@RequestBody User user){
        LambdaQueryWrapper<User> eq = new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername());
        List<User> list = userService.list(eq);
        if(list.isEmpty()){
            String password = user.getPassword();
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            user.setPassword(password);
            userService.save(user);
            return R.success("注册成功！");
        }
        return R.error("用户名被占用！请更换用户名");
    };


    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, @RequestBody User user){
        LambdaQueryWrapper<User> eq = new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername());
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(password);
        User one = userService.getOne(eq);
        log.info(one.toString());
        if(one == null) {
            return R.error("无此账号");
        }
        if(!one.getPassword().equals(password)) {
           return R.error("密码错误");
        }
        request.getSession().setAttribute("employee",one.getId());;
        return R.success("登录成功");
    };





}
