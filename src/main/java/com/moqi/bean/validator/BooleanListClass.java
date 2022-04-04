package com.moqi.bean.validator;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moqi
 * @date 4/4/22 19:09
 */
@Data
public class BooleanListClass {

    public final List<Boolean> booleanList = new ArrayList<>();

}
