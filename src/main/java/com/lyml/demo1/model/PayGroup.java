package com.lyml.demo1.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

//表名
@TableName("paygroup")
public class PayGroup extends SuperEntity<PayGroup> {
    private String name;
    private String param;
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date createTime;
    private Integer sort;

    public String getName() {
        return name;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
