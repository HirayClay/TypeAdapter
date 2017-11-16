package com.hiray.typeadapter.model;

import java.util.List;

/**
 * Created by CJJ on 2017/10/30.
 *
 * @author CJJ
 */

public class ColumnItem {
    public String desc;
    public String multiplier;
    public List<String> numbers;
    public List<String> multipliers;
    public int spanCount = 6;
    //投注类型,提交后台的字段
    public int type;


    public ColumnItem(String desc, String multiplier, List<String> numbers,
                      List<String> multipliers, int spanCount,int type) {
        this.desc = desc;
        this.multiplier = multiplier;
        this.numbers = numbers;
        this.multipliers = multipliers;
        this.spanCount = spanCount;
        this.type = type;
    }
}
