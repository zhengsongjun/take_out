package com.zsj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsj.reggie.common.R;
import com.zsj.reggie.dto.SetmealDto;
import com.zsj.reggie.entry.Category;
import com.zsj.reggie.entry.Setmeal;
import com.zsj.reggie.entry.SetmealDish;
import com.zsj.reggie.serivce.CategoryService;
import com.zsj.reggie.serivce.SetmealDishService;
import com.zsj.reggie.serivce.SetmealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@RequiredArgsConstructor
@Slf4j
public class SetmealController {
    private final SetmealDishService setmealDish;
    private final SetmealService setmeal;
    private final CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody  SetmealDto setmealDto){
        setmeal.saveWithDish(setmealDto);
        return R.success("新增套餐成功！");
    }


    @GetMapping("/page")
    public R<Page> getPage(int page, int pageSize,String name){
        Page page1 = new Page(page,pageSize);
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<Setmeal>().like(name != null,Setmeal::getName, name);
        // 寻找套餐分类
        Page page2 = setmeal.page(page1, setmealQueryWrapper);
        List<Setmeal> records = page2.getRecords();
        List<SetmealDto> setmealDtoList = records.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Category byId = categoryService.getById(item.getCategoryId());
            setmealDto.setCategoryName(byId.getName());
            return  setmealDto;
        }).collect(Collectors.toList());
        page2.setRecords(setmealDtoList);
        return R.success(page2);
    }


    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable("status") Integer status,@RequestParam("ids") List<Long> ids) {
        setmeal.updateStatus(status,ids);
        return R.success("批量修改成功");
    };

    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable("id") Long id){
        SetmealDto setmealDto = new SetmealDto();
        Setmeal setmealById = setmeal.getById(id);
        LambdaQueryWrapper<SetmealDish> eq = new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> list = setmealDish.list(eq);
        BeanUtils.copyProperties(setmealById,setmealDto);
        setmealDto.setSetmealDishes(list);
        return R.success(setmealDto);
    }



    @DeleteMapping
    public R<String> deleteByIds(@RequestParam("ids") List<Long> ids){
        System.out.println(ids.toString());
        setmeal.deleteByIds(ids);
        return R.success("批量删除成功！");
    }


    @PutMapping()
    public R<String> updateSetMeal(@RequestBody SetmealDto setmealDto){
        setmeal.updateWithDish(setmealDto);
        return R.success("修改成功！");
    }

}
