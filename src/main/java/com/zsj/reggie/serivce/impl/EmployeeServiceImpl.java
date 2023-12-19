package com.zsj.reggie.serivce.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsj.reggie.entry.Employee;
import com.zsj.reggie.mapper.EmployeeMapper;
import com.zsj.reggie.serivce.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {
}
