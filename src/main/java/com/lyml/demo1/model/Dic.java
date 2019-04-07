package com.lyml.demo1.model;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;

/**
 * 系统用户表
 */
@TableName("dic")
public class Dic extends SuperEntity {
    private String name;
    private Long pid;
    private String para;
    private String remark;
    private String dicIcon;
    private Integer sort;
    @TableField(exist=false)
    private String isParent;

    @TableField(exist=false)
    private Long count;

    @TableField(exist=false)
    private List<Dic> subDic;

    /**
     * 自定义填充的创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date ctime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDicIcon() {
        return dicIcon;
    }

    public void setDicIcon(String dicIcon) {
        this.dicIcon = dicIcon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<Dic> getSubDic() {
        return subDic;
    }

    public void setSubDic(List<Dic> subDic) {
        this.subDic = subDic;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }


}