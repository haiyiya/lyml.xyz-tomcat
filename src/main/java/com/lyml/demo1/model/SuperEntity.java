package com.lyml.demo1.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;


/**
 * 演示实体父类，可以包含公共字段
 */
public class SuperEntity<T extends Model> extends Model<T> {

    //数据库中的id字段名
    @TableId("id")
    private Long id;
    @TableField(exist = false)
    private String header;
    @TableField(exist = false)
    private String search;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}