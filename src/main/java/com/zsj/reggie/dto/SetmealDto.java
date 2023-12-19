package com.zsj.reggie.dto;

import com.zsj.reggie.entry.Setmeal;
import com.zsj.reggie.entry.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
