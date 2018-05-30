package org.client.com.category.mapper;

import org.apache.ibatis.annotations.Select;
import org.client.com.category.model.CategoryModel;

import java.util.List;

public interface CategoryMapper {
    @Select({"select c.id as id ,c.cname as cnames  from category_table c "})
    List<CategoryModel> findAll();
}
