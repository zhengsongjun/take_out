package com.zsj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsj.reggie.common.R;
import com.zsj.reggie.entry.Employee;
import com.zsj.reggie.serivce.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.digester.Digester;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        // 将页面提交的密码进行md5加密，
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 根据username进行密码比对
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<Employee>().eq(Employee::getUsername,employee.getUsername());
        Employee one = employeeService.getOne(employeeLambdaQueryWrapper);
        // 如果没有查询到则返回登录失败
        if(one == null){
            return R.error("未查询到用户");
        }
        // 查看是否被禁用了，如果被禁用了，返回员工被禁
        if(one.getStatus() == 0){
           return R.error("用户被禁用");
        }
        if(!one.getPassword().equals(password)) {
            return R.error("密码不正确");
        }

        // 登录成功，将员工id存入session并返回登录成功的结果
        request.getSession().setAttribute("employee",one.getId());;
        return R.success(one);
    }

    @PostMapping("logout")
    public R<String> layout(HttpServletRequest request){
        request.getSession().setAttribute("employee","");
        return R.success("退出成功");
    }

    @PostMapping()
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        System.out.println(employee.toString());
        employeeService.save(employee);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        // pagination
        Page page1 = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        employeeService.page(page1,employeeLambdaQueryWrapper);
        return R.success(page1);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        Long employeeId = (Long) request.getSession().getAttribute("employee");
        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable String id){
        Employee byId = employeeService.getById(id);
        return R.success(byId);
    }
}
