package org.client.com.category.mapper;

import org.client.com.category.model.CategoryModel;

import java.util.List;

public interface CategoryMapper {
    List<CategoryModel> findAll();
}
