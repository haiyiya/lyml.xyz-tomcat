package com.lyml.demo1.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lyml.demo1.SuperMapper;
import com.lyml.demo1.model.PayNote;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PayNoteMapper extends SuperMapper<PayNote> {
    List<PayNote> listByObj(Pagination page, PayNote payNote);
    List<Map<String, Object>> calcByPayGroupId(@Param(value="payGroupId") Long payGroupId);
}
