package com.lyml.demo1.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lyml.demo1.SuperMapper;
import com.lyml.demo1.model.Medicine;

import java.util.List;

public interface MedicineMapper extends SuperMapper<Medicine> {
    List<Medicine> listByObj(Pagination page, Medicine medicine);
}
